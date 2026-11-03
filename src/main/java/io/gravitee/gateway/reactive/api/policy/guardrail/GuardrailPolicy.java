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
package io.gravitee.gateway.reactive.api.policy.guardrail;

import io.gravitee.gateway.reactive.api.ExecutionPhase;
import io.gravitee.gateway.reactive.api.context.guardrail.GuardrailExecutionContext;
import io.gravitee.gateway.reactive.api.context.guardrail.LlmGuardrailExecutionContext;
import io.gravitee.gateway.reactive.api.policy.base.BasePolicy;
import io.reactivex.rxjava3.core.Completable;

/**
 * A {@link GuardrailPolicy} is a policy of a Guardian: a reusable sequence of guardrails deployed on its own and run
 * by the APIs that enable it, on the request before their own policies or on the response after them. It works
 * against the {@link GuardrailExecutionContext}, whose subtype carries the typed view of the exchange the Guardian
 * protects ({@link LlmGuardrailExecutionContext#llm()} for an LLM proxy API) so that one guardrail reads the
 * conversation without knowing the wire format of the API it runs for.
 * <p>
 * A guardrail decides on its own what happens when it matches: it blocks the call with
 * {@link GuardrailExecutionContext#interruptWith(io.gravitee.gateway.reactive.api.ExecutionFailure)}, reports it
 * through the metrics and logs of the context, or lets it through. A guardrail that modifies the exchange (a
 * redaction) does so through the typed view and lets the call continue.
 * <p>
 * The implemented methods will be called depending on the execution phase of the Guardian:
 * <ul>
 *     <li>{@link ExecutionPhase#REQUEST}: {@link #onRequest(GuardrailExecutionContext)}</li>
 *     <li>{@link ExecutionPhase#RESPONSE}: {@link #onResponse(GuardrailExecutionContext)}</li>
 * </ul>
 *
 * @author GraviteeSource Team
 */
public interface GuardrailPolicy extends BasePolicy {
    /**
     * Define the actions to perform during the {@link ExecutionPhase#REQUEST} phase.
     * The <code>onRequest(GuardrailExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current guardrail execution context allowing to access the typed exchange, the request, the response and the attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onRequest(final GuardrailExecutionContext ctx) {
        return Completable.complete();
    }

    /**
     * Define the actions to perform during the {@link ExecutionPhase#RESPONSE} phase.
     * The <code>onResponse(GuardrailExecutionContext)</code> method will be called during the policy chain construction.
     * Once built, the subscription occurs and the execution is triggered.
     * It is important that <b>nothing must be executed before the subscription occurs</b> as it could lead to important side effects.
     *
     * @param ctx the current guardrail execution context allowing to access the typed exchange, the request, the response and the attributes.
     *
     * @return a {@link Completable} that must complete when all the actions have been performed.
     */
    default Completable onResponse(final GuardrailExecutionContext ctx) {
        return Completable.complete();
    }
}
