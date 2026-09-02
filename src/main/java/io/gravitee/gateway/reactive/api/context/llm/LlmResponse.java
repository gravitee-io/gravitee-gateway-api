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
import java.util.List;
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
     *  <li>This changes what a reader observes of an answer the model produced. Writing a response <b>in the
     *      model's place</b>, with nothing upstream to patch, is {@link #answer(Turn)}.</li>
     * </ul>
     *
     * @see #onDelta(Function)
     * @see #onDeltas(FlowableTransformer)
     * @see #answer(Turn)
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
     * Answers in the model's place: the given {@link Turn} becomes what the client reads, written exactly as if
     * the model had produced it.
     * <p>
     * Deliberately distinct from {@link #deltas(Flowable)}. Replacing the delta flow patches the text of an
     * answer the model really produced, and only the text: everything else that answer carries (reasoning
     * signatures, cache markers, the vendor cargo the client-facing side rebuilds the provider's format from) is
     * left untouched, because it is still true of the answer being sent. Answering in the model's place is the
     * opposite situation: there is nothing to preserve, possibly no upstream answer at all, and the whole
     * response has to be fabricated. Use this one whenever the client has to read something the model did not
     * say: an answer served from a cache, a canned reply that is not an error, a response a policy produces on
     * its own.
     * <p>
     * Distinct from {@link LlmExecutionContext#interrupt()} as well, which terminates the call: interrupting
     * writes an <b>error</b> body, with an error status and the response templates configured on the api. This
     * writes a <b>successful</b> answer, so the call keeps the status a completed one has and the chain keeps
     * running: the response policies placed after still observe the turn, and still get to scan or transform it.
     * <p>
     * Called during {@link io.gravitee.gateway.reactive.api.policy.llm.LlmPolicy#onRequest(LlmExecutionContext)},
     * it short-circuits the model: no upstream call is made, and the given turn is what the response phase, then
     * the client, see. Called during
     * {@link io.gravitee.gateway.reactive.api.policy.llm.LlmPolicy#onResponse(LlmExecutionContext)}, it discards
     * what the model answered, so it only applies as long as nothing has been flushed downstream.
     * <p>
     * <b>Whether the response is streamed is none of the policy's business.</b> Only the gateway knows whether
     * the client asked for a stream, and it encodes the turn accordingly: one complete body for a plain call, or
     * the chunk sequence the provider's stream format expects, terminal markers included, for a streamed one.
     * The very same call therefore serves both, and a policy that tried to tell them apart would only get the
     * chance to get it wrong.
     * <p>
     * <b>The gateway owns the status code and the framing headers.</b> An answer is a completed call, so it is
     * written with the status and the content type a completed call has, and with the framing a streaming client
     * expects when there is one: a policy cannot leave a client with a response it cannot parse as its
     * provider's format. A policy that needs another status is not answering, it is failing, and says so with
     * {@link LlmExecutionContext#interruptWith(io.gravitee.gateway.reactive.api.ExecutionFailure)} and an
     * {@link LlmFailure}. Headers the policy set itself are otherwise left alone.
     * <p>
     * <b>Ordering</b>, so that combining this with the delta operations stays predictable:
     * <ul>
     *   <li>Everything registered <b>before</b> goes away with the answer it targeted: a
     *       {@link #onDeltas(FlowableTransformer)} transformer, a flow set through {@link #deltas(Flowable)}, an
     *       earlier {@code answer}. They were transforming a response that is no longer the one being sent.</li>
     *   <li>Everything that comes <b>after</b> behaves exactly as it would on a model-produced answer:
     *       {@link #deltas()}, {@link #turns()} and {@link #aggregated()} report the given turn, and a
     *       transformer registered afterwards applies to it. A guardrail placed after the policy that answers
     *       still inspects what the client will actually read.</li>
     * </ul>
     * <p>
     * <b>What the response reports afterwards:</b> {@link #failure()} stays empty and {@link #stopReason()} is
     * derived from the turn ({@link StopReason#TOOL_CALLS} when it carries tool calls, {@link StopReason#STOP}
     * otherwise), a fabricated answer being a successful one. {@link #usage()} keeps reporting what the provider
     * reported, hence nothing when no upstream call was made: the gateway does not invent token counts for text
     * no provider billed.
     *
     * @param turn the answer to write. Its {@link Turn#role()} must be {@link Role#ASSISTANT}, it standing for
     *             what the model would have authored. Its {@link Turn#content()} and its {@link Turn#toolCalls()}
     *             are both written, the latter as tool calls the client is expected to run; {@link Turn#name()}
     *             and {@link Turn#toolCallId()} carry no meaning on an assistant turn and are ignored, and
     *             {@link Turn#metadata()} is carried over only where the target format has somewhere to put it.
     * @return a {@link Completable} that completes once the answer has been set as the response (not written on
     * the wire yet), and that fails when the turn cannot be answered with: a role other than
     * {@link Role#ASSISTANT}, or a response already flushed downstream.
     */
    Completable answer(final Turn turn);

    /**
     * Answers in the model's place with a plain assistant message, the common case of {@link #answer(Turn)}: a
     * cached answer, a canned reply, anything a policy writes as a single block of text and no tool call.
     * <p>
     * Everything {@link #answer(Turn)} documents applies unchanged, streaming included: the gateway decides how
     * this text reaches the client.
     *
     * @param content the text the client will read, as the model would have written it. Must not be {@code null}.
     * @return a {@link Completable} that completes once the answer has been set as the response.
     */
    default Completable answer(final String content) {
        return answer(new Turn(Role.ASSISTANT, content, null, null, List.of(), Map.of()));
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
