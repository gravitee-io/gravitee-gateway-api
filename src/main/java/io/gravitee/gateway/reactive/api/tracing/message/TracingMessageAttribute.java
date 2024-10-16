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
package io.gravitee.gateway.reactive.api.tracing.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * See {@link <a href="https://opentelemetry.io/docs/specs/semconv/messaging/messaging-spans/">Semantic Conventions Messaging</a>}
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum TracingMessageAttribute {
    /*
     * Required attributes
     */
    MESSAGING_OPERATION_NAME("messaging.operation.name"),
    MESSAGING_SYSTEM("messaging.system"),
    /*
     * Recommended attributes
     */
    MESSAGING_CLIENT_ID("messaging.client.id"),
    MESSAGING_DESTINATION_PARTITION_ID("messaging.destination.partition.id"),
    MESSAGING_MESSAGE_CONVERSATION_ID("messaging.message.conversation_id"), // Correlation id
    MESSAGING_MESSAGE_ID("messaging.message.id"),
    NETWORK_PEER_ADDRESS("network.peer.address"),
    NETWORK_PEER_PORT("network.peer.port"),
    SERVER_PORT("server.port"),

    /*
     * Conditional attributes
     */
    ERROR_TYPE("error.type"), // If and only if the messaging operation has failed.
    MESSAGING_BATCH_MESSAGE_COUNT("messaging.batch.message_count"), // Only if more than single message is processed
    MESSAGING_CONSUMER_GROUP_NAME("messaging.consumer.group.name"),
    MESSAGING_DESTINATION_ANONYMOUS("messaging.destination.anonymous"),
    MESSAGING_DESTINATION_NAME("messaging.destination.name"),
    MESSAGING_DESTINATION_SUBSCRIPTION_NAME("messaging.destination.subscription.name"),
    MESSAGING_DESTINATION_TEMPLATE("messaging.destination.template"),
    MESSAGING_DESTINATION_TEMPORARY("messaging.destination.temporary"), // if not set, false will be assumed
    MESSAGING_OPERATION_TYPE("messaging.operation.type"),
    SERVER_ADDRESS("server.address"),
    MESSAGING_MESSAGE_BODY_SIZE("messaging.message.body.size"),
    MESSAGING_MESSAGE_ENVELOPE_SIZE("messaging.message.envelope.size");

    private final String key;
}
