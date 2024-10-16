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

import io.gravitee.gateway.reactive.api.context.kafka.KafkaConnectionContext;
import io.gravitee.gateway.reactive.api.policy.SecurityToken;
import io.gravitee.gateway.reactive.api.policy.base.BaseSecurityPolicy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * {@link KafkaSecurityPolicy} is a {@link KafkaPolicy} that can be used for securing a plan that can require a subscription.
 * Implementing a {@link KafkaSecurityPolicy} requires to implement some additional behavior in order to be used by the gateway during the security chain execution that will identify which plan the consumer is using:
 * <ul>
 *     <li>{@link #order()}: to define the priority compared to other security policies</li>
 *     <li>{@link #requireSubscription()} : to indicate if it require a valid subscription or not</li>
 *     <li>{@link #extractSecurityToken(KafkaConnectionContext ctx)}: to extract the {@link SecurityToken} from the kafka connection context</li>
 *     <li>{@link #authenticate(KafkaConnectionContext ctx)}: to validate the {@link SecurityToken} extracted from the kafka connection context</li>
 * </ul>
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface KafkaSecurityPolicy extends KafkaPolicy, BaseSecurityPolicy {
    Maybe<SecurityToken> extractSecurityToken(final KafkaConnectionContext ctx);

    Completable authenticate(final KafkaConnectionContext ctx);
}
