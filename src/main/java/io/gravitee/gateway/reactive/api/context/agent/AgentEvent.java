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

    /**
     * A tool call that has finished: the provider-assigned id, the tool's name, the arguments the agent sent, and the
     * result it returned. Emitted after the tool ran, on success and on recoverable failure alike — {@code error} is
     * {@code null} on success, else the failure's {@link ToolError}.
     *
     * <p>This is the <b>only</b> terminal tool event. It closes the {@link ToolCallStart} that opened the call, and it
     * is equally the standalone "this tool ran" fact a client renders a finished tool card from — a consumer that
     * wants one does not have to know about the other. There used to be a second event carrying identical components
     * for the latter purpose, and entrypoints split between the two arbitrarily: one that listened for the wrong one
     * showed the call starting and never finishing, with nothing to say why.</p>
     */
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
     * Terminal event marking the end of an agent's work: the final answer text, what it consumed, and the finish
     * reason ({@code null} when the provider doesn't surface one).
     *
     * <p>The scope of {@code usage} is the scope of the event. On a workflow's root it is the whole run — every
     * sub-agent plus whatever the orchestration itself spent. On a sub-agent's event (wrapped in {@link SubAgent})
     * it is that <em>invocation</em>, so a leaf run twice by a loop reports each pass on its own rather than a
     * growing total. It is never {@code null}; a scope where no provider reported usage is
     * {@link TokenCounts#NONE}, which {@link TokenCounts#measured()} tells apart from a scope that cost nothing.</p>
     */
    record Completed(String text, TokenCounts usage, String finishReason, long timestamp) implements AgentEvent {
        public Completed(String text, TokenCounts usage, String finishReason) {
            this(text, usage != null ? usage : TokenCounts.NONE, finishReason, System.currentTimeMillis());
        }
    }

    /**
     * A call to the model provider is starting. One per provider request, so an agent turn with tools produces
     * several: call, tool, call, tool, call, answer. Paired with the {@link ModelCallEnd} carrying the same
     * {@code callId} — which is what lets a consumer time the call rather than only the turn around it.
     *
     * <p>Unlike a sub-agent's own events these are <em>not</em> wrapped in {@link SubAgent}: they name their caller
     * directly through {@code agentId}, which is what a consumer needs to attribute the call. That id is the workflow
     * leaf's ref for a leaf, {@code <parent>:supervisor} for a planner, and the agent's own id on the standalone path.</p>
     *
     * @param callId       Correlates this start with its {@link ModelCallEnd}; opaque and unique within a run.
     * @param agentId      The agent whose turn made the call.
     * @param requestModel The model the call asked for ({@code gen_ai.request.model}), {@code null} when the caller
     *                     cannot know it before the response.
     * @param messages     The serialized prompt, or {@code null} — populated only when the run traces verbosely, since
     *                     it carries whatever the user typed.
     */
    record ModelCallStart(String callId, String agentId, String requestModel, String messages, long timestamp) implements AgentEvent {
        public ModelCallStart(String callId, String agentId, String requestModel, String messages) {
            this(callId, agentId, requestModel, messages, System.currentTimeMillis());
        }
    }

    /**
     * The outcome of the {@link ModelCallStart} sharing its {@code callId}.
     *
     * <p>{@code usage} is <b>that call alone</b>, never a running total: the point of the pair is to break a turn's
     * aggregate down, so summing the calls of a scope must reproduce the {@link Completed} total of that scope rather
     * than double it. {@link TokenCounts#NONE} when the provider reported nothing — unmeasured, not free.</p>
     *
     * @param responseModel The model that actually answered ({@code gen_ai.response.model}); providers may resolve an
     *                      alias to a dated version, so it can differ from the requested one.
     * @param responseId    The provider's own id for the response, {@code null} when it exposes none.
     * @param finishReason  Why the model stopped, {@code null} when the provider doesn't surface one.
     * @param output        The serialized completion, or {@code null} — populated only when tracing verbosely.
     * @param error         The failure when the call did not return, else {@code null}.
     */
    record ModelCallEnd(
        String callId,
        String agentId,
        String responseModel,
        String responseId,
        TokenCounts usage,
        String finishReason,
        String output,
        ModelError error,
        long timestamp
    ) implements AgentEvent {
        public ModelCallEnd(
            String callId,
            String agentId,
            String responseModel,
            String responseId,
            TokenCounts usage,
            String finishReason,
            String output,
            ModelError error
        ) {
            this(
                callId,
                agentId,
                responseModel,
                responseId,
                usage != null ? usage : TokenCounts.NONE,
                finishReason,
                output,
                error,
                System.currentTimeMillis()
            );
        }

        /** The call failed: no response, no usage, just the cause. */
        public static ModelCallEnd failed(String callId, String agentId, ModelError error) {
            return new ModelCallEnd(callId, agentId, null, null, TokenCounts.NONE, null, null, error);
        }
    }

    /**
     * A model-call failure, the {@link ToolError} of the {@code chat} operation: {@code type} is a low-cardinality
     * classifier — the exception's class name — and {@code cause} the raised exception when one is available. Kept
     * distinct from {@link ToolError} because a consumer that maps a failure into its protocol has to know whether it
     * is describing a tool or a provider call, and a shared type would leave that to a comment.
     */
    record ModelError(String type, Throwable cause) {
        /** Classifies {@code throwable} by its own class, the shape {@code error.type} wants. */
        public static ModelError of(Throwable throwable) {
            return throwable == null ? null : new ModelError(throwable.getClass().getName(), throwable);
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
