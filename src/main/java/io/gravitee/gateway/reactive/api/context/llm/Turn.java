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
import java.util.Map;
import java.util.StringJoiner;

/**
 * A single, vendor-agnostic entry of an llm conversation (either part of the request history, or an
 * assistant turn/delta produced in a response).
 * <p>
 * When emitted from {@link LlmResponse#deltas()}, {@code content()} and a {@link ToolCall}'s {@code arguments()}
 * are <b>incremental fragments</b> (the text/JSON appended since the previous delta for this turn), matching how
 * providers stream over SSE. Use {@link LlmResponse#aggregated()} to get the fully assembled turn instead.
 * <p>
 * Also an {@link LlmContextPart}: a message of the history is one of the two things that reach the model, so a
 * consumer selecting parts through {@link LlmRequest#llmParts(List)} gets the very turns
 * {@link LlmRequest#messages()} exposes, not a copy of them.
 *
 * @param role the role that authored this turn.
 * @param content the textual content of the turn (or fragment thereof, see above), or {@code null} for a turn that only carries tool calls.
 * @param name an optional name qualifying the author of the turn (ex: the tool name for a {@link Role#TOOL} turn).
 * @param toolCallId the id of the tool call this turn answers, set only for {@link Role#TOOL} turns.
 * @param toolCalls the tool calls requested by the model as part of this turn, set only for {@link Role#ASSISTANT} turns.
 * @param metadata metadata vendor specific
 *
 * @author GraviteeSource Team
 */
public record Turn(
    Role role,
    String content,
    String name,
    String toolCallId,
    List<ToolCall> toolCalls,
    Map<String, Object> metadata
) implements Frame, LlmContextPart {
    public Turn {
        toolCalls = toolCalls == null ? List.of() : List.copyOf(toolCalls);
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    /**
     * The turn content, followed by the name and raw json arguments of each tool call it carries.
     * <p>
     * Tool call arguments are included because they are text the model receives, and because they are a data
     * exfiltration path a dlp scan has to see. Leaving them out would make this accessor miss content that
     * {@link LlmContextPart.ToolDefinition#textContent()} would have caught on the very same payload.
     */
    @Override
    public String textContent() {
        var joiner = new StringJoiner("\n");
        TextContents.append(joiner, content);
        for (var toolCall : toolCalls) {
            TextContents.append(joiner, toolCall.name());
            TextContents.append(joiner, toolCall.arguments());
        }
        return joiner.toString();
    }

    /**
     * A tool call requested by the model.
     *
     * @param id the id of the tool call.
     * @param name the name of the tool being called.
     * @param arguments the arguments the tool is being called with, as raw JSON text. May be a partial,
     *                   non-parseable fragment when observed via {@link LlmResponse#deltas()}; guaranteed to be
     *                   complete, parseable JSON when observed via {@link LlmResponse#aggregated()}.
     * @param metadata metadata vendor specific
     */
    public record ToolCall(String id, String name, String arguments, Map<String, Object> metadata) {
        public ToolCall {
            metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        }
    }
}
