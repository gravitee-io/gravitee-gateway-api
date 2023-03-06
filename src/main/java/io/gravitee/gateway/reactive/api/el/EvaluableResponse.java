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
package io.gravitee.gateway.reactive.api.el;

import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.reactive.api.context.GenericResponse;
import java.util.Map;

public class EvaluableResponse {

    private final GenericResponse response;
    private String content;
    private Map<String, Object> jsonContent;
    private Map<String, Object> xmlContent;

    public EvaluableResponse(final GenericResponse response) {
        this(response, null);
    }

    public EvaluableResponse(final GenericResponse response, final String content) {
        this.response = response;
        this.content = content;
    }

    public int getStatus() {
        return response.status();
    }

    public HttpHeaders getHeaders() {
        return response.headers();
    }

    public String getContent() {
        return content;
    }

    public Map<String, Object> getJsonContent() {
        return jsonContent;
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
