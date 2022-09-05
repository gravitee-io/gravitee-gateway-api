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

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gravitee.gateway.jupiter.api.connector.endpoint.EndpointConnectorConfiguration;
import io.gravitee.gateway.jupiter.api.exception.PluginConfigurationException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConnectorFactoryHelperTest {

    private final ConnectorFactoryHelper connectorFactoryHelper = new ConnectorFactoryHelper(null, new ObjectMapper());

    @Test
    @DisplayName("getConnectorConfiguration should read JSON configuration and return configuration object")
    void shouldReadConfigurationWithValidConfiguration() throws PluginConfigurationException {
        TestConfiguration config = connectorFactoryHelper.getConnectorConfiguration(
            TestConfiguration.class,
            "{\"data1\":\"value1\", \"data2\":\"value2\"}"
        );
        assertEquals("value1", config.getData1());
        assertEquals("value2", config.getData2());
    }

    @Test
    @DisplayName("getConnectorConfiguration should throw PluginConfigurationException when configuration is invalid")
    void shouldThrowPluginConfigurationExceptionWithInvalidConfiguration() {
        assertThrows(
            PluginConfigurationException.class,
            () -> connectorFactoryHelper.getConnectorConfiguration(TestConfiguration.class, "THIS IS INVALID JSON")
        );
    }

    @Test
    @DisplayName("getConnectorConfiguration should get empty configuration when configuration is null")
    void shouldGetEmptyConfigurationExceptionWithNullConfiguration() throws PluginConfigurationException {
        TestConfiguration config = connectorFactoryHelper.getConnectorConfiguration(TestConfiguration.class, null);
        assertNotNull(config);
        assertNull(config.getData1());
        assertNull(config.getData2());
    }

    @Getter
    @Setter
    public static class TestConfiguration implements EndpointConnectorConfiguration {

        private String data1;
        private String data2;
    }
}
