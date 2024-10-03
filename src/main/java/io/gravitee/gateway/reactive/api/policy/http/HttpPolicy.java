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
package io.gravitee.gateway.reactive.api.policy.http;

import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.http.HttpMessageExecutionContext;
import io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext;
import io.gravitee.gateway.reactive.api.policy.base.BasePolicy;
import io.reactivex.rxjava3.core.Completable;

/**
 * A {@link HttpPolicy} allows to define the actions to apply during the different request execution phases of an HTTP request.
 * A policy can override the default behavior (which is, doing nothing at all) to execute during request or response phase or on each message when the policy is able to work at message level.
 * The implemented methods will be called depending on the execution phase:
 * <ul>
 *     <li>{@link ExecutionPhase#REQUEST}: {@link #onRequest(HttpPlainExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#RESPONSE}: {@link #onResponse(HttpPlainExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#MESSAGE_REQUEST}: {@link #onMessageRequest(HttpMessageExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#MESSAGE_RESPONSE}: {@link #onMessageResponse(HttpMessageExecutionContext)}</li>
 * </ul>
 */
public interface HttpPolicy extends BasePolicy {
    /**
     * Define the actions to perform during the {@link ExecutionPhase#REQUEST} phase.
     * The <code>onRequest(HttpSimpleExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     * </p>
     * <b>GOOD</b>, the header is added during the execution, at subscription time.
     * <pre>
     *     Completable onRequest(final HttpSimpleExecutionContext ctx) {
     *         return Completable.fromRunnable(() -> request.headers().set("X-DummyHeader", "dummy"));
     *     }
     * </pre>
     *
     * <b>BAD</b>, the header is added during the build of the policy chain.
     * <pre>
     *     Completable onRequest(final HttpSimpleExecutionContext ctx) {
     *         request.headers().set("X-DummyHeader", "dummy");
     *         return Completable.complete();
     *     }
     * </pre>
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onRequest(final HttpPlainExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#RESPONSE} phase.
     * The <code>onResponse(HttpSimpleExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     * </p>
     * <b>GOOD</b>, the header is added during the execution, at subscription time.
     * <pre>
     *     Completable onResponse(final HttpSimpleExecutionContext ctx) {
     *         return Completable.fromRunnable(() -> response.headers().set("X-DummyHeader", "dummy"));
     *     }
     * </pre>
     *
     * <b>BAD</b>, the header is added during the build of the policy chain.
     * <pre>
     *     Completable onResponse(final HttpSimpleExecutionContext ctx) {
     *         response.headers().set("X-DummyHeader", "dummy");
     *         return Completable.complete();
     *     }
     * </pre>
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onResponse(final HttpPlainExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#MESSAGE_REQUEST} phase.
     * The <code>onMessageRequest(HttpMessageExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     * </p>
     * <b>GOOD</b>, the header is added during the execution, at subscription time.
     * <pre>
     *     Completable onMessageRequest(final HttpMessageExecutionContext ctx) {
     *         return Completable.fromRunnable(() -> request.headers().set("X-DummyHeader", "dummy"));
     *     }
     * </pre>
     *
     * <b>BAD</b>, the header is added during the build of the policy chain.
     * <pre>
     *     Completable onMessageRequest(final HttpMessageExecutionContext ctx) {
     *         request.headers().set("X-DummyHeader", "dummy");
     *         return Completable.complete();
     *     }
     * </pre>
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onMessageRequest(final HttpMessageExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#MESSAGE_RESPONSE} phase.
     * The <code>onMessageResponse(HttpMessageExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     * </p>
     * <b>GOOD</b>, the header is added during the execution, at subscription time.
     * <pre>
     *     Completable onMessageResponse(final HttpMessageExecutionContext ctx) {
     *         return Completable.fromRunnable(() -> response.headers().set("X-DummyHeader", "dummy"));
     *     }
     * </pre>
     *
     * <b>BAD</b>, the header is added during the build of the policy chain.
     * <pre>
     *     Completable onMessageResponse(final HttpMessageExecutionContext ctx) {
     *         response.headers().set("X-DummyHeader", "dummy");
     *         return Completable.complete();
     *     }
     * </pre>
     *
     * @param ctx the current request execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onMessageResponse(final HttpMessageExecutionContext ctx) {
        return Completable.complete();
    }
}
