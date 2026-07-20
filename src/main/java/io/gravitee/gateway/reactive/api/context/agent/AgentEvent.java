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
package io.gravitee.gateway.reactive.api.context.agent;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Typed event emitted by the agent layer on {@link AgentResponse#events()}.
 * Entrypoints pattern-match on the variant to translate each event into their protocol shape
 * (e.g. a Slack chunked update, an SSE event, …) without coupling to the underlying agent
 * framework.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public sealed interface AgentEvent {
    /**
     * Intermediate "reasoning" text produced by the model before it commits to its answer
     * (a.k.a. thinking / chain-of-thought tokens). Not all providers expose this.
     */
    record Thinking(String text, long timestamp) implements AgentEvent {
        public Thinking(String text) {
            this(text, System.currentTimeMillis());
        }
    }

    /**
     * Partial chunk of the assistant's textual answer as it streams from the model.
     * Entrypoints typically concatenate consecutive tokens before rendering.
     */
    record PartialResponse(String token, long timestamp) implements AgentEvent {
        public PartialResponse(String token) {
            this(token, System.currentTimeMillis());
        }
    }

    record AgentStart(String agentId, String agentName, long timestamp) implements AgentEvent {
        public AgentStart(String agentId, String agentName) {
            this(agentId, agentName, System.currentTimeMillis());
        }
    }

    record AgentEnd(String agentId, String agentName, boolean error, String reason, long timestamp) implements AgentEvent {
        public AgentEnd(String agentId, String agentName, boolean error, String reason) {
            this(agentId, agentName, error, reason, System.currentTimeMillis());
        }

        public static AgentEnd of(String agentId, String agentName) {
            return new AgentEnd(agentId, agentName, false, null);
        }
    }

    /**
     * Nesting envelope wrapping an event produced by a workflow sub-agent (a leaf of the composition), so the client can
     * render sub-agent actions (its {@link AgentStart}/{@link AgentEnd}, {@link ToolCallStart}/{@link ToolCallEnd} and
     * its {@link Completed} output) nested under the parent agent — the same events the top-level agent emits, but tagged
     * with their position in the tree. Because sub-agent activity is carried inside this distinct variant, entrypoints
     * that aggregate the top-level answer never fold a sub-agent's text into the main response; a renderer that wants to
     * show the tree unwraps {@code event} and uses {@code parentAgentId}/{@code depth} to place it.
     *
     * @param parentAgentId The {@code agentId} of the enclosing agent (the workflow root for a direct child).
     * @param depth         Nesting level below the root (1 for a direct child, deeper for nested workflows).
     * @param event         The wrapped event the sub-agent produced.
     */
    record SubAgent(String parentAgentId, int depth, AgentEvent event, long timestamp) implements AgentEvent {
        public SubAgent(String parentAgentId, int depth, AgentEvent event) {
            this(parentAgentId, depth, event, System.currentTimeMillis());
        }
    }

    record ToolCallStart(String toolId, String toolName, String arguments, long timestamp) implements AgentEvent {
        public ToolCallStart(String toolId, String toolName, String arguments) {
            this(toolId, toolName, arguments, System.currentTimeMillis());
        }
    }

    /**
     * A tool-call failure. {@code type} is a low-cardinality classifier of the failure — the exception's class name,
     * or {@code null} when the tool failed without a concrete type (a protocol-level {@code isError} result).
     * {@code cause} is the raised exception when one is available (else {@code null}), so consumers that need the full
     * detail — message, stack trace, cause chain — can inspect it. A tool event's {@code error} is {@code null} when
     * the call succeeded.
     */
    record ToolError(String type, Throwable cause) {}

    record ToolCallEnd(String toolId, String toolName, String arguments, String result, ToolError error, long timestamp) implements
        AgentEvent {
        public ToolCallEnd(String toolId, String toolName, String arguments, String result) {
            this(toolId, toolName, arguments, result, null, System.currentTimeMillis());
        }

        public ToolCallEnd(String toolId, String toolName, String arguments, String result, ToolError error) {
            this(toolId, toolName, arguments, result, error, System.currentTimeMillis());
        }
    }

    /**
     * Notification that a tool call has been executed by the agent loop. Carried fields hold the
     * provider-assigned tool-call id, the tool name, the serialized arguments the agent sent to the
     * tool, and the serialized result the tool returned. Emitted <em>after</em> the tool ran (success or recoverable
     * failure). {@code error} is {@code null} on success, else the failure's {@link ToolError} (type + optional cause).
     */
    record ToolExecuted(String toolId, String toolName, String arguments, String result, ToolError error, long timestamp) implements
        AgentEvent {
        public ToolExecuted(String toolId, String toolName, String arguments, String result) {
            this(toolId, toolName, arguments, result, null, System.currentTimeMillis());
        }

        public ToolExecuted(String toolId, String toolName, String arguments, String result, ToolError error) {
            this(toolId, toolName, arguments, result, error, System.currentTimeMillis());
        }
    }

    /**
     * Terminal event marking the end of the agent loop. Carries the final answer text along with
     * the model's reported token usage and finish reason, both nullable when the provider doesn't
     * surface them.
     */
    record Completed(String text, Integer inputTokens, Integer outputTokens, String finishReason, long timestamp) implements AgentEvent {
        public Completed(String text, Integer inputTokens, Integer outputTokens, String finishReason) {
            this(text, inputTokens, outputTokens, finishReason, System.currentTimeMillis());
        }
    }

    /**
     * Notification that the model wants to invoke a tool whose execution is delegated to the
     * caller (an "external execution tool" — declared per-request by the entrypoint rather than
     * resolved from a configured {@code ToolEndpointConnector}). The agent loop pauses; it is the
     * entrypoint's responsibility to surface {@code toolName}/{@code arguments} to the caller,
     * execute the tool on its side, and resume the conversation by feeding the result back through
     * the entrypoint's own protocol (e.g. OpenAI Responses' {@code function_call_output} items).
     *
     * <p>Unlike {@link HumanInteractionRequired} this carries no callback URL: resuming is driven by
     * the entrypoint's native protocol, not a clickable link.</p>
     *
     * @param toolCallId Provider-assigned id correlating this call with the caller's eventual result.
     * @param toolName   The external tool's name, as declared by the caller for this request.
     * @param arguments  JSON-encoded arguments the model produced for the call.
     */
    record ExternalToolCallRequired(String toolCallId, String toolName, String arguments, long timestamp) implements AgentEvent {
        public ExternalToolCallRequired(String toolCallId, String toolName, String arguments) {
            this(toolCallId, toolName, arguments, System.currentTimeMillis());
        }
    }

    /**
     * The kind of human interaction a {@link HumanInteractionRequired} event is pausing for. The name lower-cased
     * (e.g. {@code TOOL_APPROVAL_REQUIRED} -> {@code "tool_approval_required"}) is the canonical wire value entrypoints
     * should use as the sub-type discriminator in their protocol payloads.
     */
    enum HumanInteractionType {
        TOOL_AUTHENTICATION_REQUIRED,
        TOOL_APPROVAL_REQUIRED,
        ELICITATION_REQUIRED,
        HUMAN_INPUT_REQUIRED,
    }

    /**
     * Notification that the agent loop is paused waiting on a human — an OAuth sign-in, a tool-call approval, an MCP
     * elicitation, or a workflow {@code human} (HITL) node input. {@code type} discriminates which of these it is;
     * {@code metadata} carries the type-specific details entrypoints need to surface the right UI and resume the run:
     *
     * <ul>
     *   <li>{@link HumanInteractionType#TOOL_AUTHENTICATION_REQUIRED}: {@code toolId}, {@code initiateUrl}.</li>
     *   <li>{@link HumanInteractionType#TOOL_APPROVAL_REQUIRED}: {@code toolId}, {@code toolName}, {@code approveUrl},
     *       {@code rejectUrl}.</li>
     *   <li>{@link HumanInteractionType#ELICITATION_REQUIRED}: {@code elicitationId}, {@code message}, {@code url}
     *       (JSON Schema, may be absent), {@code submitUrl}.</li>
     *   <li>{@link HumanInteractionType#HUMAN_INPUT_REQUIRED}: {@code interactionId}, {@code prompt}, {@code schema}
     *       (may be absent), {@code submitUrl}, {@code statusUrl} (present only when the ask was delegated to an
     *       out-of-band channel; absent for the inline case where the caller answers in place and re-sends to resume).</li>
     * </ul>
     *
     * A key absent from {@code metadata} is equivalent to a {@code null} value for that field.
     */
    record HumanInteractionRequired(HumanInteractionType type, Map<String, String> metadata, long timestamp) implements AgentEvent {
        public HumanInteractionRequired(HumanInteractionType type, Map<String, String> metadata) {
            this(type, metadata, System.currentTimeMillis());
        }

        public static HumanInteractionRequired toolAuthenticationRequired(String toolId, String initiateUrl) {
            return new HumanInteractionRequired(
                HumanInteractionType.TOOL_AUTHENTICATION_REQUIRED,
                metadataOf("toolId", toolId, "initiateUrl", initiateUrl)
            );
        }

        public static HumanInteractionRequired toolApprovalRequired(String toolId, String toolName, String approveUrl, String rejectUrl) {
            return new HumanInteractionRequired(
                HumanInteractionType.TOOL_APPROVAL_REQUIRED,
                metadataOf("toolId", toolId, "toolName", toolName, "approveUrl", approveUrl, "rejectUrl", rejectUrl)
            );
        }

        public static HumanInteractionRequired elicitationRequired(String elicitationId, String message, String url, String submitUrl) {
            return new HumanInteractionRequired(
                HumanInteractionType.ELICITATION_REQUIRED,
                metadataOf("elicitationId", elicitationId, "message", message, "url", url, "submitUrl", submitUrl)
            );
        }

        public static HumanInteractionRequired humanInputRequired(
            String interactionId,
            String prompt,
            String schema,
            String submitUrl,
            String statusUrl
        ) {
            return new HumanInteractionRequired(
                HumanInteractionType.HUMAN_INPUT_REQUIRED,
                metadataOf(
                    "interactionId",
                    interactionId,
                    "prompt",
                    prompt,
                    "schema",
                    schema,
                    "submitUrl",
                    submitUrl,
                    "statusUrl",
                    statusUrl
                )
            );
        }

        private static Map<String, String> metadataOf(String... keysAndValues) {
            Map<String, String> metadata = new LinkedHashMap<>();
            for (int i = 0; i < keysAndValues.length; i += 2) {
                metadata.put(keysAndValues[i], keysAndValues[i + 1]);
            }
            return Collections.unmodifiableMap(metadata);
        }
    }
}
