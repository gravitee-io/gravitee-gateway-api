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

import io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext;

/**
 * Execution context specialized for Llm.
 * <p>
 * An llm call is always carried over an underlying plain http call, so this context extends
 * {@link HttpPlainExecutionContext} to expose that http-level information (headers, raw body, metrics, ...) and
 * chain control ({@link #interrupt()}, {@link #interruptWith(io.gravitee.gateway.reactive.api.ExecutionFailure)}, ...)
 * alongside the vendor-agnostic {@link #request()}/{@link #response()} view.
 * <p>
 * Interrupting builds an error body, so it only applies as long as nothing has been flushed downstream: during
 * the request phase, or during the response phase before the response started streaming. It is the way to
 * <b>fail</b> a call, not to answer it differently: writing a successful response the model did not produce is
 * {@link LlmResponse#answer(Turn)}. Pass an
 * {@link LlmFailure} rather than a plain {@link io.gravitee.gateway.reactive.api.ExecutionFailure} when the body
 * needs vendor-specific attributes. Once the response is streaming there is nothing left to interrupt: the
 * status code and the headers are already sent, and the flow ends on an {@link ErrorFrame} only if the
 * provider itself failed mid-generation.
 *
 * @author GraviteeSource Team
 */
public interface LlmExecutionContext extends HttpPlainExecutionContext {
    /**
     * Get the current request stuck to this execution context.
     *
     * @return the request attached to this execution context.
     */
    LlmRequest request();

    /**
     * Get the current response stuck to this execution context.
     *
     * @return the response attached to this execution context.
     */
    LlmResponse response();
}
