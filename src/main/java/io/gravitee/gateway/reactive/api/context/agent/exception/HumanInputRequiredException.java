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
package io.gravitee.gateway.reactive.api.context.agent.exception;

/**
 * Thrown by a workflow {@code human} (HITL) node when it has no answer yet: the run pauses and asks a
 * human the {@code prompt}. The invoker stores a pending entry (keyed by a generated interaction id),
 * surfaces an {@code AgentEvent.HumanInputRequired} carrying a submit URL, and — once the human POSTs
 * the answer — the workflow resumes and the node drains the answer as its output. {@code nodeId}
 * identifies which human node paused, so multiple human nodes in one run keep separate answers.
 *
 * <p>When the node declares a channel, the exception also carries {@code channelRef} (the resource that delivers the
 * ask out-of-band), plus the {@code timeout} (ISO-8601 duration) and {@code onTimeout} action — so the invoker can
 * deliver the ask, set an expiry, and let the gateway resume the run itself (delegated). {@code channelRef == null}
 * means the inline case (the caller answers in place and re-sends to resume).</p>
 *
 * @author GraviteeSource Team
 */
public class HumanInputRequiredException extends AgentInterruptException {

    private final String prompt;
    private final String schema;
    private final String nodeId;
    private final String channelRef;
    private final String timeout;
    private final String onTimeout;
    private final String resume;

    public HumanInputRequiredException(String prompt, String schema, String nodeId) {
        this(prompt, schema, nodeId, null, null, null, null);
    }

    public HumanInputRequiredException(
        String prompt,
        String schema,
        String nodeId,
        String channelRef,
        String timeout,
        String onTimeout,
        String resume
    ) {
        super("Human input required: " + prompt);
        this.prompt = prompt;
        this.schema = schema;
        this.nodeId = nodeId;
        this.channelRef = channelRef;
        this.timeout = timeout;
        this.onTimeout = onTimeout;
        this.resume = resume;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getSchema() {
        return schema;
    }

    public String getNodeId() {
        return nodeId;
    }

    /** The channel resource that delivers the ask out-of-band, or {@code null} for the inline (caller-answers) case. */
    public String getChannelRef() {
        return channelRef;
    }

    public String getTimeout() {
        return timeout;
    }

    public String getOnTimeout() {
        return onTimeout;
    }

    /** How the delegated run resumes when the answer arrives — {@code "client"} or {@code "server"} ({@code null} = default). */
    public String getResume() {
        return resume;
    }

    /** {@code true} when the ask is delegated to a channel (the gateway resumes on its own); {@code false} = inline. */
    public boolean isDelegated() {
        return channelRef != null && !channelRef.isBlank();
    }
}
