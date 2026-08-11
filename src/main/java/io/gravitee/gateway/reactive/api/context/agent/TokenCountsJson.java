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

import io.vertx.core.json.JsonObject;

/**
 * The wire form of {@link TokenCounts}, next to the type it serialises so every entrypoint reaches it from the
 * gateway's shared classpath — the same way they reach {@link AgentEvent} — rather than bundling a copy or depending
 * on another plugin's. Shared so a client sees the same {@code usage} object
 * wherever it appears — under a run's terminal frame or a sub-agent's — and whichever provider served the call:
 *
 * <pre>
 * "usage": { "inputTokens": 1000, "cacheReadInputTokens": 700, "cacheCreationInputTokens": 0,
 *            "outputTokens": 200, "reasoningOutputTokens": 0, "llmCalls": 4 }
 * </pre>
 *
 * <p>Field names mirror the OpenTelemetry GenAI attributes the same numbers are stamped with on spans, so a client
 * and a trace can be read side by side. The cache figures are parts of {@code inputTokens} and reasoning is part of
 * {@code outputTokens} — a renderer must not add them to their totals.</p>
 *
 * @author GraviteeSource Team
 */
public final class TokenCountsJson {

    private TokenCountsJson() {}

    /**
     * The {@code usage} object for {@code counts}, or {@code null} when nothing was measured — in which case the
     * caller omits the field entirely rather than publishing zeroes, since no provider reported the run's usage and
     * a zero would read as a run that cost nothing.
     */
    public static JsonObject of(final TokenCounts counts) {
        if (counts == null || !counts.measured()) {
            return null;
        }
        return new JsonObject()
            .put("inputTokens", counts.inputTokens())
            .put("cacheReadInputTokens", counts.cacheReadInputTokens())
            .put("cacheCreationInputTokens", counts.cacheCreationInputTokens())
            .put("outputTokens", counts.outputTokens())
            .put("reasoningOutputTokens", counts.reasoningOutputTokens())
            .put("llmCalls", counts.llmCalls());
    }

    /** Puts the {@code usage} object on {@code payload} when there is one, leaving the field absent otherwise. */
    public static JsonObject putOn(final JsonObject payload, final TokenCounts counts) {
        final JsonObject usage = of(counts);
        return usage != null ? payload.put("usage", usage) : payload;
    }
}
