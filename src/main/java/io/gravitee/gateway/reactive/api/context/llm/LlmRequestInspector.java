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
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.reactive.api.context.http.HttpBaseExecutionContext;
import io.reactivex.rxjava3.core.Maybe;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public interface LlmRequestInspector {
    KindSemantic kind(HttpBaseExecutionContext context, JsonNode body);

    /**
     * Indicates whether the response for this LLM request will be streamed
     * back to the client (e.g. Server-Sent Events).
     * <p>
     * The detection source is provider-specific and left to the implementation.
     * Typical sources include:
     * <ul>
     *   <li>a {@code "stream": true} flag in the JSON request body
     *       (OpenAI-style providers),</li>
     *   <li>an {@code Accept: text/event-stream} header,</li>
     *   <li>a dedicated query parameter,</li>
     *   <li>any combination of the above.</li>
     * </ul>
     *
     * @param context the current execution context (headers, query params, etc. available via {@link HttpBaseExecutionContext#request()}).
     * @param body    the parsed JSON request body, or {@code null} if unavailable.
     * @return {@code true} if the upstream response is expected to be streamed.
     */
    boolean isStream(HttpBaseExecutionContext context, @Nullable Buffer body);

    Optional<String> model(HttpBaseExecutionContext context, JsonNode body);

    Maybe<List<String>> prompt(HttpBaseExecutionContext context, PromptQuery promptQuery, @Nullable Buffer buffer);

    sealed interface PromptQuery {
        /**
         * The last message if it’s a user message.
         */
        record PendingUserPrompt() implements PromptQuery {}

        /**
         * All user prompts from history.
         */
        record AllUserPrompts() implements PromptQuery {}

        /**
         * All prompts from history (user, agent, and tool).
         */
        record AllPrompts() implements PromptQuery {}

        /**
         * Prompt defined by an Expression Language.
         */
        record CustomPrompt(String expressionLanguage) implements PromptQuery {}
    }

    enum KindSemantic {
        PROMPT,
        TOOL_CALL,
        TOOL_RESULT,
        ERROR,
        EMBEDDINGS,
    }
}
