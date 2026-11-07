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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.gravitee.gateway.reactive.api.policy.guardrail.GuardrailVerdict.Finding;
import io.gravitee.gateway.reactive.api.policy.guardrail.GuardrailVerdict.Outcome;
import io.gravitee.gateway.reactive.api.policy.guardrail.GuardrailVerdict.Transformation;
import java.util.List;
import org.junit.jupiter.api.Test;

class GuardrailVerdictTest {

    private static final List<String> REASONS = List.of("matched");
    private static final List<Finding> FINDINGS = List.of(new Finding("aws-key", "messages[1].user"));

    @Test
    void should_build_an_allow() {
        assertThat(GuardrailVerdict.allow("CONTINUE", REASONS, FINDINGS)).isEqualTo(
            new GuardrailVerdict(Outcome.ALLOW, "CONTINUE", REASONS, FINDINGS, null)
        );
    }

    @Test
    void should_build_a_deny() {
        assertThat(GuardrailVerdict.deny("BLOCK", REASONS, FINDINGS)).isEqualTo(
            new GuardrailVerdict(Outcome.DENY, "BLOCK", REASONS, FINDINGS, null)
        );
    }

    @Test
    void should_build_a_transform() {
        assertThat(GuardrailVerdict.transform(Transformation.MASK, "MASK", REASONS, FINDINGS)).isEqualTo(
            new GuardrailVerdict(Outcome.TRANSFORM, "MASK", REASONS, FINDINGS, Transformation.MASK)
        );
    }

    @Test
    void should_read_absent_reasons_and_findings_as_empty() {
        GuardrailVerdict verdict = GuardrailVerdict.allow("CONTINUE", null, null);

        assertThat(verdict.reasons()).isEmpty();
        assertThat(verdict.findings()).isEmpty();
    }

    @Test
    void should_reject_a_null_outcome() {
        assertThatThrownBy(() -> new GuardrailVerdict(null, "BLOCK", REASONS, FINDINGS, null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_reject_a_transformation_on_another_outcome() {
        assertThatThrownBy(() -> new GuardrailVerdict(Outcome.ALLOW, "CONTINUE", REASONS, FINDINGS, Transformation.REDACT)).isInstanceOf(
            IllegalArgumentException.class
        );
    }

    @Test
    void should_reject_a_transform_without_transformation() {
        assertThatThrownBy(() -> GuardrailVerdict.transform(null, "REDACT", REASONS, FINDINGS)).isInstanceOf(
            IllegalArgumentException.class
        );
    }
}
