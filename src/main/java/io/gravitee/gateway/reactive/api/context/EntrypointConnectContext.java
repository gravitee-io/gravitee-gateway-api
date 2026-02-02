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
package io.gravitee.gateway.reactive.api.context;

import io.gravitee.gateway.reactive.api.context.base.NativeExecutionContext;
import io.reactivex.rxjava3.core.Completable;

/**
 * Context for the entrypoint connect phase in Native APIs.
 * This phase occurs when a client connects to the entrypoint, before any authentication or message flow.
 * It provides minimal context information for performance and only includes connection-level details.
 * <p>
 * This interface is protocol-agnostic and can be used by any native protocol implementation
 * (Kafka, AMQP, MQTT, etc.).
 *
 * @author Mukul Tyagi (mukul.tyagi at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface EntrypointConnectContext extends NativeExecutionContext {
    /**
     * Interrupts the connection by closing the socket.
     * This method can be called by policies to reject a connection during the entrypoint connect phase.
     *
     * @return a {@link Completable} that completes when the connection has been interrupted.
     */
    Completable interrupt();
}
