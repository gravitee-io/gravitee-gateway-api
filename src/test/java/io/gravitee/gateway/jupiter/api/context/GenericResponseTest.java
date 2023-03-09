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
package io.gravitee.gateway.jupiter.api.context;

import static org.assertj.core.api.Assertions.assertThat;

import io.gravitee.gateway.api.http.HttpHeaders;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Yann TAVERNIER (yann.tavernier at graviteesource.com)
 * @author GraviteeSource Team
 */
class GenericResponseTest {

    @ParameterizedTest
    @ValueSource(ints = { 100, 101, 103, 199 })
    void shouldBeStatus1xxResponse(int status) {
        final DummyGenericResponse cut = new DummyGenericResponse(status);
        assertThat(cut.isStatus1xx()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { 200, 201, 203, 299 })
    void shouldBeStatus2xxResponse(int status) {
        final DummyGenericResponse cut = new DummyGenericResponse(status);
        assertThat(cut.isStatus2xx()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { 300, 301, 303, 399 })
    void shouldBeStatus3xxResponse(int status) {
        final DummyGenericResponse cut = new DummyGenericResponse(status);
        assertThat(cut.isStatus3xx()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { 400, 401, 403, 499 })
    void shouldBeStatus4xxResponse(int status) {
        final DummyGenericResponse cut = new DummyGenericResponse(status);
        assertThat(cut.isStatus4xx()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { 500, 501, 503, 599 })
    void shouldBeStatus5xxResponse(int status) {
        final DummyGenericResponse cut = new DummyGenericResponse(status);
        assertThat(cut.isStatus5xx()).isTrue();
    }

    private static class DummyGenericResponse implements GenericResponse {

        private final int status;

        private DummyGenericResponse(int status) {
            this.status = status;
        }

        @Override
        public GenericResponse status(int statusCode) {
            return this;
        }

        @Override
        public int status() {
            return status;
        }

        @Override
        public String reason() {
            return null;
        }

        @Override
        public GenericResponse reason(String message) {
            return null;
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public HttpHeaders trailers() {
            return null;
        }

        @Override
        public boolean ended() {
            return false;
        }
    }
}
