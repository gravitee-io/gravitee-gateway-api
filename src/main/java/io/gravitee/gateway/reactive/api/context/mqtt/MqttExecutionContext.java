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
package io.gravitee.gateway.reactive.api.context.mqtt;

import io.gravitee.gateway.reactive.api.context.base.NativeExecutionContext;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.reactivex.rxjava3.core.Completable;

/**
 * Execution context specialized for Mqtt.
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface MqttExecutionContext extends NativeExecutionContext {
    String TEMPLATE_ATTRIBUTE_REQUEST = "request";
    String TEMPLATE_ATTRIBUTE_RESPONSE = "response";

    MqttMessageType messageType();

    int correlationId();

    MqttRequest request();

    MqttResponse response();

    /**
     * Access the connection context.
     * @return the connection context.
     */
    MqttConnectionContext connectionContext();

    /**
     * Interrupts the current execution while indicating that the response can be sent "as is" to the downstream.
     * @param response the  to interrupt the chain with.
     */
    <T extends io.netty.handler.codec.mqtt.MqttMessage> Completable interruptWith(T response);
}
