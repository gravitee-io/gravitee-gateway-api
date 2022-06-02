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
package io.gravitee.gateway.reactive.api.policy;

import io.gravitee.gateway.reactive.api.context.ExecutionContext;
import io.gravitee.gateway.reactive.api.context.RequestExecutionContext;
import io.gravitee.gateway.reactive.api.message.Message;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * A {@link Policy} allows to define the actions to apply during the different request execution phases.
 * A policy can override the default behavior (which is, doing nothing at all) to execute during request or response phase or on each message when the policy is able to work at message level.
 * The implemented methods will be called depending on the execution phase:
 * <ul>
 *     <li>{@link io.gravitee.gateway.reactive.api.ExecutionPhase#REQUEST}: {@link #onRequest(RequestExecutionContext)}</li>
 *     <li>{@link io.gravitee.gateway.reactive.api.ExecutionPhase#RESPONSE}: {@link #onResponse(RequestExecutionContext)}</li>
 *     <li>{@link io.gravitee.gateway.reactive.api.ExecutionPhase#ASYNC_REQUEST}: {@link #onRequest(RequestExecutionContext)} and {@link #onMessageFlow(ExecutionContext, Flowable)}</li>
 *     <li>{@link io.gravitee.gateway.reactive.api.ExecutionPhase#ASYNC_RESPONSE}: {@link #onResponse(RequestExecutionContext)} and {@link #onMessageFlow(ExecutionContext, Flowable)}</li>
 * </ul>
 *
 * For {@link io.gravitee.gateway.reactive.api.ExecutionPhase#ASYNC_REQUEST} and {@link io.gravitee.gateway.reactive.api.ExecutionPhase#ASYNC_RESPONSE}, the {@link #onMessage(ExecutionContext, Message)} method will be called for each incoming message.
 */
public interface Policy {
    /**
     * The id of the policy (usually the same id as defined in the policy manifest)
     *
     * @return the id of the policy.
     */
    String id();

    /**
     * Define the actions to perform during the {@link io.gravitee.gateway.reactive.api.ExecutionPhase#REQUEST} phase.
     * The <code>onRequest(RequestExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     * </p>
     * <b>GOOD</b>, the header is added during the execution, at subscription time.
     * <pre>
     *     Completable onRequest(final RequestExecutionContext ctx) {
     *         return Completable.fromRunnable(() -> request.headers().set("X-DummyHeader", "dummy"));
     *     }
     * </pre>
     *
     * <b>BAD</b>, the header is added during the build of the policy chain.
     * <pre>
     *     Completable onRequest(final RequestExecutionContext ctx) {
     *         request.headers().set("X-DummyHeader", "dummy");
     *         return Completable.complete();
     *     }
     * </pre>
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onRequest(final RequestExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link io.gravitee.gateway.reactive.api.ExecutionPhase#RESPONSE} phase.
     * The <code>onResponse(RequestExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     * </p>
     * <b>GOOD</b>, the header is added during the execution, at subscription time.
     * <pre>
     *     Completable onResponse(final RequestExecutionContext ctx) {
     *         return Completable.fromRunnable(() -> response.headers().set("X-DummyHeader", "dummy"));
     *     }
     * </pre>
     *
     * <b>BAD</b>, the header is added during the build of the policy chain.
     * <pre>
     *     Completable onResponse(final RequestExecutionContext ctx) {
     *         response.headers().set("X-DummyHeader", "dummy");
     *         return Completable.complete();
     *     }
     * </pre>
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onResponse(final RequestExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Defines the actions to perform during on each incoming {@link Message}.
     *
     * @param ctx the current execution context allowing to access context attributes.
     *
     * @return a {@link Maybe} that must complete when all the actions have been performed on the message. The message can be simply dropped by returning {@link Maybe#empty()}.
     */
    default Maybe<Message> onMessage(final ExecutionContext ctx, final Message message) {
        return Maybe.just(message);
    }

    /**
     * Defines the actions to perform on the message flow itself.
     *
     * @param ctx the current execution context allowing to access context attributes.
     *
     * @return a {@link Flowable} of {@link Message}.
     */
    default Flowable<Message> onMessageFlow(final ExecutionContext ctx, final Flowable<Message> messageFlow) {
        return messageFlow;
    }
}
