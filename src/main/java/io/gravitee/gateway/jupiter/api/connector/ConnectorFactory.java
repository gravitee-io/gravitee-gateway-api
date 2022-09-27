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
package io.gravitee.gateway.jupiter.api.connector;

import io.gravitee.gateway.jupiter.api.ApiType;
import io.gravitee.gateway.jupiter.api.ConnectorMode;
import java.util.Set;

/**
 * Factory used to create new {@link Connector}
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface ConnectorFactory<T extends Connector> {
    /**
     * @return {@link ApiType} supported by this connector.
     */
    ApiType supportedApi();

    /**
     * @return {@link ConnectorMode} supported by this connector.
     */
    Set<ConnectorMode> supportedModes();

    /**
     * Allow creating new connector from the given string configuration
     *
     * @return new connector instance
     */
    T createConnector(final String configuration);
}
