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
package io.gravitee.gateway.reactive.api.message;

import io.gravitee.common.util.ListUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractMessage implements Message {

    public static final String SOURCE_TIMESTAMP = "sourceTimestamp";

    @Builder.Default
    protected long timestamp = System.currentTimeMillis();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected long sourceTimestamp = timestamp;

    protected Map<String, Object> metadata;
    protected Map<String, Object> attributes;
    protected Map<String, Object> internalAttributes;

    @Override
    public long timestamp() {
        return this.timestamp;
    }

    @Override
    public Map<String, Object> metadata() {
        if (metadata == null) {
            metadata = Map.of();
        }
        return metadata;
    }

    public AbstractMessage metadata(Map<String, Object> metadata) {
        this.metadata = unmodifiableMetadata(metadata);
        return this;
    }

    @Override
    public <T> T attribute(String name) {
        return (T) getOrInitAttribute().get(name);
    }

    @Override
    public <T> List<T> attributeAsList(String name) {
        return ListUtils.toList(getOrInitAttribute().get(name));
    }

    @Override
    public Message attribute(String name, Object value) {
        getOrInitAttribute().put(name, value);
        return this;
    }

    @Override
    public Message removeAttribute(String name) {
        getOrInitAttribute().remove(name);
        return this;
    }

    @Override
    public Set<String> attributeNames() {
        return getOrInitAttribute().keySet();
    }

    @Override
    public <T> Map<String, T> attributes() {
        return Collections.unmodifiableMap((Map<String, T>) getOrInitAttribute());
    }

    @Override
    public <T> T internalAttribute(String name) {
        return (T) getOrInitInternalAttribute().get(name);
    }

    @Override
    public Message internalAttribute(String name, Object value) {
        getOrInitInternalAttribute().put(name, value);
        return this;
    }

    @Override
    public Message removeInternalAttribute(String name) {
        getOrInitInternalAttribute().remove(name);
        return this;
    }

    @Override
    public Set<String> internalAttributeNames() {
        return getOrInitInternalAttribute().keySet();
    }

    @Override
    public <T> Map<String, T> internalAttributes() {
        return Collections.unmodifiableMap((Map<String, T>) getOrInitInternalAttribute());
    }

    private Map<String, Object> getOrInitAttribute() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }
        return this.attributes;
    }

    private Map<String, Object> getOrInitInternalAttribute() {
        if (this.internalAttributes == null) {
            this.internalAttributes = new HashMap<>();
        }
        return this.internalAttributes;
    }

    protected static Map<String, Object> unmodifiableMetadata(final Map<String, Object> metadata) {
        if (metadata != null) {
            return Collections.unmodifiableMap(metadata);
        } else {
            return Map.of();
        }
    }

    public abstract static class AbstractMessageBuilder<C extends AbstractMessage, B extends AbstractMessage.AbstractMessageBuilder<C, B>> {

        // Insert sourceTimestamp into metadata before building the object
        B prebuild() {
            if (!timestamp$set) {
                timestamp(System.currentTimeMillis());
            }
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            if (!metadata.containsKey(SOURCE_TIMESTAMP)) {
                metadata.put(SOURCE_TIMESTAMP, sourceTimestamp != 0L ? sourceTimestamp : timestamp$value);
            }
            metadata = unmodifiableMetadata(metadata);
            return self();
        }
    }
}
