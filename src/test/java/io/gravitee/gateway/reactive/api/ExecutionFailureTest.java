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
package io.gravitee.gateway.reactive.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
class ExecutionFailureTest {

    @Test
    void should_be_equals() {
        ExecutionFailure failure = new ExecutionFailure(500)
            .key("id")
            .message("message")
            .contentType("text/plain")
            .parameters(Map.of("key", "value"));

        ExecutionFailure failure2 = new ExecutionFailure(500)
            .key("id")
            .message("message")
            .contentType("text/plain")
            .parameters(Map.of("key", "value"));
        assertThat(failure).isEqualTo(failure2);
    }

    @Test
    void should_be_equals_ignoring_cause() {
        ExecutionFailure failure = new ExecutionFailure(500)
            .key("id")
            .message("message")
            .contentType("text/plain")
            .parameters(Map.of("key", "value"))
            .cause(new RuntimeException("cause"));

        ExecutionFailure failure2 = new ExecutionFailure(500)
            .key("id")
            .message("message")
            .contentType("text/plain")
            .parameters(Map.of("key", "value"))
            .cause(new RuntimeException("other cause"));

        assertThat(failure).isEqualTo(failure2);
    }

    @Test
    void should_not_be_equals_when_status_is_different() {
        ExecutionFailure failure = new ExecutionFailure(500).key("id").message("message").contentType("text/plain");
        ExecutionFailure failure2 = new ExecutionFailure(200).key("id").message("message").contentType("text/plain");

        assertThat(failure).isNotEqualTo(failure2);
    }

    @Test
    void should_not_be_equals_when_key_is_different() {
        ExecutionFailure failure = new ExecutionFailure(500).key("id").message("message").contentType("text/plain");
        ExecutionFailure failure2 = new ExecutionFailure(500).key("id2").message("message").contentType("text/plain");

        assertThat(failure).isNotEqualTo(failure2);
    }

    @Test
    void should_not_be_equals_when_message_is_different() {
        ExecutionFailure failure = new ExecutionFailure(500).key("id").message("message").contentType("text/plain");
        ExecutionFailure failure2 = new ExecutionFailure(500).key("id").message("message2").contentType("text/plain");

        assertThat(failure).isNotEqualTo(failure2);
    }

    @Test
    void should_not_be_equals_when_content_type_is_different() {
        ExecutionFailure failure = new ExecutionFailure(500).key("id").message("message").contentType("text/plain");
        ExecutionFailure failure2 = new ExecutionFailure(500).key("id").message("message").contentType("application/json");

        assertThat(failure).isNotEqualTo(failure2);
    }
}
