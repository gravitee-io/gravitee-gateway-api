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

import io.gravitee.gateway.reactive.api.context.base.NativeMessageExecutionContext;
import org.apache.kafka.common.security.auth.KafkaPrincipal;

/**
 * Message execution context specialized for Kafka.
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface KafkaMessageExecutionContext extends NativeMessageExecutionContext {
    /**
     * Get the current request attached to this execution context.
     * @return the request.
     */
    KafkaMessageRequest request();

    /**
     * Get the current response attached to this execution context when it is available.
     * @return the response.
     */
    KafkaMessageResponse response();

    /**
     * Access the principal of the current execution context.
     * @return the principal of the current execution context.
     */
    KafkaPrincipal principal();
}
