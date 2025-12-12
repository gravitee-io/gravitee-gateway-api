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

import io.gravitee.gateway.reactive.api.context.mqtt.MqttConnectionContext;
import io.gravitee.gateway.reactive.api.policy.SecurityToken;
import io.gravitee.gateway.reactive.api.policy.base.BaseSecurityPolicy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * {@link MqttSecurityPolicy} is a {@link MqttPolicy} that can be used for securing a plan that can require a subscription.
 * Implementing a {@link MqttSecurityPolicy} requires to implement some additional behavior in order to be used by the gateway during the security chain execution that will identify which plan the consumer is using:
 * <ul>
 *     <li>{@link #order()}: to define the priority compared to other security policies</li>
 *     <li>{@link #requireSubscription()} : to indicate if it require a valid subscription or not</li>
 *     <li>{@link #extractSecurityToken(MqttConnectionContext ctx)}: to extract the {@link SecurityToken} from the kafka connection context</li>
 *     <li>{@link #authenticate(MqttConnectionContext ctx)}: to validate the {@link SecurityToken} extracted from the kafka connection context</li>
 * </ul>
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface MqttSecurityPolicy extends MqttPolicy, BaseSecurityPolicy {
    /**
     * Extracts the {@link SecurityToken} from the kafka connection context.
     * Relevant information is basically extracted from the array of {@link javax.security.auth.callback.Callback} available in the context.
     * If no relevant {@link SecurityToken} is found, it returns an empty Maybe, so that policy won't be executed.
     *
     * @param ctx the current kafka connection context.
     *
     * @return the {@link SecurityToken} found in the kafka connection context
     */
    Maybe<SecurityToken> extractSecurityToken(final MqttConnectionContext ctx);

    /**
     * Define the actions to perform during the connection.
     * The <code>authenticate(MqttConnectionContext)</code> method will be called during the connection between the kafka client and the gateway.
     * In this method, the "Gravitee" security is checked before going further.
     * For example, for an APIKey plan, the extracted apikey (extracted in the  <code>extractToken(MqttConnectionContext)</code> method) is searched in the gateway cache.
     * If the apikey is found and is valid, then the relevant {@link javax.security.auth.callback.Callback} has to be updated.
     *
     * @param ctx the current kafka connection context allowing to access the callbacks and the SASL mechanism used.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    Completable authenticate(final MqttConnectionContext ctx);
}
