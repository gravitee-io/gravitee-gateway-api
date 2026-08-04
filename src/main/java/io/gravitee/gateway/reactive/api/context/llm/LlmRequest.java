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

import io.gravitee.gateway.reactive.api.context.http.HttpBaseRequest;
import io.gravitee.gateway.reactive.api.context.http.HttpPlainRequest;
import java.util.List;

/**
 * A vendor-agnostic view over an llm request: the conversation history and the call parameters, normalized
 * regardless of the upstream provider's wire format.
 * <p>
 * An llm request is carried over an underlying plain http request, so this also extends {@link HttpPlainRequest}
 * to expose the raw http-level view (headers, path, method, raw body, ...) alongside the normalized one.
 *
 * @author GraviteeSource Team
 */
public interface LlmRequest extends HttpPlainRequest {
    /**
     * @return the conversation history, in order.
     */
    List<Turn> messages();

    /**
     * Replaces the conversation history.
     *
     * @param messages the new conversation history.
     */
    void messages(List<Turn> messages);

    /**
     * @return the call parameters (model, tools, ...).
     * <p>
     * Named {@code llmParameters()} rather than {@code parameters()} to avoid clashing with
     * {@link HttpBaseRequest#parameters()} (the http query parameters).
     */
    LlmParameters llmParameters();
}
