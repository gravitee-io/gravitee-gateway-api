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
package io.gravitee.gateway.jupiter.api.el;

import io.gravitee.common.util.MultiValueMap;
import io.gravitee.gateway.api.el.EvaluableSSLSession;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.jupiter.api.context.HttpRequest;
import java.util.Map;

public class EvaluableRequest {

    private final HttpRequest request;
    private String content;
    private Map<String, Object> jsonContent;
    private Map<String, Object> xmlContent;

    public EvaluableRequest(final HttpRequest request) {
        this(request, null);
    }

    public EvaluableRequest(final HttpRequest request, final String content) {
        this.request = request;
        this.content = content;
    }

    public String getId() {
        return request.id();
    }

    public String getTransactionId() {
        return request.transactionId();
    }

    public String getUri() {
        return request.uri();
    }

    public String getPath() {
        return request.path();
    }

    public String[] getPaths() {
        return request.path().split("/");
    }

    public String getPathInfo() {
        return request.pathInfo();
    }

    public String[] getPathInfos() {
        return request.pathInfo().split("/");
    }

    public String getContextPath() {
        return request.contextPath();
    }

    public MultiValueMap<String, String> getParams() {
        return request.parameters();
    }

    public MultiValueMap<String, String> getPathParams() {
        return request.pathParameters();
    }

    public HttpHeaders getHeaders() {
        return request.headers();
    }

    public String getMethod() {
        return request.method().name();
    }

    public String getScheme() {
        return request.scheme();
    }

    public String getVersion() {
        return request.version().name();
    }

    public long getTimestamp() {
        return request.timestamp();
    }

    public String getRemoteAddress() {
        return request.localAddress();
    }

    public String getLocalAddress() {
        return request.localAddress();
    }

    public String getContent() {
        return content;
    }

    public Map<String, Object> getJsonContent() {
        return jsonContent;
    }

    public EvaluableSSLSession getSsl() {
        return new EvaluableSSLSession(request.sslSession());
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setJsonContent(Map<String, Object> jsonContent) {
        this.jsonContent = jsonContent;
    }

    public Map<String, Object> getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(Map<String, Object> xmlContent) {
        this.xmlContent = xmlContent;
    }
}
