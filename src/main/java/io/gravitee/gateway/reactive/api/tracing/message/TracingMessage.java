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

import static io.gravitee.gateway.reactive.api.context.InternalContextAttributes.ATTR_INTERNAL_MESSAGE_RECORDABLE;
import static io.gravitee.gateway.reactive.api.context.InternalContextAttributes.ATTR_INTERNAL_TRACING_MESSAGE_SPAN;

import io.gravitee.gateway.reactive.api.message.Message;
import io.gravitee.node.api.opentelemetry.Span;
import java.util.Map;

/**
 * This interface defines a {@link Message} compatible with tracing
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface TracingMessage extends Message {
    default boolean isTraceable() {
        Boolean isTraceable = internalAttribute(ATTR_INTERNAL_MESSAGE_RECORDABLE);
        return Boolean.TRUE.equals(isTraceable);
    }

    /**
     * Behavior to apply when the underlying span will be ended.
     *
     * @param runnable
     */
    TracingMessage doOnEnd(final Runnable runnable);

    /**
     * Run the end behavior defined by {@link TracingMessage#doOnEnd(Runnable)}
     */
    void end();

    /**
     * Return tracing attributes based on Semantic Conventions for Messaging
     * @return map of tracing attributes
     */
    Map<String, String> tracingAttributes();

    /**
     * Allow to add extra tracing attribute used to trace this message
     * @param key the key of the span attribute
     * @param value  the value associated
     */
    void addTracingAttribute(final String key, final String value);
}
