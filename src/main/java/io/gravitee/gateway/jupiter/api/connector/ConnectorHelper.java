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
import io.gravitee.gateway.jupiter.api.exception.PluginConfigurationException;
import io.gravitee.node.api.configuration.Configuration;
import lombok.AllArgsConstructor;

/**
 * Helper class used by factory to create new {@link Connector}
 *
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@AllArgsConstructor
public class ConnectorHelper {

    private final Configuration configuration;
    private final ObjectMapper objectMapper;

    /**
     * Helper method to retrieve node configuration
     *
     * @return {@link Configuration} available
     */
    public Configuration getNodeConfiguration() {
        return this.configuration;
    }

    /**
     * Helper method in order to easily map json string configuration to the given configuration class
     *
     * @param configurationClass the class to map the string to
     * @param configuration a json string configuration
     * @return object serialized from given json configuration
     * @throws PluginConfigurationException in case any error occurred while mapping the json configuration
     */
    public <T> T readConfiguration(final Class<T> configurationClass, final String configuration) throws PluginConfigurationException {
        try {
            if (configuration == null) {
                return configurationClass.getDeclaredConstructor().newInstance();
            }
            return objectMapper.readValue(configuration, configurationClass);
        } catch (Exception e) {
            throw new PluginConfigurationException("Failed to instantiate connector configuration", e);
        }
    }
}
