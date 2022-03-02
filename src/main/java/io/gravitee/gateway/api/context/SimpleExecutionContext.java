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
package io.gravitee.gateway.api.context;

import io.gravitee.el.TemplateEngine;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.tracing.api.Tracer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class SimpleExecutionContext implements MutableExecutionContext {

    private final Map<String, Object> attributes = new AttributeMap();

    private Request request;

    private Response response;

    public SimpleExecutionContext(final Request request, final Response response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public MutableExecutionContext request(Request request) {
        this.request = request;
        return this;
    }

    @Override
    public MutableExecutionContext response(Response response) {
        this.response = response;
        return this;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response response() {
        return response;
    }

    @Override
    public <T> T getComponent(Class<T> componentClass) {
        throw new IllegalStateException();
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public TemplateEngine getTemplateEngine() {
        throw new IllegalStateException();
    }

    @Override
    public Tracer getTracer() {
        throw new IllegalStateException();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    private class AttributeMap extends HashMap<String, Object> {

        /**
         * In the most general case, the context will not store more than 12 elements in the Map.
         * Then, the initial capacity must be set to limit size in memory.
         */
        AttributeMap() {
            super(12, 1.0f);
        }

        @Override
        public Object get(Object key) {
            Object value = super.get(key);
            return (value != null) ? value : super.get(ExecutionContext.ATTR_PREFIX + key);
        }

        @Override
        public boolean containsKey(Object key) {
            return super.containsKey(key) || super.containsKey(ExecutionContext.ATTR_PREFIX + key);
        }
    }
}
