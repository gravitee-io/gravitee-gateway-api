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

import io.gravitee.gateway.reactive.api.ExecutionFailure;
import io.gravitee.gateway.reactive.api.context.base.BaseMessageExecutionContext;
import io.gravitee.gateway.reactive.api.message.Message;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * Specialize http execution context allowing access to an {@link HttpMessageRequest} and {@link HttpMessageResponse} that manipulates flow of messages.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface HttpMessageExecutionContext extends HttpBaseExecutionContext, BaseMessageExecutionContext {
    String TEMPLATE_ATTRIBUTE_MESSAGE = "message";

    /**
     * Get the current request attached to this execution context.
     *
     * @return the request attached to this execution context.
     */
    HttpMessageRequest request();

    /**
     * Get the current response attached to this execution context.
     *
     * @return the response attached to this execution context.
     */
    HttpMessageResponse response();

    /**
     * Same as {@link #interruptMessages()} but with an {@link ExecutionFailure} object that indicates that the execution has failed. The {@link ExecutionFailure} can be processed in order to build a proper response (ex: based on templating, with appropriate accept-encoding, ...).
     */
    Flowable<Message> interruptMessagesWith(final ExecutionFailure failure);

    /**
     * Same as {@link BaseMessageExecutionContext#interruptMessagesWith(ExecutionFailure)} but at message level
     */
    Maybe<Message> interruptMessageWith(final ExecutionFailure failure);
}
