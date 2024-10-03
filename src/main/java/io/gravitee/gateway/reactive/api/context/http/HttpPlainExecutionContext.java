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
package io.gravitee.gateway.reactive.api.context.http;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.reactive.api.ExecutionFailure;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * Http execution context allowing access to {@link HttpPlainRequest} and {@link HttpPlainResponse} that manipulates raw http bodies (content or chunks).
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface HttpPlainExecutionContext extends HttpBaseExecutionContext {
    String TEMPLATE_ATTRIBUTE_REQUEST = "request";
    String TEMPLATE_ATTRIBUTE_RESPONSE = "response";
    String TEMPLATE_ATTRIBUTE_CONTEXT = "context";

    /**
     * Get the current request stuck to this execution context.
     *
     * @return the request attached to this execution context.
     */
    HttpPlainRequest request();

    /**
     * Get the current response stuck to this execution context.
     *
     * @return the response attached to this execution context.
     */
    HttpPlainResponse response();

    /**
     * Interrupts the current execution while indicating that the response can be sent "as is" to the downstream.
     * This has direct impact on how the remaining execution flow will behave (ex: remaining policies in a policy chain won't be executed).
     */
    Completable interrupt();

    /**
     * Same as {@link #interrupt()} but with an {@link ExecutionFailure} object that indicates that the execution has failed. The {@link ExecutionFailure} can be processed in order to build a proper response (ex: based on templating, with appropriate accept-encoding, ...).
     */
    Completable interruptWith(final ExecutionFailure failure);

    /**
     * Interrupts the current execution while indicating that the response can be sent "as is" to the downstream.
     * This has direct impact on how the remaining execution flow will behave (ex: remaining policies in a policy chain won't be executed).
     */
    Maybe<Buffer> interruptBody();

    /**
     * Same as {@link #interruptBody()} but with an {@link ExecutionFailure} object that indicates that the execution has failed. The {@link ExecutionFailure} can be processed in order to build a proper response (ex: based on templating, with appropriate accept-encoding, ...).
     */
    Maybe<Buffer> interruptBodyWith(final ExecutionFailure failure);
}
