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

import java.util.List;
import org.junit.jupiter.api.Test;

class UpstreamAuthDescriptorTest {

    @Test
    void should_not_use_pkce_when_built_without_the_flag() {
        var descriptor = elicitation();

        assertThat(descriptor.pkce()).isFalse();
        assertThat(descriptor.usesPkce()).isFalse();
    }

    @Test
    void should_keep_every_other_field_when_built_without_the_flag() {
        var descriptor = elicitation();

        assertThat(descriptor).isEqualTo(
            new UpstreamAuthDescriptor(
                "elicitation-oauth",
                null,
                null,
                null,
                null,
                null,
                "https://as.example.com/authorize",
                "https://as.example.com/token",
                "client",
                "secret",
                List.of("read"),
                "audience",
                false
            )
        );
    }

    @Test
    void should_use_pkce_when_the_flag_is_set_on_elicitation_oauth() {
        var descriptor = elicitation(true);

        assertThat(descriptor.pkce()).isTrue();
        assertThat(descriptor.usesPkce()).isTrue();
    }

    @Test
    void should_not_use_pkce_when_the_flag_is_set_on_another_type() {
        var descriptor = new UpstreamAuthDescriptor("bearer", "token", null, null, null, null, null, null, null, null, null, null, true);

        assertThat(descriptor.usesPkce()).isFalse();
    }

    @Test
    void should_use_pkce_without_a_client_secret() {
        var descriptor = new UpstreamAuthDescriptor(
            "elicitation-oauth",
            null,
            null,
            null,
            null,
            null,
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "public-client",
            null,
            List.of("read"),
            null,
            true
        );

        assertThat(descriptor.clientSecret()).isNull();
        assertThat(descriptor.usesPkce()).isTrue();
    }

    @Test
    void should_mask_every_secret_in_to_string() {
        var printed = new UpstreamAuthDescriptor(
            "basic",
            "the-token",
            "the-api-key",
            "X-Key",
            "admin",
            "the-password",
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "client",
            "the-client-secret",
            List.of("read"),
            "audience",
            true
        ).toString();

        assertThat(printed)
            .doesNotContain("the-token", "the-api-key", "the-password", "the-client-secret")
            .contains("token=****", "apiKey=****", "password=****", "clientSecret=****")
            .contains("type=basic", "apiKeyHeader=X-Key", "username=admin", "clientId=client", "pkce=true");
    }

    @Test
    void should_print_absent_secrets_as_null_in_to_string() {
        var printed = elicitation(true).toString();

        assertThat(printed).contains("token=null", "apiKey=null", "password=null", "clientSecret=****");
    }

    private static UpstreamAuthDescriptor elicitation() {
        return new UpstreamAuthDescriptor(
            "elicitation-oauth",
            null,
            null,
            null,
            null,
            null,
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "client",
            "secret",
            List.of("read"),
            "audience"
        );
    }

    private static UpstreamAuthDescriptor elicitation(boolean pkce) {
        return new UpstreamAuthDescriptor(
            "elicitation-oauth",
            null,
            null,
            null,
            null,
            null,
            "https://as.example.com/authorize",
            "https://as.example.com/token",
            "client",
            "secret",
            List.of("read"),
            "audience",
            pkce
        );
    }
}
