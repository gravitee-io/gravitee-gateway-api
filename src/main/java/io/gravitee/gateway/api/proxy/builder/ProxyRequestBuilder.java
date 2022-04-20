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
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.proxy.ProxyRequest;
import io.gravitee.gateway.api.proxy.ws.WebSocketProxyRequestImpl;
import java.util.regex.Pattern;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ProxyRequestBuilder {

    // Pattern reuse for duplicate slash removal
    private static final Pattern DUPLICATE_SLASH_REMOVER = Pattern.compile("(?<!(https?:|wss?:|grpcs?:))[//]+");
    private static final String URI_PATH_SEPARATOR = "/";

    private String uri;
    private MultiValueMap<String, String> parameters;
    private HttpMethod method;
    private String rawMethod;
    private HttpHeaders headers;
    private final Request request;

    private ProxyRequestBuilder(final Request request) {
        this.request = request;
    }

    public static ProxyRequestBuilder from(Request request) {
        ProxyRequestBuilder builder = new ProxyRequestBuilder(request);

        builder.uri(request.uri());
        builder.headers(request.headers());
        builder.method(request.method());
        builder.parameters(request.parameters());

        return builder;
    }

    public ProxyRequestBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public ProxyRequestBuilder parameters(MultiValueMap<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public ProxyRequestBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public ProxyRequestBuilder rawMethod(String rawMethod) {
        this.rawMethod = rawMethod;
        return this;
    }

    public ProxyRequestBuilder headers(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public ProxyRequest build() {
        ProxyRequestImpl proxyRequest;

        if (!request.isWebSocket()) {
            proxyRequest = new ProxyRequestImpl(this.request);
        } else {
            proxyRequest = new WebSocketProxyRequestImpl(this.request);
        }

        // Remove duplicate slash
        proxyRequest.setUri(DUPLICATE_SLASH_REMOVER.matcher(this.uri).replaceAll(URI_PATH_SEPARATOR));
        proxyRequest.setMethod(this.method);
        proxyRequest.setRawMethod(this.rawMethod);
        proxyRequest.setParameters(this.parameters);
        proxyRequest.setHeaders(this.headers);

        return proxyRequest;
    }
}
