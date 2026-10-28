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
package io.gravitee.gateway.api.el;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.gravitee.common.util.MultiValueMap;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.http.HttpHeaders;
import lombok.Getter;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class EvaluableRequest {

    private final Request request;

    @Getter
    private final String content;

    public EvaluableRequest(final Request request) {
        this(request, null);
    }

    public EvaluableRequest(final Request request, final String content) {
        this.request = request;
        this.content = content;
    }

    @JsonProperty
    public String getId() {
        return request.id();
    }

    @JsonProperty
    public String getTransactionId() {
        return request.transactionId();
    }

    @JsonProperty
    public String getUri() {
        return request.uri();
    }

    @JsonProperty
    public String getPath() {
        return request.path();
    }

    @JsonProperty
    public String[] getPaths() {
        return request.path().split("/");
    }

    @JsonProperty
    public String getPathInfo() {
        return request.pathInfo();
    }

    @JsonProperty
    public String[] getPathInfos() {
        return request.pathInfo().split("/");
    }

    @JsonProperty
    public String getContextPath() {
        return request.contextPath();
    }

    @JsonProperty
    public MultiValueMap<String, String> getParams() {
        return request.parameters();
    }

    @JsonProperty
    public MultiValueMap<String, String> getPathParams() {
        return request.pathParameters();
    }

    @JsonProperty
    public HttpHeaders getHeaders() {
        return request.headers();
    }

    @JsonProperty
    public String getMethod() {
        return request.method().name();
    }

    @JsonProperty
    public String getScheme() {
        return request.scheme();
    }

    @JsonProperty
    public String getHost() {
        return request.host();
    }

    @JsonProperty
    public String getVersion() {
        return request.version().name();
    }

    @JsonProperty
    public long getTimestamp() {
        return request.timestamp();
    }

    @JsonProperty
    public String getRemoteAddress() {
        return request.remoteAddress();
    }

    @JsonProperty
    public String getLocalAddress() {
        return request.localAddress();
    }

    @JsonProperty
    public EvaluableSSLSession getSsl() {
        return new EvaluableSSLSession(request.sslSession());
    }
}
