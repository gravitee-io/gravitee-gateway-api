/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.jupiter.api.context;

import io.gravitee.el.TemplateEngine;
import io.gravitee.gateway.jupiter.api.ExecutionFailure;
import io.gravitee.gateway.jupiter.api.message.Message;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface MessageExecutionContext extends GenericExecutionContext {
    String TEMPLATE_ATTRIBUTE_MESSAGE = "message";

    /**
     * Get the current request stuck to this execution context.
     *
     * @return the request attached to this execution context.
     */
    MessageRequest request();

    /**
     * Get the current response stuck to this execution context.
     *
     * @return the response attached to this execution context.
     */
    MessageResponse response();

    /**
     * Interrupts the current execution while indicating that the flow of messages can be consumed "as is" to the downstream.
     * This has direct impact on how the remaining execution flow will behave (ex: remaining policies in a policy chain won't be executed).
     */
    Flowable<Message> interruptMessages();

    /**
     * Same as {@link #interruptMessages()} but with an {@link ExecutionFailure} object that indicates that the execution has failed. The {@link ExecutionFailure} can be processed in order to build a proper response (ex: based on templating, with appropriate accept-encoding, ...).
     */
    Flowable<Message> interruptMessagesWith(final ExecutionFailure failure);

    /**
     * Same as {@link MessageExecutionContext#interruptMessages} but at message level
     */
    Maybe<Message> interruptMessage();

    /**
     * Same as {@link MessageExecutionContext#interruptMessagesWith(ExecutionFailure)} but at message level
     */
    Maybe<Message> interruptMessageWith(final ExecutionFailure failure);

    /**
     * Get the {@link TemplateEngine} that can be used to evaluate EL expressions.
     *
     * @return the El {@link TemplateEngine}.
     */
    TemplateEngine getTemplateEngine(Message message);
}
