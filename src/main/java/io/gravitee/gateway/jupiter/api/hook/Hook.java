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
package io.gravitee.gateway.jupiter.api.hook;

import io.gravitee.gateway.jupiter.api.ExecutionFailure;
import io.gravitee.gateway.jupiter.api.ExecutionPhase;
import io.gravitee.gateway.jupiter.api.context.ExecutionContext;
import io.reactivex.Completable;
import io.reactivex.annotations.Nullable;

/**
 * Interface that can be used to add generic behaviour
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Hook {
    String id();

    default Completable pre(final String id, final ExecutionContext ctx, @Nullable final ExecutionPhase executionPhase) {
        return Completable.complete();
    }

    default Completable post(final String id, final ExecutionContext ctx, @Nullable final ExecutionPhase executionPhase) {
        return Completable.complete();
    }

    default Completable error(
        final String id,
        final ExecutionContext ctx,
        @Nullable final ExecutionPhase executionPhase,
        final Throwable throwable
    ) {
        return Completable.complete();
    }

    default Completable interrupt(final String id, final ExecutionContext ctx, @Nullable final ExecutionPhase executionPhase) {
        return Completable.complete();
    }

    default Completable interruptWith(
        final String id,
        final ExecutionContext ctx,
        @Nullable final ExecutionPhase executionPhase,
        final ExecutionFailure failure
    ) {
        return Completable.complete();
    }
}
