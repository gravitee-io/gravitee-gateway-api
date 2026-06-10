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
package io.gravitee.gateway.reactive.api.tracing;

import io.gravitee.node.api.opentelemetry.Span;
import io.vertx.core.Context;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import lombok.RequiredArgsConstructor;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
public class Tracer {

    private final Context vertxContext;
    private final io.gravitee.node.api.opentelemetry.Tracer delegate;

    /** Throwable instances already recorded as an exception event, tracked by identity. */
    private final Set<Throwable> recordedThrowables = Collections.synchronizedSet(Collections.newSetFromMap(new IdentityHashMap<>()));

    public <R> Span startRootSpanFrom(final R request) {
        return delegate.startRootSpanFrom(vertxContext, request);
    }

    public <R> Span startSpanFrom(final R request) {
        return delegate.startSpanFrom(vertxContext, request);
    }

    public <R> Span startSpanWithParentFrom(final Span parentSpan, final R request) {
        return delegate.startSpanWithParentFrom(vertxContext, parentSpan, request);
    }

    public void end(final Span span) {
        delegate.end(vertxContext, span);
    }

    /**
     * Ends the span on error. Records the exception event only the first time a given throwable
     * instance is seen by this tracer; any later span fed the same instance is ended with a
     * status-only error ({@link #endOnError(Span, String)}) so the failure stays visible without
     * duplicating the event.
     */
    public void endOnError(final Span span, final Throwable throwable) {
        if (vertxContext == null || throwable == null || recordedThrowables.add(throwable)) {
            delegate.endOnError(vertxContext, span, throwable);
        } else {
            delegate.endOnError(vertxContext, span, messageOf(throwable));
        }
    }

    public void endOnError(final Span span, final String message) {
        delegate.endOnError(vertxContext, span, message);
    }

    private static String messageOf(final Throwable throwable) {
        return throwable.getMessage() != null ? throwable.getMessage() : throwable.getClass().getName();
    }

    public <R> void endWithResponse(final Span span, final R response) {
        delegate.endWithResponse(vertxContext, span, response);
    }

    public <R> void endWithResponseAndError(final Span span, final R response, final Throwable throwable) {
        delegate.endWithResponseAndError(vertxContext, span, response, throwable);
    }

    public <R> void endWithResponseAndError(final Span span, final R response, final String message) {
        delegate.endWithResponseAndError(vertxContext, span, response, message);
    }

    public <C> void injectSpanContext(final BiConsumer<String, String> textMapSetter) {
        delegate.injectSpanContext(vertxContext, textMapSetter);
    }

    public <C> void injectSpanContext(final Span span, final BiConsumer<String, String> textMapSetter) {
        delegate.injectSpanContext(vertxContext, span, textMapSetter);
    }

    public String traceId() {
        return delegate.traceId(vertxContext);
    }

    public String spanId() {
        return delegate.spanId(vertxContext);
    }
}
