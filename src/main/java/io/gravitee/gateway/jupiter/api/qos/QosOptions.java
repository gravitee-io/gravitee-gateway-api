/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.jupiter.api.qos;

import io.gravitee.gateway.jupiter.api.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class QosOptions {

    /**
     * This refers to the {@link Qos} defined on the entrypoint by the API Owner.
     * By default, the qos is {@link Qos#BALANCED}
     */
    @Builder.Default
    private Qos qos = Qos.BALANCED;

    /**
     * This option allows an {@link io.gravitee.gateway.jupiter.api.connector.entrypoint.async.EntrypointAsyncConnector} to declare if it supports to recover after an error.
     * This means the last successful message id could be sent back by the client in order to recover from it. This last id must be added to the context using {@link io.gravitee.gateway.jupiter.api.context.InternalContextAttributes#ATTR_INTERNAL_MESSAGES_RECOVERY_LAST_ID}.
     */
    @Builder.Default
    private boolean errorRecoverySupported = false;

    /**
     * This option allows an {@link io.gravitee.gateway.jupiter.api.connector.entrypoint.async.EntrypointAsyncConnector} to declare if it supports to manual acknowledgment of messages.
     * The {@link io.gravitee.gateway.jupiter.api.connector.entrypoint.async.EntrypointAsyncConnector} is responsible to call the appropriate method on each message to acknowledge then, see {@link Message#ack()}
     */
    @Builder.Default
    private boolean manualAckSupported = false;
}
