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
package io.gravitee.gateway.reactive.api.qos;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
@Getter
public enum Qos {
    /**
     * This QoS level allows a high throughput and good performance but without any warranty regarding the delivery.
     * After a failure or any disconnection, client will only receive new messages sent after its reconnection.
     */
    NONE("none"),
    /**
     * This QoS level will adapt the QoS depending on {@link io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnector} and {@link io.gravitee.gateway.reactive.api.connector.endpoint.EndpointConnector} capabilities allowing a trade-off between performance and delivery warranty.
     * Messages could be received 0, 1 or many times.
     */
    AUTO("auto"),
    /**
     * This level of QoS warranty that messages are delivered 0 or once without any duplication.
     * Depending on {@link io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnector} and {@link io.gravitee.gateway.reactive.api.connector.endpoint.EndpointConnector}, performance could be degraded.
     */
    AT_MOST_ONCE("at-most-once"),
    /**
     * This level of QoS warranties that messages are delivered once or many times.
     * This is a good trade-off in terms of performance comparing to {@link Qos#AT_MOST_ONCE} especially when the {@link io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnector} is not able to resume the streams of messages after a failure.
     */
    AT_LEAST_ONCE("at-least-once");

    private static final Map<String, Qos> maps = Map.of(
        NONE.label,
        NONE,
        AUTO.label,
        AUTO,
        AT_MOST_ONCE.label,
        AT_MOST_ONCE,
        AT_LEAST_ONCE.label,
        AT_LEAST_ONCE
    );

    @JsonValue
    private final String label;

    public static Qos fromLabel(final String label) {
        if (label != null) {
            return maps.get(label);
        }
        return null;
    }
}
