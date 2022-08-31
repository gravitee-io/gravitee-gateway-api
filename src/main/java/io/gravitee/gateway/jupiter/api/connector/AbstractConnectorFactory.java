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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gravitee.gateway.jupiter.api.ApiType;
import io.gravitee.gateway.jupiter.api.ConnectorMode;
import io.gravitee.gateway.jupiter.api.exception.PluginConfigurationException;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Shared code between connector factory
 *
 * @param <T> related {@link io.gravitee.gateway.jupiter.api.connector.entrypoint.EntrypointConnector} or {@link io.gravitee.gateway.jupiter.api.connector.endpoint.EndpointConnector} or
 */
@RequiredArgsConstructor
public abstract class AbstractConnectorFactory<T> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Class<?> configurationClass;

    public abstract ApiType supportedApi();

    public abstract Set<ConnectorMode> supportedModes();

    public abstract T createConnector(final String configuration);

    /**
     * Helper method in order to easily map json string configuration to the given configuration class in {@link AbstractConnectorFactory(Class)}
     *
     * @param configuration a json string configuration
     * @param <U> expected Object
     * @return object serialized from given json configuration
     * @throws PluginConfigurationException in case any error occurred while mapping the json configuration
     */
    @SuppressWarnings("unchecked")
    protected <U> U getConfiguration(final String configuration) throws PluginConfigurationException {
        try {
            if (configuration == null) {
                return (U) configurationClass.getDeclaredConstructor().newInstance();
            }
            return (U) (OBJECT_MAPPER.readValue(configuration, configurationClass));
        } catch (Exception e) {
            throw new PluginConfigurationException("Failed to instantiate connector configuration", e);
        }
    }
}
