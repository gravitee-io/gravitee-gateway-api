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

import java.util.List;
import java.util.Set;

/**
 * A selection criterion over the parts of an llm request that will be transmitted to the model.
 * <p>
 * Three axes, each holding a <b>set</b> of accepted values. Values of a same axis are combined with {@code OR},
 * axes are combined with {@code AND}. An axis left {@code null} puts no constraint at all, so a criterion with
 * its three axes {@code null} selects everything that goes to the model, in order.
 * <p>
 * A single criterion cannot express a union across axes: {@code kinds={PROMPT, TOOL_DESCRIPTION}} together with
 * {@code roles={USER}} means "parts that are user-authored <b>and</b> are tool definitions", which no tool
 * definition satisfies. Express unions by passing several criteria to {@link LlmRequest#llmParts(List)}, which
 * combines them with {@code OR} while keeping {@code AND} honest inside each one.
 *
 * <h2>Malformed criteria</h2>
 * A criterion that cannot possibly select anything is rejected at construction with an
 * {@link IllegalArgumentException} rather than silently returning an empty list at runtime:
 * <ul>
 *   <li>an axis constrained to an empty set;</li>
 *   <li>{@code kinds={TOOL_DESCRIPTION}} together with any role constraint, since a tool definition has no role;</li>
 *   <li>{@code kinds={TOOL_DESCRIPTION}} together with {@code sources={DISCUSSION}}, since a tool definition is
 *       always {@link Source#HARNESS}.</li>
 * </ul>
 *
 * <h2>Build once, not per request</h2>
 * The rejection above is only a safety net if it fires when the policy is deployed. A criterion is meant to be
 * built from the policy configuration at initialization and reused for every request; this record is immutable
 * and safe to share across threads. Building one per request turns a configuration mistake into a runtime
 * failure on the client-facing path, which is strictly worse than the empty list it was meant to prevent.
 *
 * @param kinds the accepted kinds, or {@code null} for no constraint on this axis.
 * @param sources the accepted sources, or {@code null} for no constraint on this axis.
 * @param roles the accepted roles, or {@code null} for no constraint on this axis.
 *
 * @see LlmRequest#llmParts(List)
 * @author GraviteeSource Team
 */
public record LlmPartCriteria(Set<Kind> kinds, Set<Source> sources, Set<Role> roles) {
    public LlmPartCriteria {
        kinds = frozen(kinds, "kinds");
        sources = frozen(sources, "sources");
        roles = frozen(roles, "roles");

        if (Set.of(Kind.TOOL_DESCRIPTION).equals(kinds)) {
            if (roles != null) {
                throw new IllegalArgumentException(
                    "kinds=[TOOL_DESCRIPTION] cannot be combined with a role constraint: a tool definition has no role"
                );
            }
            if (sources != null && !sources.contains(Source.HARNESS)) {
                throw new IllegalArgumentException(
                    "kinds=[TOOL_DESCRIPTION] cannot be combined with sources=[DISCUSSION]: a tool definition is always HARNESS"
                );
            }
        }
    }

    /**
     * @return a criterion constraining no axis, selecting everything that will be transmitted to the model.
     */
    public static LlmPartCriteria everything() {
        return new LlmPartCriteria(null, null, null);
    }

    /**
     * Tells whether a given part satisfies every constrained axis of this criterion.
     *
     * @param part the part to test.
     * @return {@code true} if the part is selected by this criterion.
     */
    public boolean matches(LlmContextPart part) {
        return switch (part) {
            case Turn turn -> accepts(kinds, Kind.PROMPT) && accepts(sources, Source.of(turn.role())) && accepts(roles, turn.role());
            // a tool definition has no role, so any role constraint excludes it (strict AND).
            case LlmContextPart.ToolDefinition ignored -> accepts(kinds, Kind.TOOL_DESCRIPTION) &&
            accepts(sources, Source.HARNESS) &&
            roles == null;
        };
    }

    private static <T> boolean accepts(Set<T> axis, T value) {
        return axis == null || axis.contains(value);
    }

    private static <T> Set<T> frozen(Set<T> axis, String name) {
        if (axis == null) {
            return null;
        }
        if (axis.isEmpty()) {
            throw new IllegalArgumentException(name + " must not be empty: use null to put no constraint on this axis");
        }
        return Set.copyOf(axis);
    }

    /**
     * What a part is. Closed, because it maps one to one onto the variants of the sealed
     * {@link LlmContextPart}.
     */
    public enum Kind {
        /** A message of the conversation history, see {@link Turn}. */
        PROMPT,
        /** A tool definition made available to the model, see {@link LlmContextPart.ToolDefinition}. */
        TOOL_DESCRIPTION,
    }

    /**
     * Who authored a part.
     * <p>
     * Normalized, never read as-is from the payload. Providers disagree on where the system instructions live:
     * Anthropic has a top-level {@code system} field, Gemini a top-level {@code systemInstruction}, OpenAI an
     * inline message in {@code messages}. Classifying structurally would mean the same logical prompt is
     * {@code HARNESS} for one client and {@code DISCUSSION} for another, which defeats a vendor-agnostic api.
     * So the classification is semantic and derived from the role, see {@link #of(Role)}.
     */
    public enum Source {
        /** Authored by the tooling: system instructions and tool results. */
        HARNESS,
        /** The human/model exchange: everything else. */
        DISCUSSION;

        /**
         * Classifies a role.
         * <p>
         * {@link Role#SYSTEM} and {@link Role#TOOL} are {@link #HARNESS}. <b>Everything else is
         * {@link #DISCUSSION}</b>, including roles this api does not know ({@code developer}, {@code thinking},
         * whatever a provider ships next) and a {@code null} role.
         * <p>
         * That fallback is a choice of failure direction, not a default. {@link Role} is deliberately open, so
         * unknown roles will happen. A third {@code UNKNOWN} value would land them outside
         * {@code sources={DISCUSSION}}, and a guardrail scanning the discussion would silently miss them.
         * Falling back to {@link #DISCUSSION} lands them on the side that gets inspected.
         *
         * @param role the role to classify, possibly {@code null}.
         * @return the source of a part authored by this role, never {@code null}.
         */
        public static Source of(Role role) {
            return Role.SYSTEM.equals(role) || Role.TOOL.equals(role) ? HARNESS : DISCUSSION;
        }
    }
}
