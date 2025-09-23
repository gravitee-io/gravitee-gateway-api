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

import lombok.EqualsAndHashCode;

/**
 * @author GraviteeSource Team
 */
@EqualsAndHashCode
public class ExecutionWarn implements ExecutionIssue {

    private String key;
    private String message;
    private Throwable cause;

    public ExecutionWarn(String key) {
        this.key = key;
    }

    public String message() {
        return message;
    }

    public ExecutionWarn message(String message) {
        this.message = message;
        return this;
    }

    public String key() {
        return key;
    }

    public ExecutionWarn key(String key) {
        this.key = key;
        return this;
    }

    public Throwable cause() {
        return cause;
    }

    public ExecutionWarn cause(Throwable cause) {
        this.cause = cause;
        return this;
    }
}
