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
package io.gravitee.gateway.api.proxy;

import io.gravitee.common.http.HttpMethod;
import io.gravitee.common.util.MultiValueMap;
import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.reporter.api.http.Metrics;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface ProxyRequest {
    /**
     * @return the target URI of the request.
     */
    String uri();

    /**
     * @return the query parameters in the request
     */
    MultiValueMap<String, String> parameters();

    /**
     * @return the path parameters in the request
     */
    MultiValueMap<String, String> pathParameters();

    /**
     * @return the HTTP method for the request.
     */
    HttpMethod method();

    /**
     * The raw method in case the method() returns HttpMethod.OTHER
     * @return
     */
    String rawMethod();

    /**
     * @return the headers in the request.
     */
    HttpHeaders headers();

    /**
     * @return the metrics attached to the request.
     */
    Metrics metrics();

    ProxyRequest closeHandler(Handler<Void> closeHandler);
}
