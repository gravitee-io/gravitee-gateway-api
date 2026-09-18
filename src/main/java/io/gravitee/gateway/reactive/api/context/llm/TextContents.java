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
import java.util.StringJoiner;

/**
 * How the {@link LlmContextPart} variants assemble their {@link LlmContextPart#textContent()}.
 * <p>
 * They live in two different files ({@link Turn} is also a {@link Frame}, so it is a top-level type), and an
 * interface cannot hold a package-private helper for them to share. This class is that shared place: without
 * it the same "add it if it says something" rule would be written once per variant.
 *
 * @author GraviteeSource Team
 */
final class TextContents {

    private TextContents() {}

    /**
     * Adds the given value to the joiner, unless it says nothing.
     */
    static void append(StringJoiner joiner, String value) {
        if (value != null && !value.isBlank()) {
            joiner.add(value);
        }
    }

    /**
     * Adds every textual {@code description} found in the given json tree, recursively.
     */
    static void appendDescriptions(JsonNode node, StringJoiner joiner) {
        if (node == null || node.isNull()) {
            return;
        }
        if (node.isObject()) {
            var fields = node.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                if ("description".equals(field.getKey()) && field.getValue().isTextual()) {
                    append(joiner, field.getValue().asText());
                } else {
                    appendDescriptions(field.getValue(), joiner);
                }
            }
        } else if (node.isArray()) {
            node.forEach(child -> appendDescriptions(child, joiner));
        }
    }
}
