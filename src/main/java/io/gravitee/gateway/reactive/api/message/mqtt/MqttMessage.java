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
package io.gravitee.gateway.reactive.api.message.mqtt;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.reactive.api.message.Message;
import java.util.Map;

public interface MqttMessage extends Message {
    /**
     * FIXME: Refactor/remove this method when {@link Message#headers() } are http agnostic
     * Get the Mqtt message headers associated to the message.
     * @return the Mqtt headers
     */
    Map<String, Buffer> messageHeaders();

    /**
     * FIXME: Refactor/remove this method when {@link Message#headers(HttpHeaders) } are http agnostic
     * Put a Mqtt message header to the message.
     * @param key the header key
     * @param value the header value
     * @return reference to itself for easily chain calls.
     */
    MqttMessage putMessageHeader(String key, Buffer value);

    /**
     * FIXME: Refactor/remove this method when {@link Message#headers(HttpHeaders) } are http agnostic
     * Remove a Mqtt message header from the message.
     * @param key
     * @return
     */
    MqttMessage removeMessageHeader(String key);

    /**
     * Get the Mqtt message key associated to the message.
     * @return the Mqtt key
     */
    Buffer key();

    /**
     * Set the Mqtt message key associated with the message.
     * Allows modification of the Mqtt key.
     *
     * @param key the buffer representing the Mqtt key
     * @return reference to itself for easily chain calls.
     */
    MqttMessage key(final Buffer key);

    /**
     * Overloaded setter for the Mqtt key accepting a String.
     *
     * @param key the string representing the Mqtt key
     * @return reference to itself for easily chain calls.
     */
    MqttMessage key(final String key);

    /**
     * Get the Mqtt message offset value associated to the message.
     * @return the Mqtt offset
     */
    long offset();

    /**
     * Get the Mqtt message partition value associated to the message.
     * @return the Mqtt partition
     */
    int sequence();

    /**
     * Get the Mqtt message indexPartition type value associated to the message.
     * @return the Mqtt indexPartition
     */
    int indexPartition();

    /**
     * Get the Mqtt message topic value associated to the message.
     * @return the Mqtt topic
     */
    String topic();

    /**
     * Get the size in bytes of that message.
     * @return the size in bytes
     */
    int sizeInBytes();

    @Deprecated
    default void ack() {
        throw new IllegalStateException("Ack not supported for Mqtt messages");
    }

    @Deprecated
    default HttpHeaders headers() {
        throw new IllegalStateException("HttpHeaders not supported for Mqtt messages");
    }

    @Deprecated
    default Message headers(HttpHeaders headers) {
        throw new IllegalStateException("HttpHeaders not supported for Mqtt messages");
    }
}
