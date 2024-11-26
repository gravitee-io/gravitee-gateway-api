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

import io.gravitee.gateway.reactive.api.context.base.NativeExecutionContext;
import io.reactivex.rxjava3.core.Completable;
import java.util.function.Function;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.security.auth.KafkaPrincipal;

/**
 * Execution context specialized for Kafka.
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface KafkaExecutionContext extends NativeExecutionContext {
    ApiKeys apiKey();
    int correlationId();
    KafkaRequest request();
    KafkaResponse response();

    /**
     * Access the principal of the current execution context.
     * @return the principal of the current execution context.
     */
    KafkaPrincipal principal();

    /**
     * Access the network controller of the current execution context.
     * @return the network controller of the current execution context.
     */
    NetworkController networkController();

    /**
     * Allows defining an action in the request phase to be executed at the response phase.
     * @param onResponseCallback the action to be executed at the response phase.
     */
    void addActionOnResponse(Function<KafkaExecutionContext, Completable> onResponseCallback);
}
