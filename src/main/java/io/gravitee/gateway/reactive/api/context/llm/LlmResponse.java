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
 * {@link #deltas()} always exposes the frame(s) as they become available, and {@link #aggregated()} always
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
     * Get the frames of this response as they become available. For a non-streamed call this emits a single,
     * already-complete {@link Turn}. For a streamed call this emits one incremental {@link Turn} per delta (see
     * {@link Turn} for what "incremental" means). In both cases the flow ends on an {@link ErrorFrame} when
     * generation failed, and that frame is then its last element.
     * <b>WARN:</b> you should not keep a direct reference on the delta flow as it could be overridden by others at anytime.
     *
     * @return a {@link Flowable} of {@link Frame} deltas.
     */
    Flowable<Frame> deltas();

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
    void deltas(final Flowable<Frame> deltas);

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
    Completable onDeltas(final FlowableTransformer<Frame, Frame> onDeltas);

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
    default Completable onDelta(Function<Frame, Frame> onDelta) {
        return onDeltas(deltas -> deltas.map(onDelta::apply));
    }

    /**
     * Same as {@link #deltas()}, restricted to the generated turns.
     * <p>
     * For <b>reading</b> only: a consumer that just needs the content (dlp scanning, token counting) has no
     * reason to pattern-match on the frame variant. A consumer that <b>transforms</b> the flow goes through
     * {@link #onDeltas(FlowableTransformer)} and states what it does with an {@link ErrorFrame}, so that a flow
     * ending on a failure is never silently turned into a flow ending normally.
     *
     * @return a {@link Flowable} of the {@link Turn} deltas only.
     */
    default Flowable<Turn> turns() {
        return deltas().ofType(Turn.class);
    }

    /**
     * Get the fully assembled {@link Turn}, buffering {@link #deltas()} internally if the response is streamed.
     * Reflects any transformation applied via {@link #onDeltas(FlowableTransformer)}.
     * <p>
     * Never signals an error: a flow that ended on an {@link ErrorFrame} assembles into the turn built from
     * what was generated before it, possibly empty. What reached the client is what an auditing policy needs to
     * see, whether generation completed or not; whether it failed is read from {@link #failure()}.
     *
     * @return the fully assembled {@link Turn}.
     */
    Single<Turn> aggregated();

    /**
     * @return the reason why generation stopped, normalized onto {@link StopReason}, if any. Reflects the
     * terminal {@link ErrorFrame} when the flow ended on one.
     */
    Optional<StopReason> stopReason();

    /**
     * @return the token usage reported for this response, if any.
     */
    Optional<Usage> usage();

    /**
     * @return the failure this response ended on, if it failed: the terminal {@link ErrorFrame} the flow ended
     * on, carrying the error message and the vendor-specific attributes that came with it.
     * <p>
     * Kept separate from {@link #stopReason()}, which is not restricted to a failure: it also reports why a
     * successful generation stopped. When a failure is present the two agree, the frame's own
     * {@link ErrorFrame#stopReason()} being what {@link #stopReason()} reports.
     */
    Optional<ErrorFrame> failure();

    /**
     * Token usage reported for an llm response.
     * <p>
     * {@code totalTokens} is the total <b>the provider itself reported</b>, never a sum computed from the other
     * components: whether a vendor-specific count sits inside or outside the two primary ones is the provider's
     * own convention, so adding them up would over-count on every provider whose breakdown is inclusive (a
     * cached prompt count, a reasoning completion count). A provider reporting no total leaves it {@code null}.
     *
     * @param promptTokens number of tokens consumed by the prompt.
     * @param completionTokens number of tokens generated in the completion.
     * @param totalTokens the total the provider reported, or {@code null} if it reported none.
     * @param specific others type of tokens vendors specific.
     */
    record Usage(long promptTokens, long completionTokens, Long totalTokens, Map<String, Long> specific) {
        public Usage {
            specific = specific == null ? Map.of() : Map.copyOf(specific);
        }
    }
}
