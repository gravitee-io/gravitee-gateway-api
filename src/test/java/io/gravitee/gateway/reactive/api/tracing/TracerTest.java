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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import io.gravitee.node.api.opentelemetry.Span;
import io.vertx.core.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TracerTest {

    private Context vertxContext;
    private io.gravitee.node.api.opentelemetry.Tracer delegate;
    private Span span;

    @BeforeEach
    void setUp() {
        // The dedup only branches on vertxContext being null vs non-null — a bare mock is enough.
        vertxContext = mock(Context.class);
        delegate = mock(io.gravitee.node.api.opentelemetry.Tracer.class);
        span = mock(Span.class);
    }

    @Test
    void should_record_exception_event_on_first_sighting() {
        Tracer tracer = new Tracer(vertxContext, delegate);
        RuntimeException ex = new RuntimeException("boom");

        tracer.endOnError(span, ex);

        verify(delegate).endOnError(vertxContext, span, ex);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void should_downgrade_to_status_only_when_same_instance_seen_again() {
        // One Tracer per request, shared across layers via ctx.getTracer(). The deeper span records
        // the event; an outer span on the same request + same exception instance gets status-only.
        Tracer tracer = new Tracer(vertxContext, delegate);
        RuntimeException ex = new RuntimeException("boom");
        Span outerSpan = mock(Span.class);

        tracer.endOnError(span, ex);
        tracer.endOnError(outerSpan, ex);

        verify(delegate).endOnError(vertxContext, span, ex);
        verify(delegate).endOnError(vertxContext, outerSpan, "boom");
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void should_record_event_for_each_distinct_instance() {
        Tracer tracer = new Tracer(vertxContext, delegate);
        RuntimeException first = new RuntimeException("a");
        // A layer that wraps + rethrows produces a new instance — its own stack trace is worth its own event.
        IllegalStateException wrapped = new IllegalStateException("b", first);
        Span wrapperSpan = mock(Span.class);

        tracer.endOnError(span, first);
        tracer.endOnError(wrapperSpan, wrapped);

        verify(delegate).endOnError(vertxContext, span, first);
        verify(delegate).endOnError(vertxContext, wrapperSpan, wrapped);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void should_fall_back_to_class_name_when_message_is_null() {
        Tracer tracer = new Tracer(vertxContext, delegate);
        RuntimeException ex = new RuntimeException(); // null message
        Span outerSpan = mock(Span.class);

        tracer.endOnError(span, ex);
        tracer.endOnError(outerSpan, ex);

        verify(delegate).endOnError(vertxContext, span, ex);
        verify(delegate).endOnError(vertxContext, outerSpan, RuntimeException.class.getName());
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void should_not_share_dedup_state_across_distinct_tracer_instances() {
        // Two Tracer instances model two requests — each has its own dedup set, so the same exception
        // instance is recorded independently by each.
        RuntimeException ex = new RuntimeException("boom");

        new Tracer(vertxContext, delegate).endOnError(span, ex);
        new Tracer(vertxContext, delegate).endOnError(span, ex);

        verify(delegate, org.mockito.Mockito.times(2)).endOnError(vertxContext, span, ex);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void should_not_dedup_when_context_is_null() {
        // Null context = the shared no-op Tracer singleton (tracing disabled). It has no request scope,
        // so dedup is skipped and every call records — never accumulating throwables on the singleton.
        Tracer noopLike = new Tracer(null, delegate);
        RuntimeException ex = new RuntimeException("boom");
        Span outerSpan = mock(Span.class);

        noopLike.endOnError(span, ex);
        noopLike.endOnError(outerSpan, ex);

        verify(delegate).endOnError(null, span, ex);
        verify(delegate).endOnError(null, outerSpan, ex);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void should_pass_null_throwable_through_unchanged() {
        Tracer tracer = new Tracer(vertxContext, delegate);

        tracer.endOnError(span, (Throwable) null);

        verify(delegate).endOnError(vertxContext, span, (Throwable) null);
        verifyNoMoreInteractions(delegate);
    }
}
