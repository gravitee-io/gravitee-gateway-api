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
package io.gravitee.gateway.reactive.api.message.kafka;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.reactive.api.message.Message;
import java.util.Map;

public interface KafkaMessage extends Message {
    /**
     * FIXME: Refactor/remove this method when {@link Message#headers() } are http agnostic
     * Get the Kafka record headers associated to the message.
     * @return the Kafka headers
     */
    Map<String, Buffer> recordHeaders();

    /**
     * FIXME: Refactor/remove this method when {@link Message#headers(HttpHeaders) } are http agnostic
     * Put a Kafka record header to the message.
     * @param key the header key
     * @param value the header value
     * @return reference to itself for easily chain calls.
     */
    KafkaMessage putRecordHeader(String key, Buffer value);

    /**
     * FIXME: Refactor/remove this method when {@link Message#headers(HttpHeaders) } are http agnostic
     * Remove a Kafka record header from the message.
     * @param key
     * @return
     */
    KafkaMessage removeRecordHeader(String key);

    /**
     * Get the Kafka record key associated to the message.
     * @return the Kafka key
     */
    Buffer key();

    /**
     * Set the Kafka record key associated with the message.
     * Allows modification of the Kafka key.
     *
     * @param key the buffer representing the Kafka key
     * @return reference to itself for easily chain calls.
     */
    KafkaMessage key(final Buffer key);

    /**
     * Overloaded setter for the Kafka key accepting a String.
     *
     * @param key the string representing the Kafka key
     * @return reference to itself for easily chain calls.
     */
    KafkaMessage key(final String key);

    /**
     * Get the Kafka record offset value associated to the message.
     * @return the Kafka offset
     */
    long offset();

    /**
     * Get the Kafka record partition value associated to the message.
     * @return the Kafka partition
     */
    int sequence();

    /**
     * Get the Kafka record indexPartition type value associated to the message.
     * @return the Kafka indexPartition
     */
    int indexPartition();

    /**
     * Get the Kafka record topic value associated to the message.
     * @return the Kafka topic
     */
    String topic();

    @Deprecated
    default void ack() {
        throw new IllegalStateException("Ack not supported for Kafka messages");
    }

    @Deprecated
    default HttpHeaders headers() {
        throw new IllegalStateException("HttpHeaders not supported for Kafka messages");
    }

    @Deprecated
    default Message headers(HttpHeaders headers) {
        throw new IllegalStateException("HttpHeaders not supported for Kafka messages");
    }
}
