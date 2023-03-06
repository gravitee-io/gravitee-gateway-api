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
import io.gravitee.gateway.reactive.api.message.Message;
import java.util.Map;
import java.util.Set;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public class EvaluableMessage {

    private final Message message;

    public EvaluableMessage(Message message) {
        this.message = message;
    }

    public Set<String> attributeNames() {
        return message.attributeNames();
    }

    public <T> Map<String, T> attributes() {
        return message.attributes();
    }

    public String id() {
        return message.id();
    }

    public boolean error() {
        return message.error();
    }

    public Map<String, Object> metadata() {
        return message.metadata();
    }

    public HttpHeaders headers() {
        return message.headers();
    }

    public String content() {
        return message.content().toString();
    }

    public int contentLength() {
        return message.content().length();
    }
}
