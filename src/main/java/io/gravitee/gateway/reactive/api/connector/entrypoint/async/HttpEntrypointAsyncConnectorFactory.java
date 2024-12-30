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
package io.gravitee.gateway.reactive.api.connector.entrypoint.async;

import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.ConnectorMode;
import io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnectorFactory;
import io.gravitee.gateway.reactive.api.context.DeploymentContext;
import io.gravitee.gateway.reactive.api.qos.Qos;
import java.util.Set;

/**
 * Specialized factory for {@link HttpEntrypointAsyncConnector}
 */
public interface HttpEntrypointAsyncConnectorFactory<T extends HttpEntrypointAsyncConnector>
    extends EntrypointConnectorFactory<HttpEntrypointAsyncConnector> {
    @Override
    default ApiType supportedApi() {
        return ApiType.MESSAGE;
    }

    /**
     * Returns a set of {@link ConnectorMode} supported by this connector. It will be used to resolve the proper connector.
     *
     * @return set of {@link ConnectorMode} supported by this connector.
     */
    Set<Qos> supportedQos();

    /**
     * {@inheritDoc}
     * <p/>
     * Same as {@link #createConnector(DeploymentContext, Qos, String)} but without Qos.
     */
    default T createConnector(final DeploymentContext deploymentContext, final String configuration) {
        return createConnector(deploymentContext, null, configuration);
    }

    /**
     * Allow creating new connector from the given string configuration and QoS (Quality Of Service).
     *
     * @param deploymentContext context containing useful deployment entities (api, services, ...).
     * @param qos the expected quality of service.
     * @param configuration the configuration as json string.
     *
     * @return new connector instance.
     */
    T createConnector(final DeploymentContext deploymentContext, final Qos qos, final String configuration);
}
