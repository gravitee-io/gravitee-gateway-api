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

import io.gravitee.gateway.reactive.api.ExecutionFailure;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author GraviteeSource Team
 */
class LlmFailureTest {

    @Test
    void should_keep_its_own_type_while_chaining_inherited_setters() {
        LlmFailure failure = new LlmFailure(429)
            .key("RATE_LIMIT_TOO_MANY_REQUESTS")
            .message("too many requests")
            .parameters(Map.of("limit", 10))
            .metadata(Map.of("param", "model"));

        assertThat(failure.statusCode()).isEqualTo(429);
        assertThat(failure.key()).isEqualTo("RATE_LIMIT_TOO_MANY_REQUESTS");
        assertThat(failure.message()).isEqualTo("too many requests");
        assertThat(failure.parameters()).containsEntry("limit", 10);
        assertThat(failure.metadata()).containsEntry("param", "model");
    }

    @Test
    void should_have_no_metadata_by_default() {
        assertThat(new LlmFailure(500).metadata()).isEmpty();
    }

    @Test
    void should_be_equal_when_both_the_execution_failure_and_the_metadata_match() {
        assertThat(new LlmFailure(400).message("bad").metadata(Map.of("param", "model")))
            .isEqualTo(new LlmFailure(400).message("bad").metadata(Map.of("param", "model")))
            .isNotEqualTo(new LlmFailure(400).message("bad").metadata(Map.of("param", "messages")))
            .isNotEqualTo(new LlmFailure(400).message("other").metadata(Map.of("param", "model")));
    }

    @Test
    void should_not_be_equal_to_a_plain_execution_failure() {
        assertThat(new LlmFailure(400).message("bad")).isNotEqualTo(new ExecutionFailure(400).message("bad"));
    }
}
