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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author GraviteeSource Team
 */
class StopReasonTest {

    @ParameterizedTest
    @ValueSource(strings = { "stop", "STOP", "Stop" })
    void should_reuse_the_well_known_constant_whatever_the_case(String value) {
        assertThat(StopReason.of(value)).isSameAs(StopReason.STOP);
    }

    @Test
    void should_keep_an_unknown_reason_as_is() {
        StopReason refusal = StopReason.of("refusal");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(refusal.value()).as("raw value").isEqualTo("refusal");
            softly.assertThat(refusal).as("equality, whatever the case").isEqualTo(StopReason.of("REFUSAL"));
            softly.assertThat(refusal).as("hash code, whatever the case").hasSameHashCodeAs(StopReason.of("Refusal"));
        });
    }

    @Test
    void should_not_equal_another_reason() {
        assertThat(StopReason.of("length")).isEqualTo(StopReason.LENGTH).isNotEqualTo(StopReason.STOP);
    }

    @Test
    void should_reject_a_null_value() {
        assertThatThrownBy(() -> StopReason.of(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_expose_the_raw_value_as_string() {
        assertThat(StopReason.CONTENT_FILTER).hasToString("content_filter");
    }
}
