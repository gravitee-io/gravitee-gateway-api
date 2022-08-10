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
package io.gravitee.gateway.jupiter.api.endpoint;

import static org.junit.jupiter.api.Assertions.*;

import io.gravitee.gateway.jupiter.api.ApiType;
import io.gravitee.gateway.jupiter.api.ConnectorMode;
import io.gravitee.gateway.jupiter.api.endpoint.async.EndpointAsyncConnector;
import io.gravitee.gateway.jupiter.api.endpoint.async.EndpointAsyncConnectorFactory;
import io.gravitee.gateway.jupiter.api.exception.PluginConfigurationException;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EndpointConnectorFactoryTest {

    @Getter
    @Setter
    public static class TestConfiguration {

        private String data1;
        private String data2;
    }

    private EndpointConnectorFactory testFactory = new EndpointAsyncConnectorFactory(TestConfiguration.class) {
        @Override
        public ApiType supportedApi() {
            return null;
        }

        @Override
        public Set<ConnectorMode> supportedModes() {
            return null;
        }

        @Override
        public EndpointAsyncConnector createConnector(String configuration) {
            return null;
        }
    };

    @Test
    @DisplayName("getConfiguration should read JSON configuration and return configuration object")
    public void shouldReadConfigurationWithValidConfiguration() throws PluginConfigurationException {
        TestConfiguration config = (TestConfiguration) testFactory.getConfiguration("{\"data1\":\"value1\", \"data2\":\"value2\"}");
        assertEquals("value1", config.getData1());
        assertEquals("value2", config.getData2());
    }

    @Test
    @DisplayName("getConfiguration should throw PluginConfigurationException when configuration is invalid")
    public void shouldThrowPluginConfigurationExceptionWithInvalidConfiguration() {
        assertThrows(PluginConfigurationException.class, () -> testFactory.getConfiguration("THIS IS INVALID JSON"));
    }

    @Test
    @DisplayName("getConfiguration should get empty configuration when configuration is null")
    public void shouldGetEmptyConfigurationExceptionWithNullConfiguration() throws PluginConfigurationException {
        TestConfiguration config = (TestConfiguration) testFactory.getConfiguration(null);
        assertNotNull(config);
        assertNull(config.getData1());
        assertNull(config.getData2());
    }
}
