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
package io.gravitee.gateway.reactive.api.connector.endpoint.sync;

import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.ConnectorMode;
import io.gravitee.gateway.reactive.api.connector.endpoint.HttpEndpointConnector;
import java.util.Collection;
import java.util.Set;

/**
 * Specialized {@link HttpEndpointConnector} for {@link ApiType#A2A_PROXY}
 */
public interface A2AProxyEndpointSyncConnector extends HttpEndpointConnector {
    @Override
    default ApiType supportedApi() {
        return apiType();
    }

    @Override
    default Set<ConnectorMode> supportedModes() {
        return modes();
    }

    static ApiType apiType() {
        return ApiType.A2A_PROXY;
    }

    static Set<ConnectorMode> modes() {
        return Set.of(ConnectorMode.REQUEST_RESPONSE);
    }
}
