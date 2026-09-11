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
package io.gravitee.gateway.reactive.api.context.llm;

import io.gravitee.gateway.reactive.api.context.http.HttpPlainResponse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Single;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * A vendor-agnostic view over an llm response.
 * <p>
 * Whether the upstream provider actually streamed the response or not is transparent to the consumer:
 * {@link #deltas()} always exposes the turn(s) as they become available, and {@link #aggregated()} always
 * exposes the fully assembled turn, buffering deltas internally when needed.
 * <p>
 * An llm response is carried over an underlying plain http response, so this also extends
 * {@link HttpPlainResponse} to expose the raw http-level view (status, headers, raw body, ...) alongside the
 * normalized one.
 *
 * @author GraviteeSource Team
 */
public interface LlmResponse extends HttpPlainResponse {
    /**
     * Get the current turn(s) of this response as they become available. For a non-streamed call this emits a
     * single, already-complete {@link Turn}. For a streamed call this emits one incremental {@link Turn} per
     * delta (see {@link Turn} for what "incremental" means).
     * <b>WARN:</b> you should not keep a direct reference on the delta flow as it could be overridden by others at anytime.
     *
     * @return a {@link Flowable} of {@link Turn} deltas.
     */
    Flowable<Turn> deltas();

    /**
     * Set the current delta flow.
     * <b>WARN:</b>
     * <ul>
     *  <li>Replacing the delta flow <b>DOES NOT</b> take care of the previous delta flow in place.</li>
     *  <li>You <b>MUST</b> ensure to consume the previous delta flow when using it.</li>
     *  <li>You <b>SHOULD</b> consider using {@link #onDeltas(FlowableTransformer)} or {@link #onDelta(Function)} that may be more appropriate for delta transformation.</li>
     * </ul>
     *
     * @see #onDelta(Function)
     * @see #onDeltas(FlowableTransformer)
     */
    void deltas(final Flowable<Turn> deltas);

    /**
     * Applies a given transformation on each delta.
     * Ex, redacting content as it streams to the client:
     * <code>
     *     response.onDeltas(deltas -> deltas.map(delta -> redact(delta)));
     * </code>
     *
     * @param onDeltas the transformer that will be applied on the delta flow.
     * @return a {@link Completable} that completes once the transformation has been set up on the delta flow (not executed).
     */
    Completable onDeltas(final FlowableTransformer<Turn, Turn> onDeltas);

    /**
     * Applies a given transformation on each delta.
     * Ex, discarding a delta:
     * <code>
     *     response.onDelta(delta -> Maybe.empty());
     * </code>
     *
     * @param onDelta the transformer that will be applied on each delta.
     * @return a {@link Completable} that completes once the transformation has been set up on the delta flow (not executed).
     */
    default Completable onDelta(Function<Turn, Turn> onDelta) {
        return onDeltas(deltas -> deltas.map(onDelta::apply));
    }

    /**
     * @return the fully assembled {@link Turn}, buffering {@link #deltas()} internally if the response is streamed.
     * Reflects any transformation applied via {@link #onDeltas(FlowableTransformer)}.
     */
    Single<Turn> aggregated();

    /**
     * @return the reason given by the provider for why generation stopped (ex: {@code "stop"}, {@code "length"}, {@code "tool_calls"}), if any.
     */
    Optional<String> finishReason();

    /**
     * @return the token usage reported for this response, if any.
     */
    Optional<Usage> usage();

    /**
     * @return the provider-reported error message, if the call failed.
     */
    Optional<String> errorMessage();

    /**
     * Token usage reported for an llm response.
     *
     * @param promptTokens number of tokens consumed by the prompt.
     * @param completionTokens number of tokens generated in the completion.
     * @param specific others type of tokens vendors specific.
     */
    record Usage(long promptTokens, long completionTokens, Map<String, Long> specific) {
        Usage(long promptTokens, long completionTokens) {
            this(promptTokens, completionTokens, Map.of());
        }

        public long total() {
            return promptTokens + completionTokens + specific.values().stream().mapToLong(Long::longValue).sum();
        }
    }
}
