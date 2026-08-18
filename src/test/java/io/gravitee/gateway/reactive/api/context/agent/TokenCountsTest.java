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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TokenCountsTest {

    private static final TokenCounts A_TURN = new TokenCounts(1000, 700, 50, 200, 30, 4);

    @Test
    void adds_and_subtracts_back_to_where_it_started() {
        TokenCounts other = new TokenCounts(10, 4, 1, 5, 2, 1);

        assertThat(A_TURN.plus(other).minus(other)).isEqualTo(A_TURN);
    }

    @Test
    void leaves_the_part_a_total_holds_beyond_what_is_already_accounted_for() {
        // The standalone path's case: langchain4j reports the whole loop, the intermediates are already counted, and
        // what is left is the final call.
        TokenCounts intermediates = new TokenCounts(800, 600, 50, 150, 20, 3);

        assertThat(A_TURN.minus(intermediates)).isEqualTo(new TokenCounts(200, 100, 0, 50, 10, 1));
    }

    @Test
    void never_reports_a_negative_count() {
        // No consumer of a gen_ai.usage.* attribute could read one, so an inconsistent provider is clamped rather than
        // propagated.
        assertThat(new TokenCounts(10, 0, 0, 2, 0, 1).minus(A_TURN)).isEqualTo(TokenCounts.NONE);
    }

    @Test
    void a_remainder_of_nothing_reads_as_unmeasured_rather_than_free() {
        assertThat(A_TURN.minus(A_TURN).measured()).isFalse();
    }
}
