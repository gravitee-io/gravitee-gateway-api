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
package io.gravitee.gateway.reactive.api.policy;

import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.EntrypointConnectContext;
import io.gravitee.gateway.reactive.api.policy.base.BasePolicy;
import io.reactivex.rxjava3.core.Completable;

/**
 * Base interface for native protocol policies.
 * <p>
 * A {@link NativePolicy} defines common actions that can be applied across different native protocols
 * (Kafka, AMQP, MQTT, etc.). Protocol-specific policy interfaces should extend this interface.
 * <p>
 * The implemented methods will be called depending on the execution phase:
 * <ul>
 *     <li>{@link ExecutionPhase#ENTRYPOINT_CONNECT}: {@link #onEntrypointConnect(EntrypointConnectContext)}</li>
 * </ul>
 *
 * @author Mukul Tyagi (mukul.tyagi at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface NativePolicy extends BasePolicy {
    /**
     * Define the actions to perform during the {@link ExecutionPhase#ENTRYPOINT_CONNECT} phase.
     * The <code>onEntrypointConnect(EntrypointConnectContext)</code> method will be called when a client connects to the entrypoint, before authentication.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current entrypoint connect context allowing to access connection-level information and interrupt the connection.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onEntrypointConnect(final EntrypointConnectContext ctx) {
        return Completable.complete();
    }
}
