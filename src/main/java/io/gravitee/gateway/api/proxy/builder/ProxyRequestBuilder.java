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
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.proxy.ProxyRequest;

import java.net.URI;
import java.util.Map;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ProxyRequestBuilder {

    private URI uri;
    private Map<String, String> parameters;
    private HttpMethod method;
    private HttpHeaders headers;
    private Request request;

    private ProxyRequestBuilder(Request request) {
        this.request = request;
    }

    public static ProxyRequestBuilder from(Request request) {
        return new ProxyRequestBuilder(request);
    }

    public ProxyRequestBuilder uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public ProxyRequestBuilder parameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public ProxyRequestBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public ProxyRequestBuilder headers(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public ProxyRequest build() {
        SimpleProxyRequest proxyRequest = new SimpleProxyRequest(this.request);
        proxyRequest.setUri(this.uri);
        proxyRequest.setMethod(this.method);
        proxyRequest.setParameters(this.parameters);
        proxyRequest.setHeaders(this.headers);
        return proxyRequest;
    }
}
