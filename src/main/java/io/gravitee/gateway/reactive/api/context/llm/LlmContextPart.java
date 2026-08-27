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
import java.util.StringJoiner;

/**
 * A single part of an llm request that will be transmitted to the model.
 * <p>
 * Two things reach the model, and they have nothing in common structurally: the conversation messages, and the
 * tool definitions the model is allowed to call. {@link Turn} and {@link ToolDefinition} model them separately
 * rather than as one record whose half of the components would be {@code null} in every instance.
 * <p>
 * A conversation message is a {@link Turn}, the very type {@link LlmRequest#messages()} exposes: selecting parts
 * hands back the turns themselves, so there is no second shape of a message to keep in sync with the first.
 * <p>
 * The only accessor the two variants share is {@link #textContent()}, which is what a consumer that does not
 * care about the shape needs: dlp scanning, token counting, prompt injection detection. A consumer that does
 * care pattern-matches on the variant.
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
public sealed interface LlmContextPart permits Turn, LlmContextPart.ToolDefinition {
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
            TextContents.append(joiner, name);
            TextContents.append(joiner, description);
            TextContents.appendDescriptions(schema, joiner);
            return joiner.toString();
        }
    }
}
