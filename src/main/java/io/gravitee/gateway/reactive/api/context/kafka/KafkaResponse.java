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

import io.gravitee.gateway.reactive.api.context.base.BaseRequest;
import io.gravitee.gateway.reactive.api.context.base.NativeResponse;

/**
 * Represents a response that can manipulate a Kafka native response.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface KafkaResponse extends NativeResponse {
    // TODO: to be defined.

    /**
     * Access the underlying native Kafka response.
     *
     * @param <T> the expected native Kafka response.
     *
     * @return the Kafka native response.
     */
    <T> T delegate();
}
