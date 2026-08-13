/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.reactive.api.connector.endpoint.agent.exception;

/**
 * A resource an agent's definition names — a sandbox, a store — is deployed nowhere it can reach.
 *
 * <p>This is a deployment fault, not something the model can work around, so it interrupts the run instead of being
 * handed back as a tool result: an agent told "the tool is unavailable" writes an apology, and the caller cannot tell
 * that answer from a real one.</p>
 *
 * <p><b>Two audiences.</b> {@link #getMessage()} is written for whoever called the agent and reaches them in the
 * response body, so it names nothing about how the agent is built. The identifying detail — which agent, which
 * reference — is carried separately for the log, the metric and the span, where only operators look.</p>
 *
 * @author GraviteeSource Team
 */
public class AgentResourceUnavailableException extends ToolException {

    /** The failure key this surfaces under: on the request metrics, the connection log, and the span's error type. */
    public static final String FAILURE_KEY = "AGENT_RESOURCE_NOT_FOUND";

    /** What the caller is told. Enough to know it will not work and who can fix it; nothing about the deployment. */
    public static final String PUBLIC_MESSAGE = "This agent is not correctly configured and cannot run. Contact the agent's owner.";

    /**
     * The agent that declared the reference — a workflow's {@code refId}, or the agent's id when it runs alone;
     * {@code null} when raised somewhere that does not know, until {@link #withAgentRef} names it.
     */
    private final String agentRef;

    /** The resource name that resolved nowhere. */
    private final String resourceRef;

    public AgentResourceUnavailableException(final String agentRef, final String resourceRef) {
        super(PUBLIC_MESSAGE);
        this.agentRef = agentRef;
        this.resourceRef = resourceRef;
    }

    /**
     * The same failure, attributed to an agent. A connector knows the name that did not resolve but not which agent is
     * running it, so the agent layer names it on the way out.
     */
    public AgentResourceUnavailableException withAgentRef(final String agentRef) {
        return new AgentResourceUnavailableException(agentRef, resourceRef);
    }

    public String getAgentRef() {
        return agentRef;
    }

    public String getResourceRef() {
        return resourceRef;
    }

    /**
     * This failure inside {@code throwable}'s cause chain, or {@code null}. Frameworks wrap what a tool threw, so the
     * layers that report a run's outcome all have to look past the wrapper.
     */
    public static AgentResourceUnavailableException findIn(final Throwable throwable) {
        for (Throwable candidate = throwable; candidate != null; candidate = candidate.getCause()) {
            if (candidate instanceof AgentResourceUnavailableException found) {
                return found;
            }
            if (candidate.getCause() == candidate) {
                return null;
            }
        }
        return null;
    }

    /** The operator-facing form: safe for a log, a span attribute or anywhere else the caller cannot read. */
    public String detail() {
        return (
            "agent [" + (agentRef != null ? agentRef : "unknown") + "] declares resource [" + resourceRef +
            "], which is deployed nowhere it can reach"
        );
    }
}
