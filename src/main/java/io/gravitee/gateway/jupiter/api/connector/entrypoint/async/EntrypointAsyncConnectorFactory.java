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
package io.gravitee.gateway.jupiter.api.connector.entrypoint.async;

import io.gravitee.gateway.jupiter.api.ApiType;
import io.gravitee.gateway.jupiter.api.ConnectorMode;
import io.gravitee.gateway.jupiter.api.connector.entrypoint.EntrypointConnectorFactory;
import io.gravitee.gateway.jupiter.api.qos.Qos;
import java.util.Set;

/**
 * Specialized factory for {@link EntrypointAsyncConnector}
 */
public interface EntrypointAsyncConnectorFactory extends EntrypointConnectorFactory<EntrypointAsyncConnector> {
    @Override
    default ApiType supportedApi() {
        return ApiType.ASYNC;
    }

    /**
     * Returns a set of {@link ConnectorMode} supported by this connector. It will be used to resolve the proper connector.
     *
     * @return set of {@link ConnectorMode} supported by this connector.
     */
    Set<Qos> supportedQos();

    /**
     * Allow creating new connector from the given string configuration.
     *
     * @return new connector instance
     */
    default EntrypointAsyncConnector createConnector(final String configuration) {
        return createConnector(null, configuration);
    }

    /**
     * Allow creating new connector from the given string configuration
     *
     * @return new connector instance
     */
    EntrypointAsyncConnector createConnector(final Qos qos, final String configuration);
}
