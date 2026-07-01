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

import io.gravitee.gateway.reactive.api.connector.endpoint.agent.auth.AuthenticationConfiguration;
import java.util.List;

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
    record Thinking(String text) implements AgentEvent {}

    /**
     * Partial chunk of the assistant's textual answer as it streams from the model.
     * Entrypoints typically concatenate consecutive tokens before rendering.
     */
    record PartialResponse(String token) implements AgentEvent {}

    /**
     * Notification that a tool call has been executed by the agent loop. Carried fields hold the
     * provider-assigned tool-call id, the tool name, the serialized arguments the agent sent to the
     * tool, and the serialized result the tool returned. Emitted <em>after</em> the tool ran
     * (success or recoverable failure).
     */
    record ToolExecuted(String id, String tool, String arguments, String result) implements AgentEvent {}

    /**
     * Terminal event marking the end of the agent loop. Carries the final answer text along with
     * the model's reported token usage and finish reason, both nullable when the provider doesn't
     * surface them.
     */
    record Completed(String text, Integer inputTokens, Integer outputTokens, String finishReason) implements AgentEvent {}

    record ToolAuthenticationRequired(String id, AuthenticationConfiguration configuration) implements AgentEvent {}

    /**
     * Notification that a tool requires explicit user approval before the agent can invoke it.
     * Entrypoints should surface an approve/reject UI (e.g. Slack buttons, HTTP response body).
     * Once the caller approves via {@code approveUrl} the original query must be retried.
     *
     * <p>{@code memoryId} and {@code pendingToolCallIds} are internal routing fields used by the
     * invoker to store the pending approval with enough context for the approve/reject callbacks to
     * rewind or update the {@code ToolExecutionResultMessage} entries injected into working memory.
     * Entrypoints can ignore these two fields.</p>
     */
    record ToolApprovalRequired(String toolId, String toolName, Object memoryId, List<String> pendingToolCallIds) implements AgentEvent {
        /** Convenience factory for contexts where no working memory is in use. */
        public static ToolApprovalRequired of(String toolId, String toolName) {
            return new ToolApprovalRequired(toolId, toolName, null, List.of());
        }
    }

    /**
     * Notification that the MCP backend issued an {@code elicitation/create} during a tool call —
     * the server needs additional user input before it can complete the invocation.
     * Entrypoints should surface the prompt and schema to the user and POST the response to
     * {@code submitUrl}. The original query must then be retried.
     *
     * @param elicitationId  Unique id stored in the pending-elicitation vault (passed as path segment to the submit URL).
     * @param message         Human-readable message from the MCP server describing what input is needed.
     * @param url JSON Schema describing the expected input fields (may be {@code null}).
     */
    record ElicitationRequired(String elicitationId, String message, String url) implements AgentEvent {}
}
