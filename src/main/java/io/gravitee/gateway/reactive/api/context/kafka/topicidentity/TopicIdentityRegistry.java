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
package io.gravitee.gateway.reactive.api.context.kafka.topicidentity;

import java.util.Optional;
import org.apache.kafka.common.Uuid;

public interface TopicIdentityRegistry {
    /**
     * Find a topic identity by its name
     * @param name the name of the topic.
     * @return Optional of {@link TopicIdentity}
     */
    Optional<TopicIdentity> findByName(String name);

    /**
     * Find a topic identity by its id
     * @param id Uuid of the topic.
     * @return Optional of {@link TopicIdentity}.
     */
    Optional<TopicIdentity> findById(Uuid id);

    /**
     * Put a topic identity in the registry
     * @param topicIdentity {@link TopicIdentity}
     */
    void put(TopicIdentity topicIdentity);
}
