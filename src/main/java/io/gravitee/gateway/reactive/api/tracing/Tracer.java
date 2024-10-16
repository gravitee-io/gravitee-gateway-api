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

import io.gravitee.common.service.AbstractService;
import io.gravitee.node.api.opentelemetry.Span;
import io.vertx.core.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
@Slf4j
public class Tracer {

    private final Context vertxContext;
    private final io.gravitee.node.api.opentelemetry.Tracer delegate;

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

    public void endOnError(final Span span, final Throwable throwable) {
        delegate.endOnError(vertxContext, span, throwable);
    }

    public void endOnError(final Span span, final String message) {
        delegate.endOnError(vertxContext, span, message);
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
}
