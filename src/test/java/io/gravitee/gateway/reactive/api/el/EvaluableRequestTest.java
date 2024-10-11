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
package io.gravitee.gateway.reactive.api.el;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.gravitee.gateway.reactive.api.context.GenericRequest;
import io.gravitee.gateway.reactive.api.context.http.HttpBaseRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
class EvaluableRequestTest {

    @Test
    void should_construct_with_http_base_request() {
        HttpBaseRequest httpBaseRequest = Mockito.mock(HttpBaseRequest.class);
        assertDoesNotThrow(() -> new EvaluableRequest(httpBaseRequest));
    }

    @Test
    void should_construct_with_http_base_request_and_content() {
        HttpBaseRequest httpBaseRequest = Mockito.mock(HttpBaseRequest.class);
        assertDoesNotThrow(() -> new EvaluableRequest(httpBaseRequest, "content"));
    }

    @Test
    void should_construct_with_generic_request() {
        GenericRequest genericRequest = Mockito.mock(GenericRequest.class);
        assertDoesNotThrow(() -> new EvaluableRequest(genericRequest));
    }

    @Test
    void should_construct_with_generic_request_and_content() {
        GenericRequest genericRequest = Mockito.mock(GenericRequest.class);
        assertDoesNotThrow(() -> new EvaluableRequest(genericRequest, "content"));
    }
}
