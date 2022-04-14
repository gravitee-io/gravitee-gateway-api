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
package io.gravitee.gateway.reactive.api.context;

import io.gravitee.el.TemplateEngine;
import io.gravitee.tracing.api.Tracer;
import java.util.Map;
import java.util.Set;

public interface ExecutionContext<RQ extends Request<?>, RS extends Response<?>> {
    boolean isInterrupted();

    // TODO will need to be introduce in future
    // ExecutableApi executableApi();

    RQ request();

    RS response();

    <T> T getComponent(Class<T> componentClass);

    void putAttribute(final String name, final Object value);

    void removeAttribute(final String name);

    Object getAttribute(final String name);

    Set<String> getAttributeNames();

    Map<String, Object> getAttributes();

    TemplateEngine getTemplateEngine();

    Tracer getTracer();
}
