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
package io.gravitee.gateway.api.proxy.builder;

import io.gravitee.common.http.HttpHeaders;
import io.gravitee.common.http.HttpMethod;
import io.gravitee.common.util.MultiValueMap;
import io.gravitee.gateway.api.proxy.ProxyRequest;
import io.gravitee.reporter.api.http.Metrics;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ProxyRequestImpl implements ProxyRequest {

    private String uri;
    private MultiValueMap<String, String> parameters;
    private HttpMethod method;
    private String rawMethod;
    private HttpHeaders headers;
    private final Metrics metrics;

    protected ProxyRequestImpl(Metrics metrics) {
        this.metrics = metrics;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setParameters(MultiValueMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public void setRawMethod(String rawMethod) {
        this.rawMethod = rawMethod;
    }

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public MultiValueMap<String, String> parameters() {
        return parameters;
    }

    @Override
    public HttpMethod method() {
        return method;
    }

    @Override
    public String rawMethod() {
        return rawMethod;
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }

    @Override
    public Metrics metrics() {
        return metrics;
    }
}
