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
package io.gravitee.gateway.reactive.api.context.agent;

/**
 * What some scope of an agent run consumed, in the breakdown the OpenTelemetry GenAI semantic conventions define.
 * Each component maps to one {@code gen_ai.usage.*} attribute, so a consumer can stamp a span, write a metric or
 * render a UI without inventing a vocabulary of its own:
 *
 * <ul>
 *   <li>{@code inputTokens} → {@code gen_ai.usage.input_tokens}</li>
 *   <li>{@code cacheReadInputTokens} → {@code gen_ai.usage.cache_read.input_tokens}</li>
 *   <li>{@code cacheCreationInputTokens} → {@code gen_ai.usage.cache_creation.input_tokens}</li>
 *   <li>{@code outputTokens} → {@code gen_ai.usage.output_tokens}</li>
 *   <li>{@code reasoningOutputTokens} → {@code gen_ai.usage.reasoning.output_tokens}</li>
 * </ul>
 *
 * <p><b>The details are subsets, not siblings.</b> Both cache figures are part of {@code inputTokens} and
 * {@code reasoningOutputTokens} is part of {@code outputTokens}, exactly as the conventions require — so
 * {@code inputTokens} is always "the input", whatever the provider's own payload looked like, and
 * {@link #uncachedInputTokens()} is the part billed at the standard rate. Whoever adapts a provider is responsible
 * for normalising onto this shape: some report their input total inclusive of the cache figures, others report the
 * uncached remainder and expect them to be added.</p>
 *
 * <p>{@code llmCalls} is not a semantic convention: it is the number of calls made to the model in this scope — one
 * per provider request, so an agent that used three tools before answering made four. It also separates <em>nobody
 * told us</em> from <em>it cost nothing</em>: a provider that reports no usage leaves {@link #measured()} false,
 * while a call that genuinely consumed nothing is measured with zeroes. That distinction is why nothing here is
 * nullable — a run whose usage went unreported must not be summed into a cost view as a free one, and expressing
 * that as a {@code null} would put the burden on every caller to remember.</p>
 *
 * @author GraviteeSource Team
 */
public record TokenCounts(
    int inputTokens,
    int cacheReadInputTokens,
    int cacheCreationInputTokens,
    int outputTokens,
    int reasoningOutputTokens,
    int llmCalls
) {
    /** Nothing measured — the identity of {@link #plus}, and what a scope no model served reports. */
    public static final TokenCounts NONE = new TokenCounts(0, 0, 0, 0, 0, 0);

    /** Whether any call reported its usage. False means unknown, never free. */
    public boolean measured() {
        return llmCalls > 0;
    }

    /** The input neither served from nor written to a provider cache — the part billed at the standard rate. */
    public int uncachedInputTokens() {
        return inputTokens - cacheReadInputTokens - cacheCreationInputTokens;
    }

    /** This and {@code other} together. */
    public TokenCounts plus(final TokenCounts other) {
        return new TokenCounts(
            inputTokens + other.inputTokens,
            cacheReadInputTokens + other.cacheReadInputTokens,
            cacheCreationInputTokens + other.cacheCreationInputTokens,
            outputTokens + other.outputTokens,
            reasoningOutputTokens + other.reasoningOutputTokens,
            llmCalls + other.llmCalls
        );
    }

    /**
     * What this scope holds beyond {@code other} — the inverse of {@link #plus}, for a caller that knows a total and
     * the part of it already accounted for and wants the remainder. Every component is clamped at zero: a provider
     * whose total is inconsistent with its parts must not yield a negative count, which no consumer of a
     * {@code gen_ai.usage.*} attribute would know how to read.
     *
     * <p>A remainder of nothing is {@link #NONE} — {@link #measured()} false — so a leftover no one reported reads as
     * unmeasured rather than as a call that was free.</p>
     */
    public TokenCounts minus(final TokenCounts other) {
        return new TokenCounts(
            Math.max(0, inputTokens - other.inputTokens),
            Math.max(0, cacheReadInputTokens - other.cacheReadInputTokens),
            Math.max(0, cacheCreationInputTokens - other.cacheCreationInputTokens),
            Math.max(0, outputTokens - other.outputTokens),
            Math.max(0, reasoningOutputTokens - other.reasoningOutputTokens),
            Math.max(0, llmCalls - other.llmCalls)
        );
    }
}
