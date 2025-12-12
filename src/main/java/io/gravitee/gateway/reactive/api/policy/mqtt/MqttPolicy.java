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
package io.gravitee.gateway.reactive.api.policy.mqtt;

import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.mqtt.MqttConnectionContext;
import io.gravitee.gateway.reactive.api.context.mqtt.MqttExecutionContext;
import io.gravitee.gateway.reactive.api.context.mqtt.MqttMessageExecutionContext;
import io.gravitee.gateway.reactive.api.policy.base.BasePolicy;
import io.reactivex.rxjava3.core.Completable;

/**
 * A {@link MqttPolicy} allows to define the actions to apply during the different Mqtt requests and responses.
 * A policy can override the default behavior (which is, doing nothing at all) to execute during the Mqtt request or response phase or on each message when the policy is able to work at message level.
 * In the case of records from ProduceRequest and FetchResponse.
 * The implemented methods will be called depending on the execution phase:
 * <ul>
 *     <li>{@link ExecutionPhase#REQUEST}: {@link #onRequest(MqttExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#RESPONSE}: {@link #onResponse(MqttExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#MESSAGE_REQUEST}: {@link #onMessageRequest(MqttMessageExecutionContext)}. Flow of messages is derived from Kakfa ProduceRequest data.</li>
 *     <li>{@link ExecutionPhase#MESSAGE_RESPONSE}: {@link #onMessageResponse(MqttMessageExecutionContext)}. Flow of messages is derived from Kakfa FetchResponse data.</li>
 * </ul>
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface MqttPolicy extends BasePolicy {
    /**
     * Define the actions to perform once api is deployed and the connection is established.
     * The <code>onInitialize(MqttConnectionContext)</code> method will be called once before any policy chain construction.
     *
     * @param ctx the current connection context allowing to access the connection attributes & topicIdentityRegistry.
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onInitialize(final MqttConnectionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#REQUEST} phase.
     * The <code>onRequest(MqttExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onRequest(final MqttExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#RESPONSE} phase.
     * The <code>onResponse(MqttExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onResponse(final MqttExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#MESSAGE_REQUEST} phase.
     * The <code>onMessageRequest(MqttMessageExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onMessageRequest(final MqttMessageExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#MESSAGE_RESPONSE} phase.
     * The <code>onMessageResponse(MqttMessageExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onMessageResponse(final MqttMessageExecutionContext ctx) {
        return Completable.complete();
    }
}
