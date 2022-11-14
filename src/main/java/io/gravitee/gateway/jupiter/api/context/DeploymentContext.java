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
package io.gravitee.gateway.jupiter.api.context;

import io.gravitee.el.TemplateEngine;

/**
 * The {@link DeploymentContext} allows to access useful information at deployment time.
 * It is the responsibility of the implementation to give access to the component of its choice (ex: api definition, services components, ...).
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface DeploymentContext {
    /**
     * Returns the component corresponding to the specified <code>componentClass</code>.
     *
     * @param componentClass the {@link Class} of the expected component to retrieve.
     * @param <T> the expected instance type.
     *
     * @return the component or <code>null</code> if no component found.
     */
    <T> T getComponent(Class<T> componentClass);

    /**
     * Get the {@link TemplateEngine} that can be used to evaluate EL expressions.
     *
     * @return the El {@link TemplateEngine}.
     */
    TemplateEngine getTemplateEngine();
}
