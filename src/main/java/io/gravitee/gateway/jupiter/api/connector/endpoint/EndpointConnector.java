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
package io.gravitee.gateway.jupiter.api.connector.endpoint;

import io.gravitee.gateway.jupiter.api.ApiType;
import io.gravitee.gateway.jupiter.api.ConnectorMode;
import io.gravitee.gateway.jupiter.api.context.ExecutionContext;
import io.reactivex.Completable;
import java.util.Set;

/**
 * Interface describing Endpoint Connector which could be implemented to deal with new protocol specification
 *
 * @author GraviteeSource Team
 */
public interface EndpointConnector {
    /**
     * Returns the {@link ApiType} supported by this endpoint. It will be used to filter available endpoint when creating a new API
     *
     * @return {@link ApiType} supported by this entrypoint.
     */
    ApiType supportedApi();

    /**
     * Returns a set of {@link ConnectorMode} supported by this endpoint. It will be used to resolve the proper {@link io.gravitee.gateway.jupiter.api.connector.endpoint.EndpointConnector}.
     *
     * @return set of {@link ConnectorMode} supported by this entrypoint.
     */
    Set<ConnectorMode> supportedModes();

    Completable connect(final ExecutionContext executionContext);
}
