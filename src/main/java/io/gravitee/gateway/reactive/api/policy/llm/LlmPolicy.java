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
package io.gravitee.gateway.reactive.api.policy.llm;

import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.llm.LlmExecutionContext;
import io.gravitee.gateway.reactive.api.policy.base.BasePolicy;
import io.reactivex.rxjava3.core.Completable;

/**
 * A {@link LlmPolicy} allows to define the actions to apply during the different execution phases of an llm chat
 * call, working against the vendor-agnostic {@link LlmExecutionContext} rather than the provider's raw wire format.
 * <p>
 * Unlike {@link io.gravitee.gateway.reactive.api.policy.http.HttpPolicy}, there is no dedicated message-level
 * phase: whether the upstream call is streamed or not is already made transparent by
 * {@link io.gravitee.gateway.reactive.api.context.llm.LlmResponse}, whose {@code deltas()} and {@code aggregated()}
 * expose the conversation turns as they become available or fully assembled. A policy that needs to act on each
 * streamed delta can do so from {@link #onResponse(LlmExecutionContext)} using
 * {@link io.gravitee.gateway.reactive.api.context.llm.LlmResponse#onDeltas(io.reactivex.rxjava3.core.FlowableTransformer)}.
 * <p>
 * The implemented methods will be called depending on the execution phase:
 * <ul>
 *     <li>{@link ExecutionPhase#REQUEST}: {@link #onRequest(LlmExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#RESPONSE}: {@link #onResponse(LlmExecutionContext)}</li>
 * </ul>
 * <p>
 * {@link LlmExecutionContext} extends {@link io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext},
 * so chain control ({@code interrupt()}, {@code interruptWith(ExecutionFailure)}, ...) is directly available on the
 * context passed to {@link #onRequest(LlmExecutionContext)}/{@link #onResponse(LlmExecutionContext)}.
 *
 * @author GraviteeSource Team
 */
public interface LlmPolicy extends BasePolicy {
    /**
     * Define the actions to perform during the {@link ExecutionPhase#REQUEST} phase.
     * The <code>onRequest(LlmExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current llm execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onRequest(final LlmExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#RESPONSE} phase.
     * The <code>onResponse(LlmExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current llm execution context allowing to access the request, response and attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onResponse(final LlmExecutionContext ctx) {
        return Completable.complete();
    }
}
