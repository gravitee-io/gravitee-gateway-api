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

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * A single part of an llm request that will be transmitted to the model.
 * <p>
 * Two things reach the model, and they have nothing in common structurally: the conversation messages, and the
 * tool definitions the model is allowed to call. {@link Message} and {@link ToolDefinition} model them
 * separately rather than as one record whose half of the components would be {@code null} in every instance.
 * <p>
 * The only accessor they share is {@link #textContent()}, which is what a consumer that does not care about the
 * shape needs: dlp scanning, token counting, prompt injection detection. A consumer that does care
 * pattern-matches on the variant.
 * <p>
 * Deliberately absent from this type:
 * <ul>
 *   <li><b>the kind</b> ({@code PROMPT} / {@code TOOL_DESCRIPTION}): it is exactly the variant, so an accessor
 *       would say nothing {@code instanceof} does not already say. It only exists as a selection criterion, see
 *       {@link LlmPartCriteria.Kind}.</li>
 *   <li><b>the source</b> ({@code HARNESS} / {@code DISCUSSION}): it is a total function of the role, see
 *       {@link LlmPartCriteria.Source#of(Role)}. Exposing a derived value here would freeze a classification
 *       that is expected to evolve. It only exists as a selection criterion.</li>
 *   <li><b>the position</b> in the conversation: {@link LlmRequest#llmParts(List)} returns parts in order, so
 *       the list itself carries it.</li>
 * </ul>
 *
 * @see LlmRequest#llmParts(List)
 * @author GraviteeSource Team
 */
public sealed interface LlmContextPart permits LlmContextPart.Message, LlmContextPart.ToolDefinition {
    /**
     * All the text of this part that will reach the model, concatenated, newline-separated.
     * <p>
     * <b>Text only.</b> Non-textual content that is also transmitted to the model (inline images, file
     * references, audio) is <b>not</b> represented here, and nothing in this API currently signals its presence.
     * A guardrail that returns a verdict based solely on this value returns it on a partial view of the request.
     * The name says so on purpose.
     *
     * @return the textual content of this part, never {@code null}, possibly empty.
     */
    String textContent();

    /**
     * A message of the conversation history that will be transmitted to the model.
     * <p>
     * Carries the same components as {@link Turn} on purpose: {@link Turn} is left untouched for now, so the two
     * shapes are duplicated until it is decided whether {@link Turn} becomes this variant. Any component added
     * to {@link Turn} must be added here too until then.
     *
     * @param role the role that authored this message.
     * @param content the textual content of the message, or {@code null} for a message that only carries tool calls.
     * @param name an optional name qualifying the author (ex: the tool name for a {@link Role#TOOL} message).
     * @param toolCallId the id of the tool call this message answers, set only for {@link Role#TOOL} messages.
     * @param toolCalls the tool calls requested by the model, set only for {@link Role#ASSISTANT} messages.
     * @param metadata vendor specific metadata.
     */
    record Message(
        Role role,
        String content,
        String name,
        String toolCallId,
        List<Turn.ToolCall> toolCalls,
        Map<String, Object> metadata
    ) implements LlmContextPart {
        public Message {
            toolCalls = toolCalls == null ? List.of() : List.copyOf(toolCalls);
            metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        }

        /**
         * The message content, followed by the name and raw json arguments of each tool call it carries.
         * <p>
         * Tool call arguments are included because they are text the model receives, and because they are a data
         * exfiltration path a dlp scan has to see. Leaving them out would make this accessor miss content that
         * {@link ToolDefinition#textContent()} would have caught on the very same payload.
         */
        @Override
        public String textContent() {
            var joiner = new StringJoiner("\n");
            append(joiner, content);
            for (var toolCall : toolCalls) {
                append(joiner, toolCall.name());
                append(joiner, toolCall.arguments());
            }
            return joiner.toString();
        }

        private static void append(StringJoiner joiner, String value) {
            if (value != null && !value.isBlank()) {
                joiner.add(value);
            }
        }
    }

    /**
     * A tool definition made available to the model.
     * <p>
     * Always authored by the harness, never by the conversation, so it never carries a {@link Role}.
     *
     * @param name the name of the tool, as the model will see it.
     * @param description the description of the tool, as the model will see it.
     * @param schema the json schema of the tool parameters, in its provider-specific shape.
     * @param raw the whole tool definition as sent by the client, untouched.
     */
    record ToolDefinition(String name, String description, JsonNode schema, JsonNode raw) implements LlmContextPart {
        /**
         * The tool name, its description, and every {@code description} found in the parameter {@link #schema()},
         * recursively.
         * <p>
         * Parameter descriptions are included because they are a known prompt injection vector and because they
         * weigh in the prompt token count. Json punctuation, field names and type names are <b>not</b> included:
         * they would be noise for a dlp scan. Use {@link #raw()} to get the exact bytes instead.
         */
        @Override
        public String textContent() {
            var joiner = new StringJoiner("\n");
            if (name != null && !name.isBlank()) {
                joiner.add(name);
            }
            if (description != null && !description.isBlank()) {
                joiner.add(description);
            }
            collectDescriptions(schema, joiner);
            return joiner.toString();
        }

        private static void collectDescriptions(JsonNode node, StringJoiner joiner) {
            if (node == null || node.isNull()) {
                return;
            }
            if (node.isObject()) {
                var fields = node.fields();
                while (fields.hasNext()) {
                    var field = fields.next();
                    if ("description".equals(field.getKey()) && field.getValue().isTextual()) {
                        var value = field.getValue().asText();
                        if (!value.isBlank()) {
                            joiner.add(value);
                        }
                    } else {
                        collectDescriptions(field.getValue(), joiner);
                    }
                }
            } else if (node.isArray()) {
                node.forEach(child -> collectDescriptions(child, joiner));
            }
        }
    }
}
