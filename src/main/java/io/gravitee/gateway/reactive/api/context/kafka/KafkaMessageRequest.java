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
package io.gravitee.gateway.reactive.api.context.kafka;

import io.gravitee.gateway.reactive.api.message.kafka.KafkaMessage;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Maybe;
import java.util.function.Function;

/**
 * Represents a request that can manipulate a flow of messages that is mapped from a Kafka native ProduceRequest.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface KafkaMessageRequest {
    /**
     * Get the flow of messages.
     * Messages are extracted from the ProduceRequest.
     * <b>WARN:</b> you should not keep a direct reference on the message flow as it could be overridden by others at anytime.
     *
     * @return a {@link Flowable} of {@link KafkaMessage}.
     */
    Flowable<KafkaMessage> messages();

    /**
     * Set the request message flow.
     * <b>WARN:</b>
     * <ul>
     *  <li>Replacing the message flow <b>DOES NOT</b> take care of the previous message flow in place.</li>
     *  <li>You <b>MUST</b> ensure to consume the previous message flow when using it.</li>
     *  <li>You <b>SHOULD</b> consider using {@link #onMessages(FlowableTransformer)} or {@link #onMessage(Function)} that may be more appropriate for message transformation.</li>
     * </ul>
     *
     * @see #onMessage(Function)
     * @see #onMessages(FlowableTransformer)
     * @param messages the flow of messages.
     */
    void messages(final Flowable<KafkaMessage> messages);

    /**
     * Applies a given transformation on each message.
     * Messages are extracted from the ProduceRequest.
     * Ex:
     * <code>
     *     request.onMessages(messages -> messages.flatMap(message -> transformMyMessage(message)));
     * </code>
     *
     * @param onMessages the transformer that will be applied on each message.
     * @return a {@link Completable} that completes once the message transformation has been set up on the message flow (not executed).
     */
    Completable onMessages(final FlowableTransformer<KafkaMessage, KafkaMessage> onMessages);

    /**
     * Applies a given transformation on each message.
     * Messages are extracted from the ProduceRequest.
     * Ex:
     * Discard a message:
     * <code>
     *     request.onMessage(message -> Maybe.empty());
     * </code>
     *
     * Update a message:
     * <code>
     *     request.onMessage(message -> transformMyMessage(message));
     * </code>
     *
     * @param onMessage the transformer that will be applied on each message.
     * @return a {@link Completable} that completes once the message transformation has been set up on the message flow (not executed).
     */
    default Completable onMessage(Function<KafkaMessage, Maybe<KafkaMessage>> onMessage) {
        return onMessages(messages -> messages.concatMapMaybe(onMessage::apply));
    }
}
