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
package io.gravitee.gateway.reactive.api.context.guardrail;

import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext;
import io.gravitee.gateway.reactive.api.context.llm.LlmExecutionContext;

/**
 * Execution context of a guardrail: the execution context of the API that enabled the Guardian, with the typed view
 * of the exchange that API carries.
 * <p>
 * {@link #request()} and {@link #response()} stay the plain http exchange, as on any
 * {@link HttpPlainExecutionContext}; the typed view is reached through the accessor of the protocol the API speaks,
 * which {@link #apiType()} names. A guardrail built for one protocol asks for that view and nothing else; the
 * Guardian reactor only runs it on an API of that type.
 *
 * @author GraviteeSource Team
 */
public interface GuardrailExecutionContext extends HttpPlainExecutionContext {
    /**
     * The type of the API the Guardian runs for.
     *
     * @return the type of the API the Guardian runs for.
     */
    ApiType apiType();

    /**
     * The llm view of the exchange: the normalized conversation sent to the model and the answer it gives back.
     *
     * @return the llm execution context of the exchange.
     * @throws IllegalStateException when {@link #apiType()} is not {@link ApiType#LLM_PROXY}.
     */
    LlmExecutionContext llm();
}
