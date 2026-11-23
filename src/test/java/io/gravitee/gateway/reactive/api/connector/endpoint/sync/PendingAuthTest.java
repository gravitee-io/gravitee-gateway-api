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
package io.gravitee.gateway.reactive.api.connector.endpoint.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PendingAuthTest {

    private static final Instant CREATED_AT = Instant.parse("2026-11-23T10:00:00Z");

    @Test
    void should_hold_no_code_verifier_when_built_without_an_elicitation_id() {
        var pending = new PendingAuth(
            "user",
            "group",
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "client",
            "secret",
            List.of("read"),
            "audience",
            CREATED_AT
        );

        assertThat(pending.elicitationId()).isNull();
        assertThat(pending.codeVerifier()).isNull();
        assertThat(pending.usesPkce()).isFalse();
    }

    @Test
    void should_hold_no_code_verifier_when_built_with_an_elicitation_id_only() {
        var pending = withoutPkce();

        assertThat(pending.elicitationId()).isEqualTo("elicitation");
        assertThat(pending.codeVerifier()).isNull();
        assertThat(pending.usesPkce()).isFalse();
    }

    @Test
    void should_hold_the_code_verifier_it_is_given() {
        var pending = withPkce("secret");

        assertThat(pending.codeVerifier()).isEqualTo("dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk");
        assertThat(pending.usesPkce()).isTrue();
    }

    @Test
    void should_use_pkce_without_a_client_secret() {
        var pending = withPkce(null);

        assertThat(pending.clientSecret()).isNull();
        assertThat(pending.usesPkce()).isTrue();
    }

    @Test
    void should_mask_the_client_secret_and_the_code_verifier_in_to_string() {
        var printed = withPkce("secret").toString();

        assertThat(printed)
            .doesNotContain("secret")
            .doesNotContain("dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk")
            .contains("clientSecret=****")
            .contains("codeVerifier=****")
            .contains("subject=user")
            .contains("endpointGroupId=group")
            .contains("clientId=client")
            .contains("elicitationId=elicitation");
    }

    @Test
    void should_print_absent_secrets_as_null_in_to_string() {
        var printed = new PendingAuth(
            "user",
            "group",
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "public-client",
            null,
            List.of("read"),
            null,
            CREATED_AT,
            "elicitation",
            null
        ).toString();

        assertThat(printed).contains("clientSecret=null").contains("codeVerifier=null");
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "",
            "   ",
            // 42 characters: one short of the RFC 7636 minimum
            "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjX",
            // "+" and "/" are base64, not base64url
            "dBjftJeZ4CVP+mB92K27uhbUJU1p1r/wW1gFWFOEjXk",
            "dBjftJeZ4CVP mB92K27uhbUJU1p1r_wW1gFWFOEjXk",
        }
    )
    void should_refuse_a_code_verifier_rfc_7636_does_not_allow(String codeVerifier) {
        assertThatThrownBy(() -> withPkce("secret", codeVerifier))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("RFC 7636");
    }

    @Test
    void should_refuse_a_code_verifier_longer_than_128_characters() {
        assertThatThrownBy(() -> withPkce("secret", "a".repeat(129))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_accept_every_character_and_length_rfc_7636_allows() {
        var longest = "A".repeat(124) + "-._~";

        assertThat(withPkce("secret", longest).codeVerifier()).hasSize(128);
        assertThat(withPkce("secret", "a".repeat(43)).usesPkce()).isTrue();
    }

    @Test
    void should_not_put_the_refused_code_verifier_in_the_error_message() {
        var invalid = "not a valid verifier, and a secret all the same";

        assertThatThrownBy(() -> withPkce("secret", invalid))
            .message()
            .doesNotContain(invalid);
    }

    private static PendingAuth withoutPkce() {
        return new PendingAuth(
            "user",
            "group",
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "client",
            "secret",
            List.of("read"),
            "audience",
            CREATED_AT,
            "elicitation"
        );
    }

    private static PendingAuth withPkce(String clientSecret) {
        return withPkce(clientSecret, "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk");
    }

    private static PendingAuth withPkce(String clientSecret, String codeVerifier) {
        return new PendingAuth(
            "user",
            "group",
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "client",
            clientSecret,
            List.of("read"),
            "audience",
            CREATED_AT,
            "elicitation",
            codeVerifier
        );
    }
}
