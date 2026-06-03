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

    record ToolAuthentication(String id, AuthenticationConfiguration configuration) implements AgentEvent {}
}
