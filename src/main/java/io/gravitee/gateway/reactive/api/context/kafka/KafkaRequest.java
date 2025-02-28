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

import io.gravitee.gateway.reactive.api.context.base.NativeRequest;
import org.apache.kafka.common.requests.AbstractRequest;

/**
 * Represents a request that can manipulate a Kafka native request.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface KafkaRequest extends NativeRequest {
    /**
     * Access the underlying native Kafka request.
     * @return the Kafka native request.
     */
    <T extends AbstractRequest> T delegate();

    /**
     * Register that the request has been updated during its processing and need to be rebuilt before sending it.
     */
    void notifyChange();
}
