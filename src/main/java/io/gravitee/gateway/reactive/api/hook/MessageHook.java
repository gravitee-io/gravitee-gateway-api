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
package io.gravitee.gateway.reactive.api.hook;

import io.gravitee.gateway.reactive.api.ExecutionFailure;
import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.http.HttpExecutionContext;
import io.gravitee.gateway.reactive.api.context.http.HttpMessageExecutionContext;
import io.gravitee.gateway.reactive.api.message.Message;
import io.reactivex.rxjava3.core.Completable;

/**
 * Interface that can be used to add hook behaviour on messages
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface MessageHook extends HttpHook {
    default Completable preMessage(
        final String id,
        final HttpMessageExecutionContext ctx,
        final ExecutionPhase executionPhase,
        final Message message
    ) {
        return Completable.complete();
    }

    default Completable postMessage(
        final String id,
        final HttpMessageExecutionContext ctx,
        final ExecutionPhase executionPhase,
        final Message message
    ) {
        return Completable.complete();
    }

    default Completable errorMessage(
        final String id,
        final HttpMessageExecutionContext ctx,
        final ExecutionPhase executionPhase,
        final Message message,
        final Throwable throwable
    ) {
        return Completable.complete();
    }

    default Completable interruptMessage(
        final String id,
        final HttpMessageExecutionContext ctx,
        final ExecutionPhase executionPhase,
        final Message message
    ) {
        return Completable.complete();
    }

    default Completable interruptMessageWith(
        final String id,
        final HttpMessageExecutionContext ctx,
        final ExecutionPhase executionPhase,
        final Message message,
        final ExecutionFailure failure
    ) {
        return Completable.complete();
    }

    default void cancelMessage(
        final String id,
        final HttpMessageExecutionContext ctx,
        final ExecutionPhase executionPhase,
        final Message message
    ) {
        // Nothing to do by default
    }
}
