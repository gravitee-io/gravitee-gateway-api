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

import io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext;

/**
 * Execution context of a guardrail: the execution context of the API that enabled the Guardian, with the typed view
 * of the exchange that API carries.
 * <p>
 * {@link #request()} and {@link #response()} stay the plain http exchange, as on any
 * {@link HttpPlainExecutionContext}; the typed view is carried by the subtype matching the protocol the API speaks.
 * A guardrail matches on that subtype to reach it; the Guardian reactor only runs a guardrail on an API of a type
 * the Guardian supports.
 *
 * @author GraviteeSource Team
 */
public sealed interface GuardrailExecutionContext extends HttpPlainExecutionContext permits LlmGuardrailExecutionContext {}
