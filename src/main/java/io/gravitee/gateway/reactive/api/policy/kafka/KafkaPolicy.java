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
package io.gravitee.gateway.reactive.api.policy.kafka;

import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.kafka.KafkaExecutionContext;
import io.gravitee.gateway.reactive.api.context.kafka.KafkaMessageExecutionContext;
import io.gravitee.gateway.reactive.api.policy.base.BasePolicy;
import io.reactivex.rxjava3.core.Completable;

/**
 * A {@link KafkaPolicy} allows to define the actions to apply during the different Kafka requests and responses.
 * A policy can override the default behavior (which is, doing nothing at all) to execute during the Kafka request or response phase or on each message when the policy is able to work at message level.
 * In the case of records from ProduceRequest and FetchResponse.
 * The implemented methods will be called depending on the execution phase:
 * <ul>
 *     <li>{@link ExecutionPhase#REQUEST}: {@link #onRequest(KafkaExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#RESPONSE}: {@link #onResponse(KafkaExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#MESSAGE_REQUEST}: {@link #onMessageRequest(KafkaMessageExecutionContext)}. Flow of messages is derived from Kakfa ProduceRequest data.</li>
 *     <li>{@link ExecutionPhase#MESSAGE_RESPONSE}: {@link #onMessageResponse(KafkaMessageExecutionContext)}. Flow of messages is derived from Kakfa FetchResponse data.</li>
 * </ul>
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface KafkaPolicy extends BasePolicy {
    /**
     * Define the actions to perform during the {@link ExecutionPhase#REQUEST} phase.
     * The <code>onRequest(KafkaExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onRequest(final KafkaExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#RESPONSE} phase.
     * The <code>onResponse(KafkaExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onResponse(final KafkaExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#MESSAGE_REQUEST} phase.
     * The <code>onMessageRequest(KafkaMessageExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onMessageRequest(final KafkaMessageExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#MESSAGE_RESPONSE} phase.
     * The <code>onMessageResponse(KafkaMessageExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onMessageResponse(final KafkaMessageExecutionContext ctx) {
        return Completable.complete();
    }
}
