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
package io.gravitee.gateway.reactive.api.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.gravitee.gateway.reactive.api.ExecutionWarn;
import io.gravitee.gateway.reactive.api.context.TlsSession;
import io.gravitee.gateway.reactive.api.context.base.BaseExecutionContext;
import io.gravitee.gateway.reactive.api.tracing.Tracer;
import io.gravitee.node.api.Node;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AbstractBaseExecutionContextAwareLoggerTest {

    @Test
    void should_register_full_class_hierarchy_as_log_sources() throws Exception {
        // Given
        Node node = mock(Node.class);
        FakeExecutionContext context = new FakeExecutionContext(node);
        Logger delegate = NOPLogger.NOP_LOGGER;

        FakeContextAwareLogger logger = new FakeContextAwareLogger(context, delegate);

        Map<Class<?>, Object> logSources = new HashMap<>();

        // When
        Method registerLogSources = findRegisterLogSourcesMethod();
        registerLogSources.setAccessible(true);
        registerLogSources.invoke(logger, logSources);

        // Then
        assertThat(logSources)
            .containsKey(FakeExecutionContext.class)
            .containsKey(FakeInterface.class)
            .containsKey(BaseExecutionContext.class)
            .doesNotContainKey(Object.class);

        // All values should point to the same context instance
        assertThat(logSources.get(FakeExecutionContext.class)).isSameAs(context);
        assertThat(logSources.get(BaseExecutionContext.class)).isSameAs(context);
    }

    @Test
    void should_cache_hierarchy_for_same_class() throws Exception {
        // Given
        Node node = mock(Node.class);
        FakeExecutionContext ctx1 = new FakeExecutionContext(node);
        FakeExecutionContext ctx2 = new FakeExecutionContext(node);

        FakeContextAwareLogger logger1 = new FakeContextAwareLogger(ctx1, NOPLogger.NOP_LOGGER);
        FakeContextAwareLogger logger2 = new FakeContextAwareLogger(ctx2, NOPLogger.NOP_LOGGER);

        Map<Class<?>, Object> logSources1 = new HashMap<>();
        Map<Class<?>, Object> logSources2 = new HashMap<>();

        Method registerLogSources = findRegisterLogSourcesMethod();
        registerLogSources.setAccessible(true);

        // When
        registerLogSources.invoke(logger1, logSources1);
        registerLogSources.invoke(logger2, logSources2);

        // Then - same keys registered for both
        assertThat(logSources1.keySet()).isEqualTo(logSources2.keySet());
    }

    private Method findRegisterLogSourcesMethod() throws NoSuchMethodException {
        return AbstractBaseExecutionContextAwareLogger.class.getDeclaredMethod("registerLogSources", Map.class);
    }

    interface FakeInterface extends BaseExecutionContext {}

    static class FakeExecutionContext implements FakeInterface {

        private final Node node;

        FakeExecutionContext(Node node) {
            this.node = node;
        }

        @Override
        public <T> T getComponent(Class<T> componentClass) {
            if (componentClass == Node.class) {
                return componentClass.cast(node);
            }
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {}

        @Override
        public void putAttribute(String name, Object value) {}

        @Override
        public void removeAttribute(String name) {}

        @Override
        public <T> T getAttribute(String name) {
            return null;
        }

        @Override
        public <T> List<T> getAttributeAsList(String name) {
            return null;
        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public <T> Map<String, T> getAttributes() {
            return null;
        }

        @Override
        public void setInternalAttribute(String name, Object value) {}

        @Override
        public void putInternalAttribute(String name, Object value) {}

        @Override
        public void removeInternalAttribute(String name) {}

        @Override
        public <T> T getInternalAttribute(String name) {
            return null;
        }

        @Override
        public <T> Map<String, T> getInternalAttributes() {
            return null;
        }

        @Override
        public io.gravitee.el.TemplateEngine getTemplateEngine() {
            return null;
        }

        @Override
        public Tracer getTracer() {
            return null;
        }

        @Override
        public long timestamp() {
            return 0;
        }

        @Override
        public String remoteAddress() {
            return null;
        }

        @Override
        public String localAddress() {
            return null;
        }

        @Override
        public TlsSession tlsSession() {
            return null;
        }

        @Override
        public void warnWith(ExecutionWarn executionWarn) {}

        @Override
        public Logger withLogger(Logger delegate) {
            return delegate;
        }
    }

    static class FakeContextAwareLogger extends AbstractBaseExecutionContextAwareLogger<FakeExecutionContext> {

        public FakeContextAwareLogger(FakeExecutionContext context, Logger logger) {
            super(context, logger);
        }
    }
}
