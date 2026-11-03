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

import io.gravitee.gateway.reactive.api.context.llm.LlmExecutionContext;

/**
 * Execution context of a guardrail running for an LLM proxy API.
 *
 * @author GraviteeSource Team
 */
public non-sealed interface LlmGuardrailExecutionContext extends GuardrailExecutionContext {
    /**
     * The llm view of the exchange: the normalized conversation sent to the model and the answer it gives back.
     *
     * @return the llm execution context of the exchange.
     */
    LlmExecutionContext llm();
}
