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
package io.gravitee.gateway.reactive.api.policy.entrypoint;

import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.base.BaseExecutionContext;
import io.gravitee.gateway.reactive.api.policy.base.BasePolicy;
import io.reactivex.rxjava3.core.Completable;

/**
 * An {@link EntrypointPolicy} allows defining actions at the connection level,
 * before any protocol-specific processing occurs.
 * <p>
 * This is the appropriate interface for policies that operate on entrypoint-level
 * concerns such as:
 * <ul>
 *     <li>IP filtering (allow/block lists)</li>
 *     <li>TLS certificate validation</li>
 *     <li>Connection rate limiting</li>
 *     <li>Geographic restrictions</li>
 *     <li>Early authentication checks</li>
 * </ul>
 * <p>
 * Entrypoint policies are protocol-agnostic and can be reused across different
 * native protocols (Kafka, MQTT, AMQP, etc.).
 * <p>
 * The {@link #onEntrypointConnect(BaseExecutionContext)} method is called with a lightweight
 * context before any resource-intensive protocol-specific initialization occurs.
 * This allows early rejection of connections without wasting resources.
 *
 * @author GraviteeSource Team
 */
public interface EntrypointPolicy extends BasePolicy {
    /**
     * Define the actions to perform during the {@link ExecutionPhase#ENTRYPOINT_CONNECT} phase.
     * This method is called immediately after a connection is accepted, before any
     * protocol-specific processing (authentication, handshake, etc.) occurs.
     * <p>
     * It is important that <b>nothing must be executed before the subscription occurs</b>
     * as it could lead to important side effects.
     *
     * @param ctx the base execution context providing access to connection-level information
     *            (remote address, local address, TLS status) and attributes
     * @return a {@link Completable} that must complete when all the actions have been performed
     */
    default Completable onEntrypointConnect(final BaseExecutionContext ctx) {
        return Completable.complete();
    }
}
