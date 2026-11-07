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

import java.util.List;
import java.util.Objects;

/**
 * What a guardrail concluded on one run. The Guardian turns the verdicts of its guardrails into one decision.
 *
 * @param outcome what the guardrail did to the call.
 * @param verdict the guardrail's own word for it: {@code BLOCK}, {@code CONTINUE}, {@code REDACT}...
 * @param reasons its explanation, in operator words. Never the content of the exchange.
 * @param findings what matched, empty when nothing did.
 * @param transformation how the exchange was rewritten, on a {@link Outcome#TRANSFORM} only.
 *
 * @author GraviteeSource Team
 */
public record GuardrailVerdict(
    Outcome outcome,
    String verdict,
    List<String> reasons,
    List<Finding> findings,
    Transformation transformation
) {
    public enum Outcome {
        ALLOW,
        DENY,
        TRANSFORM,
    }

    public enum Transformation {
        REDACT,
        MASK,
        REWRITE,
    }

    /**
     * What matched: the rule and where, never the text.
     *
     * @param rule the name of the rule that matched.
     * @param location where it matched in the exchange, {@code messages[1].user} for example.
     */
    public record Finding(String rule, String location) {}

    public GuardrailVerdict {
        Objects.requireNonNull(outcome, "outcome");
        if (outcome == Outcome.TRANSFORM && transformation == null) {
            throw new IllegalArgumentException("A TRANSFORM verdict needs a transformation");
        }
        if (outcome != Outcome.TRANSFORM && transformation != null) {
            throw new IllegalArgumentException("Only a TRANSFORM verdict has a transformation");
        }
        reasons = reasons == null ? List.of() : List.copyOf(reasons);
        findings = findings == null ? List.of() : List.copyOf(findings);
    }

    public static GuardrailVerdict allow(String verdict, List<String> reasons, List<Finding> findings) {
        return new GuardrailVerdict(Outcome.ALLOW, verdict, reasons, findings, null);
    }

    public static GuardrailVerdict deny(String verdict, List<String> reasons, List<Finding> findings) {
        return new GuardrailVerdict(Outcome.DENY, verdict, reasons, findings, null);
    }

    public static GuardrailVerdict transform(Transformation transformation, String verdict, List<String> reasons, List<Finding> findings) {
        return new GuardrailVerdict(Outcome.TRANSFORM, verdict, reasons, findings, transformation);
    }
}
