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
import javax.security.auth.callback.Callback;

/**
 * Specialized context for Kafka connection.
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface KafkaConnectionContext extends NativeExecutionContext {
    /**
     * Access the array of {@link Callback} exchanged during connection. This can be used by a kafka security policy to extract a security token and to authenticate the connection.
     * @return an array of {@link Callback}
     */
    Callback[] callbacks();

    /**
     * Access the SASL mechanism name used during connection. It can be 'PLAIN', 'SCRAM-SHA-256', 'SCRAM-SHA-512' or 'OAUTHBEARER'.
     * @return the name of the SASL mechanism
     */
    String saslMechanism();
}