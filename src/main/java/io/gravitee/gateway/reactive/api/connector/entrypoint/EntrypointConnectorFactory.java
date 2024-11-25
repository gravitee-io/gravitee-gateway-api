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
package io.gravitee.gateway.reactive.api.connector.entrypoint;

import io.gravitee.gateway.reactive.api.ListenerType;
import io.gravitee.gateway.reactive.api.connector.ConnectorFactory;
import io.gravitee.gateway.reactive.api.context.DeploymentContext;

/**
 * Specialized factory for {@link BaseEntrypointConnector}
 */
public interface EntrypointConnectorFactory<T extends BaseEntrypointConnector> extends ConnectorFactory<T> {
    ListenerType supportedListenerType();

    /**
     * Allow creating new entrypoint connector from the given string configuration.
     *
     * @param deploymentContext context containing useful deployment entities (api, services, ...).
     * @param configuration the configuration as json string.
     *
     * @return new connector instance.
     */
    T createConnector(final DeploymentContext deploymentContext, final String configuration);
}
