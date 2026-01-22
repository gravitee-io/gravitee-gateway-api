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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.NOPLogger;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ExecutionContextLazyLoggerTest {

    @Nested
    class LazyLoading {

        @Test
        void should_not_initialize_context_aware_logger_if_level_is_disabled() {
            AtomicInteger supplierCalls = new AtomicInteger(0);
            FakeLogger delegate = new FakeLogger();
            delegate.setInfoEnabled(false);

            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(
                delegate,
                () -> {
                    supplierCalls.incrementAndGet();
                    return NOPLogger.NOP_LOGGER;
                }
            );

            lazyLogger.info("message");

            assertThat(supplierCalls.get()).isZero();
        }

        @Test
        void should_not_initialize_context_aware_logger_if_level_with_marker_is_disabled() {
            AtomicInteger supplierCalls = new AtomicInteger(0);
            FakeLogger delegate = new FakeLogger();
            delegate.setInfoEnabled(false);
            Marker marker = MarkerFactory.getMarker("TEST");

            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(
                delegate,
                () -> {
                    supplierCalls.incrementAndGet();
                    return NOPLogger.NOP_LOGGER;
                }
            );

            lazyLogger.info(marker, "message");

            assertThat(supplierCalls.get()).isZero();
        }

        @Test
        void should_initialize_context_aware_logger_only_once_if_level_is_enabled() {
            AtomicInteger supplierCalls = new AtomicInteger(0);
            FakeLogger delegate = new FakeLogger();
            delegate.setInfoEnabled(true);
            FakeLogger contextLogger = new FakeLogger();

            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(
                delegate,
                () -> {
                    supplierCalls.incrementAndGet();
                    return contextLogger;
                }
            );

            lazyLogger.info("message 1");
            lazyLogger.info("message 2");

            assertThat(supplierCalls.get()).isEqualTo(1);
            assertThat(contextLogger.getLoggedMessages()).containsExactly("message 1", "message 2");
        }
    }

    @Nested
    class Delegation {

        @Test
        void should_delegate_isTraceEnabled() {
            FakeLogger delegate = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> null);

            delegate.setTraceEnabled(true);
            assertThat(lazyLogger.isTraceEnabled()).isTrue();

            delegate.setTraceEnabled(false);
            assertThat(lazyLogger.isTraceEnabled()).isFalse();
        }

        @Test
        void should_delegate_isTraceEnabled_with_marker() {
            FakeLogger delegate = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> null);
            Marker marker = MarkerFactory.getMarker("TEST");

            delegate.setTraceEnabled(true);
            assertThat(lazyLogger.isTraceEnabled(marker)).isTrue();

            delegate.setTraceEnabled(false);
            assertThat(lazyLogger.isTraceEnabled(marker)).isFalse();
        }

        @Test
        void should_delegate_trace_action_when_enabled() {
            FakeLogger delegate = new FakeLogger();
            delegate.setTraceEnabled(true);
            FakeLogger contextLogger = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> contextLogger);

            lazyLogger.trace("trace message");

            assertThat(contextLogger.getLoggedMessages()).containsExactly("trace message");
        }

        @Test
        void should_delegate_trace_action_with_marker_when_enabled() {
            FakeLogger delegate = new FakeLogger();
            delegate.setTraceEnabled(true);
            FakeLogger contextLogger = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> contextLogger);
            Marker marker = MarkerFactory.getMarker("TEST");

            lazyLogger.trace(marker, "trace message marker");

            assertThat(contextLogger.getLoggedMessages()).containsExactly("trace message marker");
        }

        @Test
        void should_delegate_debug_action_when_enabled() {
            FakeLogger delegate = new FakeLogger();
            delegate.setDebugEnabled(true);
            FakeLogger contextLogger = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> contextLogger);

            lazyLogger.debug("debug message");

            assertThat(contextLogger.getLoggedMessages()).containsExactly("debug message");
        }

        @Test
        void should_delegate_info_action_when_enabled() {
            FakeLogger delegate = new FakeLogger();
            delegate.setInfoEnabled(true);
            FakeLogger contextLogger = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> contextLogger);

            lazyLogger.info("info message");

            assertThat(contextLogger.getLoggedMessages()).containsExactly("info message");
        }

        @Test
        void should_delegate_warn_action_when_enabled() {
            FakeLogger delegate = new FakeLogger();
            delegate.setWarnEnabled(true);
            FakeLogger contextLogger = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> contextLogger);

            lazyLogger.warn("warn message");

            assertThat(contextLogger.getLoggedMessages()).containsExactly("warn message");
        }

        @Test
        void should_delegate_error_action_when_enabled() {
            FakeLogger delegate = new FakeLogger();
            delegate.setErrorEnabled(true);
            FakeLogger contextLogger = new FakeLogger();
            ExecutionContextLazyLogger lazyLogger = new ExecutionContextLazyLogger(delegate, () -> contextLogger);

            lazyLogger.error("error message");

            assertThat(contextLogger.getLoggedMessages()).containsExactly("error message");
        }
    }

    private static class FakeLogger implements Logger {

        private boolean traceEnabled;
        private boolean debugEnabled;
        private boolean infoEnabled;
        private boolean warnEnabled;
        private boolean errorEnabled;
        private List<String> loggedMessages = new ArrayList<>();

        public void setTraceEnabled(boolean traceEnabled) {
            this.traceEnabled = traceEnabled;
        }

        public void setDebugEnabled(boolean debugEnabled) {
            this.debugEnabled = debugEnabled;
        }

        public void setInfoEnabled(boolean infoEnabled) {
            this.infoEnabled = infoEnabled;
        }

        public void setWarnEnabled(boolean warnEnabled) {
            this.warnEnabled = warnEnabled;
        }

        public void setErrorEnabled(boolean errorEnabled) {
            this.errorEnabled = errorEnabled;
        }

        public List<String> getLoggedMessages() {
            return loggedMessages;
        }

        @Override
        public String getName() {
            return "FakeLogger";
        }

        @Override
        public boolean isTraceEnabled() {
            return traceEnabled;
        }

        @Override
        public void trace(String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void trace(String format, Object arg) {}

        @Override
        public void trace(String format, Object arg1, Object arg2) {}

        @Override
        public void trace(String format, Object... arguments) {}

        @Override
        public void trace(String msg, Throwable t) {}

        @Override
        public boolean isTraceEnabled(Marker marker) {
            return traceEnabled;
        }

        @Override
        public void trace(Marker marker, String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void trace(Marker marker, String format, Object arg) {}

        @Override
        public void trace(Marker marker, String format, Object arg1, Object arg2) {}

        @Override
        public void trace(Marker marker, String format, Object... argArray) {}

        @Override
        public void trace(Marker marker, String msg, Throwable t) {}

        @Override
        public boolean isDebugEnabled() {
            return debugEnabled;
        }

        @Override
        public void debug(String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void debug(String format, Object arg) {}

        @Override
        public void debug(String format, Object arg1, Object arg2) {}

        @Override
        public void debug(String format, Object... arguments) {}

        @Override
        public void debug(String msg, Throwable t) {}

        @Override
        public boolean isDebugEnabled(Marker marker) {
            return debugEnabled;
        }

        @Override
        public void debug(Marker marker, String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void debug(Marker marker, String format, Object arg) {}

        @Override
        public void debug(Marker marker, String format, Object arg1, Object arg2) {}

        @Override
        public void debug(Marker marker, String format, Object... arguments) {}

        @Override
        public void debug(Marker marker, String msg, Throwable t) {}

        @Override
        public boolean isInfoEnabled() {
            return infoEnabled;
        }

        @Override
        public void info(String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void info(String format, Object arg) {}

        @Override
        public void info(String format, Object arg1, Object arg2) {}

        @Override
        public void info(String format, Object... arguments) {}

        @Override
        public void info(String msg, Throwable t) {}

        @Override
        public boolean isInfoEnabled(Marker marker) {
            return infoEnabled;
        }

        @Override
        public void info(Marker marker, String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void info(Marker marker, String format, Object arg) {}

        @Override
        public void info(Marker marker, String format, Object arg1, Object arg2) {}

        @Override
        public void info(Marker marker, String format, Object... arguments) {}

        @Override
        public void info(Marker marker, String msg, Throwable t) {}

        @Override
        public boolean isWarnEnabled() {
            return warnEnabled;
        }

        @Override
        public void warn(String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void warn(String format, Object arg) {}

        @Override
        public void warn(String format, Object... arguments) {}

        @Override
        public void warn(String format, Object arg1, Object arg2) {}

        @Override
        public void warn(String msg, Throwable t) {}

        @Override
        public boolean isWarnEnabled(Marker marker) {
            return warnEnabled;
        }

        @Override
        public void warn(Marker marker, String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void warn(Marker marker, String format, Object arg) {}

        @Override
        public void warn(Marker marker, String format, Object arg1, Object arg2) {}

        @Override
        public void warn(Marker marker, String format, Object... arguments) {}

        @Override
        public void warn(Marker marker, String msg, Throwable t) {}

        @Override
        public boolean isErrorEnabled() {
            return errorEnabled;
        }

        @Override
        public void error(String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void error(String format, Object arg) {}

        @Override
        public void error(String format, Object arg1, Object arg2) {}

        @Override
        public void error(String format, Object... arguments) {}

        @Override
        public void error(String msg, Throwable t) {}

        @Override
        public boolean isErrorEnabled(Marker marker) {
            return errorEnabled;
        }

        @Override
        public void error(Marker marker, String msg) {
            loggedMessages.add(msg);
        }

        @Override
        public void error(Marker marker, String format, Object arg) {}

        @Override
        public void error(Marker marker, String format, Object arg1, Object arg2) {}

        @Override
        public void error(Marker marker, String format, Object... arguments) {}

        @Override
        public void error(Marker marker, String msg, Throwable t) {}
    }
}
