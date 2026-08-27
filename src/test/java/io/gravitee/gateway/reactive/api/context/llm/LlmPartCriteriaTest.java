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

import io.gravitee.gateway.reactive.api.context.llm.LlmPartCriteria.Kind;
import io.gravitee.gateway.reactive.api.context.llm.LlmPartCriteria.Source;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

/**
 * @author GraviteeSource Team
 */
class LlmPartCriteriaTest {

    private static final Turn SYSTEM_MESSAGE = message(Role.SYSTEM, "You are terse.");
    private static final Turn USER_MESSAGE = message(Role.USER, "Call me at 0102030405");
    private static final Turn ASSISTANT_MESSAGE = message(Role.ASSISTANT, "Noted.");
    private static final Turn TOOL_MESSAGE = message(Role.TOOL, "{\"ok\":true}");
    private static final Turn UNKNOWN_ROLE_MESSAGE = message(Role.of("developer"), "Ship it.");
    private static final LlmContextPart.ToolDefinition TOOL_DEFINITION = new LlmContextPart.ToolDefinition(
        "get_weather",
        "Returns the weather",
        null,
        null
    );

    @Nested
    class Selecting {

        @Test
        void should_select_everything_when_no_axis_is_constrained() {
            var criteria = LlmPartCriteria.everything();

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(criteria.matches(USER_MESSAGE)).as("a message").isTrue();
                softly.assertThat(criteria.matches(TOOL_DEFINITION)).as("a tool definition").isTrue();
            });
        }

        @Test
        void should_combine_the_values_of_a_same_axis_with_or() {
            var criteria = new LlmPartCriteria(null, null, Set.of(Role.USER, Role.TOOL));

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(criteria.matches(USER_MESSAGE)).as("user").isTrue();
                softly.assertThat(criteria.matches(TOOL_MESSAGE)).as("tool").isTrue();
                softly.assertThat(criteria.matches(ASSISTANT_MESSAGE)).as("assistant").isFalse();
            });
        }

        @Test
        void should_combine_the_axes_with_and() {
            // system is HARNESS, so constraining the source to DISCUSSION excludes it even though its role matches.
            var criteria = new LlmPartCriteria(null, Set.of(Source.DISCUSSION), Set.of(Role.SYSTEM, Role.USER));

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(criteria.matches(SYSTEM_MESSAGE)).as("system, excluded by the source").isFalse();
                softly.assertThat(criteria.matches(USER_MESSAGE)).as("user").isTrue();
            });
        }

        @Test
        void should_never_select_a_tool_definition_when_a_role_is_constrained() {
            var criteria = new LlmPartCriteria(null, null, Set.of(Role.USER));

            assertThat(criteria.matches(TOOL_DEFINITION)).isFalse();
        }

        @Test
        void should_select_the_tool_definitions_only() {
            var criteria = new LlmPartCriteria(Set.of(Kind.TOOL_DESCRIPTION), null, null);

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(criteria.matches(TOOL_DEFINITION)).as("a tool definition").isTrue();
                softly.assertThat(criteria.matches(SYSTEM_MESSAGE)).as("a message").isFalse();
            });
        }

        @Test
        void should_select_the_messages_only() {
            var criteria = new LlmPartCriteria(Set.of(Kind.PROMPT), null, null);

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(criteria.matches(SYSTEM_MESSAGE)).as("a message").isTrue();
                softly.assertThat(criteria.matches(TOOL_DEFINITION)).as("a tool definition").isFalse();
            });
        }
    }

    @Nested
    class Classifying_a_source {

        @ParameterizedTest
        @MethodSource("io.gravitee.gateway.reactive.api.context.llm.LlmPartCriteriaTest#harnessRoles")
        void should_classify_the_tooling_roles_as_harness(Role role) {
            assertThat(Source.of(role)).isEqualTo(Source.HARNESS);
        }

        @ParameterizedTest
        @NullSource
        @MethodSource("io.gravitee.gateway.reactive.api.context.llm.LlmPartCriteriaTest#discussionRoles")
        void should_classify_every_other_role_as_discussion_including_the_unknown_ones(Role role) {
            assertThat(Source.of(role)).isEqualTo(Source.DISCUSSION);
        }

        @Test
        void should_land_an_unknown_role_on_the_side_that_gets_inspected() {
            var discussion = new LlmPartCriteria(null, Set.of(Source.DISCUSSION), null);

            assertThat(discussion.matches(UNKNOWN_ROLE_MESSAGE)).isTrue();
        }
    }

    @Nested
    class Rejecting_a_malformed_criterion {

        @Test
        void should_reject_an_empty_kinds() {
            assertThatThrownBy(() -> new LlmPartCriteria(Set.of(), null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("kinds");
        }

        @Test
        void should_reject_an_empty_sources() {
            assertThatThrownBy(() -> new LlmPartCriteria(null, Set.of(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("sources");
        }

        @Test
        void should_reject_an_empty_roles() {
            assertThatThrownBy(() -> new LlmPartCriteria(null, null, Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("roles");
        }

        @Test
        void should_reject_tool_definitions_combined_with_a_role() {
            assertThatThrownBy(() -> new LlmPartCriteria(Set.of(Kind.TOOL_DESCRIPTION), null, Set.of(Role.USER)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a tool definition has no role");
        }

        @Test
        void should_reject_tool_definitions_combined_with_the_discussion_source() {
            assertThatThrownBy(() -> new LlmPartCriteria(Set.of(Kind.TOOL_DESCRIPTION), Set.of(Source.DISCUSSION), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("always HARNESS");
        }

        @Test
        void should_accept_tool_definitions_combined_with_the_harness_source() {
            var criteria = new LlmPartCriteria(Set.of(Kind.TOOL_DESCRIPTION), Set.of(Source.HARNESS, Source.DISCUSSION), null);

            assertThat(criteria.matches(TOOL_DEFINITION)).isTrue();
        }
    }

    @Test
    void should_not_be_affected_by_a_mutation_of_the_given_axis() {
        var roles = new HashSet<>(Set.of(Role.USER));
        var criteria = new LlmPartCriteria(null, null, roles);

        roles.add(Role.ASSISTANT);

        assertThat(criteria.matches(ASSISTANT_MESSAGE)).isFalse();
    }

    static List<Role> harnessRoles() {
        return List.of(Role.SYSTEM, Role.TOOL);
    }

    static List<Role> discussionRoles() {
        return List.of(Role.USER, Role.ASSISTANT, Role.of("developer"));
    }

    private static Turn message(Role role, String content) {
        return new Turn(role, content, null, null, List.of(), Map.of());
    }
}
