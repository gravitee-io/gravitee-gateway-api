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
package io.gravitee.gateway.reactive.api.connector;

import io.gravitee.common.component.LifecycleComponent;
import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.ConnectorMode;
import java.util.Set;

/**
 * Interface describing Connector which could be implemented to deal with new protocol specification
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Connector extends LifecycleComponent<Connector> {
    /**
     * Returns the id of the connector
     *
     * @return identifier of this connector
     */
    String id();

    /**
     * Returns the {@link ApiType} supported by this endpoint. It will be used to filter available connector when creating a new API
     *
     * @return {@link ApiType} supported by this connector.
     */
    ApiType supportedApi();

    /**
     * Returns a set of {@link ConnectorMode} supported by this connector. It will be used to resolve the proper connector.
     *
     * @return set of {@link ConnectorMode} supported by this connector.
     */
    Set<ConnectorMode> supportedModes();
}
