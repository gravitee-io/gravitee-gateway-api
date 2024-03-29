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
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class DefaultMessage implements Message {

    public static final String SOURCE_TIMESTAMP = "sourceTimestamp";
    private String id;

    @Builder.Default
    private String correlationId = UUID.randomUUID().toString();

    private String parentCorrelationId;

    @Builder.Default
    private long timestamp = System.currentTimeMillis();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private long sourceTimestamp = timestamp;

    private Map<String, Object> attributes;
    private Map<String, Object> internalAttributes;
    private Map<String, Object> metadata;
    private HttpHeaders headers;
    private Buffer content;
    private boolean error;
    private Runnable ackRunnable;

    public DefaultMessage(final String content) {
        this(); // Due to @Builder.Default annotation
        if (content != null) {
            this.content = Buffer.buffer(content);
        }
    }

    private static Map<String, Object> unmodifiableMetadata(final Map<String, Object> metadata) {
        if (metadata != null) {
            return Collections.unmodifiableMap(metadata);
        } else {
            return Map.of();
        }
    }

    @Override
    public <T> T attribute(final String name) {
        return (T) getOrInitAttribute().get(name);
    }

    @Override
    public <T> List<T> attributeAsList(String name) {
        return ListUtils.toList(getOrInitAttribute().get(name));
    }

    @Override
    public DefaultMessage attribute(final String name, final Object value) {
        getOrInitAttribute().put(name, value);
        return this;
    }

    @Override
    public DefaultMessage removeAttribute(final String name) {
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
    public <T> T internalAttribute(final String name) {
        return (T) getOrInitInternalAttribute().get(name);
    }

    @Override
    public DefaultMessage internalAttribute(final String name, final Object value) {
        getOrInitInternalAttribute().put(name, value);
        return this;
    }

    @Override
    public DefaultMessage removeInternalAttribute(final String name) {
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

    @Override
    public Map<String, Object> metadata() {
        if (metadata == null) {
            metadata = Map.of();
        }
        return metadata;
    }

    @Override
    public HttpHeaders headers() {
        if (headers == null) {
            headers = HttpHeaders.create();
        }
        return headers;
    }

    @Override
    public DefaultMessage content(Buffer content) {
        this.content = content;
        return this;
    }

    @Override
    public DefaultMessage content(String content) {
        if (content != null) {
            this.content(Buffer.buffer(content));
        } else {
            this.content((Buffer) null);
        }

        return this;
    }

    public DefaultMessage metadata(Map<String, Object> metadata) {
        this.metadata = unmodifiableMetadata(metadata);
        return this;
    }

    public void ack() {
        if (ackRunnable != null) {
            ackRunnable.run();
        }
    }

    public static DefaultMessageBuilder builder() {
        return new DefaultMessageBuilder() {
            @Override
            public DefaultMessage build() {
                prebuild();
                return super.build();
            }
        };
    }

    public static class DefaultMessageBuilder {

        // Insert sourceTimestamp into metadata before building the object
        void prebuild() {
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
        }
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
}
