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
package io.gravitee.gateway.reactive.api;

import java.util.Map;
import lombok.EqualsAndHashCode;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
@EqualsAndHashCode
public class ExecutionFailure {

    private int statusCode;
    private String message;
    private String key;
    private Map<String, Object> parameters;
    private String contentType;

    public ExecutionFailure() {}

    public ExecutionFailure(int statusCode) {
        this.statusCode = statusCode;
    }

    public int statusCode() {
        return statusCode;
    }

    public ExecutionFailure statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String message() {
        return message;
    }

    public ExecutionFailure message(String message) {
        this.message = message;
        return this;
    }

    public String key() {
        return key;
    }

    public ExecutionFailure key(String key) {
        this.key = key;
        return this;
    }

    public Map<String, Object> parameters() {
        return parameters;
    }

    public ExecutionFailure parameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    public String contentType() {
        return contentType;
    }

    public ExecutionFailure contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
