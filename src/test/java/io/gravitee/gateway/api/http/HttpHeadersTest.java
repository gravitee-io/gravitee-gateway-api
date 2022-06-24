/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.api.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Yann TAVERNIER (yann.tavernier at graviteesource.com)
 * @author GraviteeSource Team
 */
public class HttpHeadersTest {

    /**
     * Provide a stream of Arguments for testing deeplyEquals function.
     *
     * @return a Stream of Arguments composed of
     * - A HttpHeaders object
     * - A second HttpHeaders to compare with the first one
     * - The expected equality result
     * - The display name of the test case
     */
    private static Stream<Arguments> provideHttpHeadersDataForDeeplyEqualsTest() {
        return Stream.of(
            Arguments.of(
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", List.of("application/json", "application/json+vnd")),
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", List.of("application/json", "application/json+vnd")),
                true,
                "HttpHeaders are equals"
            ),
            Arguments.of(
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", List.of("application/json", "application/json+vnd")),
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", "application/json"),
                false,
                "HttpHeaders are not equals: not same size in values"
            ),
            Arguments.of(
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", List.of("application/json", "application/json+vnd")),
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", List.of("application/json", "application/xml")),
                false,
                "HttpHeaders are not equals: different values"
            ),
            Arguments.of(
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", List.of("application/json", "application/json+vnd")),
                HttpHeaders.create().add("Host", "test.gravitee.io"),
                false,
                "HttpHeaders are not equals: key-sets have different sizes"
            ),
            Arguments.of(
                HttpHeaders.create().add("Host", "test.gravitee.io").add("Accept", List.of("application/json", "application/json+vnd")),
                HttpHeaders
                    .create()
                    .add("Host", "test.gravitee.io")
                    .add("X-Not-Expected-Header", List.of("application/json", "application/json+vnd")),
                false,
                "HttpHeaders are not equals: first key-set does not contains all key of second"
            )
        );
    }

    @ParameterizedTest(name = "#{index} - {3}")
    @MethodSource("provideHttpHeadersDataForDeeplyEqualsTest")
    public void shouldTestDeeplyEquality(HttpHeaders headers1, HttpHeaders headers2, boolean expectedResult, String testName) {
        Assertions.assertThat(headers1.deeplyEquals(headers2)).isEqualTo(expectedResult);
        Assertions.assertThat(headers2.deeplyEquals(headers1)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldReturnListValuesMap() {
        final HttpHeaders headers = HttpHeaders
            .create()
            .add("transfer-encoding", "chunked")
            .add("X-Gravitee-Transaction-Id", "transaction-id")
            .add("content-type", "application/json")
            .add("X-Gravitee-Request-Id", "request-id")
            .add("accept-encoding", "deflate")
            .add("accept-encoding", "gzip")
            .add("accept-encoding", "compress");

        final Map<String, List<String>> result = headers.toListValuesMap();

        assertThat(result.get("transfer-encoding")).hasSize(1);
        assertThat(result.get("transfer-encoding")).contains("chunked");
        assertThat(result.get("X-Gravitee-Transaction-Id")).hasSize(1);
        assertThat(result.get("X-Gravitee-Transaction-Id")).contains("transaction-id");
        assertThat(result.get("content-type")).hasSize(1);
        assertThat(result.get("content-type")).contains("application/json");
        assertThat(result.get("X-Gravitee-Request-Id")).hasSize(1);
        assertThat(result.get("X-Gravitee-Request-Id")).contains("request-id");
        assertThat(result.get("accept-encoding")).hasSize(3);
        assertThat(result.get("accept-encoding")).contains("deflate", "gzip", "compress");
    }
}
