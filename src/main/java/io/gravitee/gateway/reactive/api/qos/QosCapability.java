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
package io.gravitee.gateway.reactive.api.qos;

import com.fasterxml.jackson.annotation.JsonValue;
import io.gravitee.gateway.reactive.api.message.Message;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This enum list the qos capabilities required or supportted respectively by {@link io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnector} and {@link io.gravitee.gateway.reactive.api.connector.endpoint.EndpointConnector}.
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
@Getter
public enum QosCapability {
    /**
     * This means manual acknowledgment are required or supported. Specific action must be done at message level to perform the acknowledgment.
     *
     * @see Message#ack()
     */
    MANUAL_ACK("manual-ack"),
    /**
     * This means auto acknowledgment is required or supported. No specific action is required at message level.
     */
    AUTO_ACK("auto-ack"),
    /**
     * Capability offers a way to recover from an event id.
     * */
    RECOVER("recover");

    private static final Map<String, QosCapability> maps = Map.of(
        MANUAL_ACK.label,
        MANUAL_ACK,
        AUTO_ACK.label,
        AUTO_ACK,
        RECOVER.label,
        RECOVER
    );

    @JsonValue
    private final String label;

    public static QosCapability fromLabel(final String label) {
        if (label != null) {
            return maps.get(label);
        }
        return null;
    }
}
