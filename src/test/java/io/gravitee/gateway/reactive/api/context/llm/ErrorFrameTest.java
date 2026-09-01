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

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

/**
 * @author GraviteeSource Team
 */
class ErrorFrameTest {

    @Test
    void should_default_a_null_metadata_to_an_empty_one() {
        assertThat(new ErrorFrame(StopReason.ERROR, "boom", null).metadata()).isEmpty();
    }

    @Test
    void should_default_the_metadata_of_the_shorthand_constructor() {
        ErrorFrame frame = new ErrorFrame(StopReason.CONTENT_FILTER, "pii detected");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(frame.stopReason()).as("stop reason").isEqualTo(StopReason.CONTENT_FILTER);
            softly.assertThat(frame.message()).as("message").isEqualTo("pii detected");
            softly.assertThat(frame.metadata()).as("metadata").isEmpty();
        });
    }

    @Test
    void should_not_be_affected_by_a_mutation_of_the_given_metadata() {
        Map<String, Object> metadata = new HashMap<>(Map.of("code", "content_policy_violation"));
        ErrorFrame frame = new ErrorFrame(StopReason.CONTENT_FILTER, "pii detected", metadata);

        metadata.put("code", "tampered");

        assertThat(frame.metadata()).containsExactly(Map.entry("code", "content_policy_violation"));
    }

    @Test
    void should_reject_a_null_stop_reason() {
        assertThatThrownBy(() -> new ErrorFrame(null, "boom")).isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_be_a_frame() {
        assertThat(new ErrorFrame(StopReason.ERROR, "boom")).isInstanceOf(Frame.class);
    }
}
