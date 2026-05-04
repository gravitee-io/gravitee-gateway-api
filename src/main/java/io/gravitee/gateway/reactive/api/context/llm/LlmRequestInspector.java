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
import org.jspecify.annotations.Nullable;

public interface LlmRequestInspector {
    KindSemantic kind(HttpBaseExecutionContext context, JsonNode body);

    Maybe<String> prompt(HttpBaseExecutionContext context, PromptQuery promptQuery, @Nullable Buffer buffer);

    sealed interface PromptQuery {
        /**
         * The last message if it’s a user message.
         */
        record PendingUserPrompt() implements PromptQuery {}

        /**
         * All messages of historic produced by user.
         */
        record AllUserPrompts() implements PromptQuery {}

        /**
         * All prompt of historic (user, agent and tool)
         */
        record AllPrompts() implements PromptQuery {}

        /**
         * Prompt defined by an Expression Langage
         */
        record CustomPrompt(String expressionLanguage) implements PromptQuery {}
    }

    enum KindSemantic {
        PROMPT,
        TOOL_CALL,
        TOOL_RESULT,
        ERROR,
    }
}
