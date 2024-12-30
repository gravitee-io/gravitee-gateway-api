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
package io.gravitee.gateway.reactive.api.connector.endpoint;

import io.gravitee.gateway.reactive.api.context.DeploymentContext;

/**
 * Specialized factory for {@link EndpointConnector}
 * @deprecated see {@link HttpEndpointConnectorFactory}
 */
@Deprecated(forRemoval = true)
public interface EndpointConnectorFactory<T extends EndpointConnector> extends HttpEndpointConnectorFactory<T> {
    /**
     * Allow creating new endpoint connector from the given strings configuration.
     * This method might look redundant with {@link BaseEndpointConnectorFactory#createConnector(DeploymentContext, String, String)} but it needs to remain here for the existing plugins which inherit from it.
     *
     * @param deploymentContext context containing useful deployment entities (api, services, ...).
     * @param configuration the configuration as json string.
     * @param sharedConfiguration the shared configuration as json string.
     *
     * @return new connector instance.
     */
    default T createConnector(final DeploymentContext deploymentContext, final String configuration, final String sharedConfiguration) {
        return null;
    }
}
