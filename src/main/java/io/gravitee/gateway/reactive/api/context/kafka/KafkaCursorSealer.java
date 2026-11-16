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
package io.gravitee.gateway.reactive.api.context.kafka;

/**
 * Seals a DescribeTopicPartitions cursor naming a topic the client must not see, and opens it again on the next request.
 *
 * <p>A sealed cursor is a topic name carrying the cursor itself, encrypted and authenticated: like a broker cursor, it works
 * on any connection and can be sent again, and the client can neither read nor forge it without the key. Every gateway
 * instance sharing the key opens the cursors sealed by the others; the implementation chooses the key.
 *
 * <p>The owner, for example the policy sealing the cursor, is sealed with it: a policy only opens its own cursors, and lets
 * through the cursors sealed by another one.
 *
 * <p>Obtained with {@code ctx.getComponent(KafkaCursorSealer.class)}.
 */
public interface KafkaCursorSealer {
    /**
     * Prefix of every sealed cursor. A ':' never appears in a topic name, so a sealed cursor never collides with a topic.
     */
    String SEALED_CURSOR_PREFIX = "gravitee-cursor:";

    /**
     * Seals a cursor.
     * @param owner the owner of the cursor, the only one {@link #open(String, String)} gives the cursor back to.
     * @param topicName the topic name of the cursor.
     * @param partitionIndex the partition index of the cursor.
     * @return the sealed cursor, to give to the client as the topic name of the cursor.
     */
    String seal(String owner, String topicName, int partitionIndex);

    /**
     * Opens the topic name of a cursor sent by the client.
     * @param owner the owner opening the cursor.
     * @param topicName the topic name of the cursor sent by the client.
     * @return the outcome, and the cursor when it was sealed by {@code owner}.
     */
    Opened open(String owner, String topicName);

    /**
     * @param topicName the topic name of a cursor.
     * @return {@code true} when the topic name is a sealed cursor, valid or not.
     */
    static boolean isSealed(String topicName) {
        return topicName != null && topicName.startsWith(SEALED_CURSOR_PREFIX);
    }

    /**
     * The outcome of {@link #open(String, String)}.
     * @param status the outcome.
     * @param topicName the topic name of the cursor when {@link Status#OPENED}, otherwise {@code null}.
     * @param partitionIndex the partition index of the cursor when {@link Status#OPENED}, otherwise {@code 0}.
     */
    record Opened(Status status, String topicName, int partitionIndex) {
        public static Opened opened(String topicName, int partitionIndex) {
            return new Opened(Status.OPENED, topicName, partitionIndex);
        }

        public static Opened of(Status status) {
            return new Opened(status, null, 0);
        }
    }

    enum Status {
        /** The topic name is not a sealed cursor. */
        NOT_SEALED,
        /** The cursor was sealed by the owner opening it. */
        OPENED,
        /** The cursor was sealed, with the same key, by another owner. */
        OTHER_OWNER,
        /** The cursor was sealed with another key, or was tampered with. */
        INVALID,
    }
}
