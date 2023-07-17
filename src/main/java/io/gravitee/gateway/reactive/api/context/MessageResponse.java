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
package io.gravitee.gateway.reactive.api.context;

import io.gravitee.gateway.reactive.api.message.Message;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Maybe;
import java.util.function.Function;

/**
 * Represents a response that can manipulate a flow of messages.
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface MessageResponse extends GenericResponse {
    /**
     * Get the response message flow as a {@link Flowable} of {@link Message}.
     * <b>WARN:</b> you should not keep a direct reference on the message flow as it could be overridden by others at anytime.
     *
     * @return a {@link Flowable} of {@link Message}.
     */
    Flowable<Message> messages();

    /**
     * Set the response message flow.
     * <b>WARN:</b>
     * <ul>
     *  <li>Replacing the message flow <b>DOES NOT</b> take care of the previous message flow in place.</li>
     *  <li>You <b>MUST</b> ensure to consume the previous message flow when using it.</li>
     *  <li>You <b>SHOULD</b> consider using {@link #onMessages(FlowableTransformer)} or {@link #onMessage(Function)} that may be more appropriate for message transformation.</li>
     * </ul>
     *
     * @see #onMessage(Function)
     * @see #onMessages(FlowableTransformer)
     */
    void messages(final Flowable<Message> messages);

    /**
     * Applies a given transformation on each message.
     * Ex:
     * <code>
     *     response.onMessages(messages -> messages.flatMap(message -> transformMyMessage(message)));
     * </code>
     *
     * @param onMessages the transformer that will be applied on each message.
     * @return a {@link Completable} that completes once the message transformation has been set up on the message flow (not executed).
     */
    Completable onMessages(final FlowableTransformer<Message, Message> onMessages);

    /**
     * Applies a given transformation on each message.
     * Ex:
     * Discard a message:
     * <code>
     *     response.onMessage(message -> Maybe.empty());
     * </code>
     *
     * Update a message:
     * <code>
     *     response.onMessage(message -> transformMyMessage(message));
     * </code>
     *
     * @param onMessage the transformer that will be applied on each message.
     * @return a {@link Completable} that completes once the message transformation has been set up on the message flow (not executed).
     */
    default Completable onMessage(Function<Message, Maybe<Message>> onMessage) {
        return onMessages(upstream -> upstream.concatMapMaybe(onMessage::apply));
    }
}
