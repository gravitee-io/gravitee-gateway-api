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

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.reactive.api.tracing.message.TracingMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class DefaultMessage extends AbstractMessage implements TracingMessage {

    private String id;

    private String correlationId;

    private String parentCorrelationId;

    private HttpHeaders headers;
    private Buffer content;
    private boolean error;
    private Runnable ackRunnable;
    private Runnable endRunnable;
    private boolean ended;
    private Map<String, String> tracingAttributes;

    private DefaultMessage(DefaultMessageBuilder b) {
        super(b.timestamp, b.sourceTimestamp, b.metadata, b.attributes, b.internalAttributes);
        this.attributes = b.attributes;
        this.internalAttributes = b.internalAttributes;
        this.id = b.id;
        if (b.correlationId != null) {
            this.correlationId = b.correlationId;
        } else {
            this.correlationId = UUID.randomUUID().toString();
        }
        this.parentCorrelationId = b.parentCorrelationId;
        this.headers = b.headers;
        this.content = b.content;
        this.error = b.error;
        this.ackRunnable = b.ackRunnable;
        this.endRunnable = b.endRunnable;
        this.ended = b.ended;
        this.tracingAttributes = b.tracingAttributes;
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

    @Override
    public void ack() {
        if (ackRunnable != null) {
            ackRunnable.run();
        }
        if (!this.ended) {
            this.end();
        }
    }

    @Override
    public DefaultMessage doOnEnd(final Runnable runnable) {
        this.endRunnable = runnable;
        return this;
    }

    @Override
    public void end() {
        if (this.endRunnable != null) {
            this.endRunnable.run();
        }
        this.ended = true;
    }

    @Override
    public Map<String, String> tracingAttributes() {
        if (this.tracingAttributes == null) {
            this.tracingAttributes = new HashMap<>();
        }
        return tracingAttributes;
    }

    @Override
    public void addTracingAttribute(final String key, final String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value of tracing attribute cannot be null");
        }
        tracingAttributes.put(key, value);
    }

    public static DefaultMessageBuilder builder() {
        return new DefaultMessageBuilder();
    }

    @Data
    public static class DefaultMessageBuilder {

        // Fields from AbstractMessage
        private long timestamp;
        private long sourceTimestamp;
        private Map<String, Object> metadata;
        private Map<String, Object> attributes;
        private Map<String, Object> internalAttributes;

        // Fields from DefaultMessage
        private String id;
        private String correlationId;
        private String parentCorrelationId;
        private HttpHeaders headers;
        private Buffer content;
        private boolean error;
        private Runnable ackRunnable;
        private Runnable endRunnable;
        private boolean ended;
        private Map<String, String> tracingAttributes;

        public DefaultMessageBuilder content(String content) {
            if (content != null) {
                this.content = Buffer.buffer(content);
            }
            return this;
        }

        public DefaultMessageBuilder content(Buffer content) {
            this.content = content;
            return this;
        }

        public DefaultMessage build() {
            return new DefaultMessage(this);
        }
    }
}
