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
package io.gravitee.gateway.jupiter.api.message;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class DefaultMessage implements Message {

    private String id;
    private Map<String, Object> attributes;
    private Map<String, Object> internalAttributes;
    private Map<String, Object> metadata;
    private HttpHeaders headers;
    private Buffer content;
    private boolean error;
    private Runnable ackRunnable;

    public DefaultMessage(final String content) {
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
    public String id() {
        return id;
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
    public Message content(Buffer content) {
        this.content = content;
        return this;
    }

    @Override
    public Message content(String content) {
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

    public static class DefaultMessageBuilder {

        public DefaultMessageBuilder metadata(final Map<String, Object> metadata) {
            this.metadata = unmodifiableMetadata(metadata);
            return this;
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
