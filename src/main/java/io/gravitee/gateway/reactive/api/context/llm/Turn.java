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
package io.gravitee.gateway.reactive.api.context.llm;

import java.util.List;

/**
 * A single, vendor-agnostic entry of an llm conversation (either part of the request history, or an
 * assistant turn/delta produced in a response).
 * <p>
 * When emitted from {@link LlmResponse#deltas()}, {@code content()} and a {@link ToolCall}'s {@code arguments()}
 * are <b>incremental fragments</b> (the text/JSON appended since the previous delta for this turn), matching how
 * providers stream over SSE. Use {@link LlmResponse#aggregated()} to get the fully assembled turn instead.
 *
 * @param role the role that authored this turn.
 * @param content the textual content of the turn (or fragment thereof, see above), or {@code null} for a turn that only carries tool calls.
 * @param name an optional name qualifying the author of the turn (ex: the tool name for a {@link Role#TOOL} turn).
 * @param toolCallId the id of the tool call this turn answers, set only for {@link Role#TOOL} turns.
 * @param toolCalls the tool calls requested by the model as part of this turn, set only for {@link Role#ASSISTANT} turns.
 *
 * @author GraviteeSource Team
 */
public record Turn(Role role, String content, String name, String toolCallId, List<ToolCall> toolCalls) {
    /**
     * A tool call requested by the model.
     *
     * @param id the id of the tool call.
     * @param name the name of the tool being called.
     * @param arguments the arguments the tool is being called with, as raw JSON text. May be a partial,
     *                   non-parseable fragment when observed via {@link LlmResponse#deltas()}; guaranteed to be
     *                   complete, parseable JSON when observed via {@link LlmResponse#aggregated()}.
     */
    public record ToolCall(String id, String name, String arguments) {}
}
