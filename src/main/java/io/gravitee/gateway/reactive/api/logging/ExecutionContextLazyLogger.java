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

import io.gravitee.gateway.reactive.api.context.base.BaseExecutionContext;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * A logger implementation that provides lazy initialization of a context-aware logger.
 * This class is designed to defer the creation of a context-specific logger until it is
 * actually needed, reducing initialization overhead. It delegates calls to an underlying
 * logger and initializes context-aware behavior when execution occurs within a specific context.
 *
 * The {@code ExecutionContextLazyLogger} is particularly useful in scenarios where
 * a logging operation requires context-specific information, but that context is
 * expensive to compute, or it might not always be needed.
 *
 * Features:
 * - Lazy initialization of a context-aware logger.
 * - Delegate-based design allowing for fallback behavior to a default logger.
 * - Compatibility with various logging levels (e.g., TRACE, DEBUG, INFO, WARN, ERROR).
 *
 * This class implements the {@link Logger} interface and supports logging with or without markers.
 */
public class ExecutionContextLazyLogger implements Logger {

    private final Logger delegate;
    private final Supplier<Logger> contextAwareLoggerSupplier;
    private volatile Logger contextAwareLogger;

    protected ExecutionContextLazyLogger(Logger delegate, Supplier<Logger> contextAwareLoggerSupplier) {
        this.delegate = delegate;
        this.contextAwareLoggerSupplier = contextAwareLoggerSupplier;
    }

    /**
     * Creates a lazily initialized logger that is context-aware. The logger is only
     * instantiated and made context-aware when it is needed, depending on logging level.
     *
     * @param delegate the base logger that serves as the delegate for logging operations
     * @param context the execution context from which the context-aware logger is created
     * @param contextAwareLoggerFactory a factory function that creates a context-aware logger
     *                                  using the given context and delegate logger
     * @param <C> the type of the execution context, which must extend {@code BaseExecutionContext}
     * @return an instance of {@code ExecutionContextLazyLogger} that defers
     *         logger initialization until it is needed
     */
    public static <C extends BaseExecutionContext> ExecutionContextLazyLogger lazy(
        Logger delegate,
        C context,
        BiFunction<C, Logger, Logger> contextAwareLoggerFactory
    ) {
        return new ExecutionContextLazyLogger(delegate, () -> contextAwareLoggerFactory.apply(context, delegate));
    }

    private Logger contextAwareLogger() {
        if (contextAwareLogger == null) {
            contextAwareLogger = contextAwareLoggerSupplier.get();
        }
        return contextAwareLogger;
    }

    // ===== Level checks - delegate directly =====

    @Override
    public boolean isTraceEnabled() {
        return delegate.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return delegate.isTraceEnabled(marker);
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return delegate.isDebugEnabled(marker);
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return delegate.isInfoEnabled(marker);
    }

    @Override
    public boolean isWarnEnabled() {
        return delegate.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return delegate.isWarnEnabled(marker);
    }

    @Override
    public boolean isErrorEnabled() {
        return delegate.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return delegate.isErrorEnabled(marker);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    // ===== Logging action - check level, then lazy init  =====

    @Override
    public void trace(String msg) {
        if (delegate.isTraceEnabled()) {
            contextAwareLogger().trace(msg);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        if (delegate.isTraceEnabled()) {
            contextAwareLogger().trace(format, arg);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (delegate.isTraceEnabled()) {
            contextAwareLogger().trace(format, arg1, arg2);
        }
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (delegate.isTraceEnabled()) {
            contextAwareLogger().trace(format, arguments);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (delegate.isTraceEnabled()) {
            contextAwareLogger().trace(msg, t);
        }
    }

    @Override
    public void trace(Marker marker, String msg) {
        if (delegate.isTraceEnabled(marker)) {
            contextAwareLogger().trace(marker, msg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        if (delegate.isTraceEnabled(marker)) {
            contextAwareLogger().trace(marker, format, arg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (delegate.isTraceEnabled(marker)) {
            contextAwareLogger().trace(marker, format, arg1, arg2);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        if (delegate.isTraceEnabled(marker)) {
            contextAwareLogger().trace(marker, format, argArray);
        }
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        if (delegate.isTraceEnabled(marker)) {
            contextAwareLogger().trace(marker, msg, t);
        }
    }

    @Override
    public void debug(String msg) {
        if (delegate.isDebugEnabled()) {
            contextAwareLogger().debug(msg);
        }
    }

    @Override
    public void debug(String format, Object arg) {
        if (delegate.isDebugEnabled()) {
            contextAwareLogger().debug(format, arg);
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (delegate.isDebugEnabled()) {
            contextAwareLogger().debug(format, arg1, arg2);
        }
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (delegate.isDebugEnabled()) {
            contextAwareLogger().debug(format, arguments);
        }
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (delegate.isDebugEnabled()) {
            contextAwareLogger().debug(msg, t);
        }
    }

    @Override
    public void debug(Marker marker, String msg) {
        if (delegate.isDebugEnabled(marker)) {
            contextAwareLogger().debug(marker, msg);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        if (delegate.isDebugEnabled(marker)) {
            contextAwareLogger().debug(marker, format, arg);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (delegate.isDebugEnabled(marker)) {
            contextAwareLogger().debug(marker, format, arg1, arg2);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        if (delegate.isDebugEnabled(marker)) {
            contextAwareLogger().debug(marker, format, arguments);
        }
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        if (delegate.isDebugEnabled(marker)) {
            contextAwareLogger().debug(marker, msg, t);
        }
    }

    @Override
    public void info(String msg) {
        if (delegate.isInfoEnabled()) {
            contextAwareLogger().info(msg);
        }
    }

    @Override
    public void info(String format, Object arg) {
        if (delegate.isInfoEnabled()) {
            contextAwareLogger().info(format, arg);
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        if (delegate.isInfoEnabled()) {
            contextAwareLogger().info(format, arg1, arg2);
        }
    }

    @Override
    public void info(String format, Object... arguments) {
        if (delegate.isInfoEnabled()) {
            contextAwareLogger().info(format, arguments);
        }
    }

    @Override
    public void info(String msg, Throwable t) {
        if (delegate.isInfoEnabled()) {
            contextAwareLogger().info(msg, t);
        }
    }

    @Override
    public void info(Marker marker, String msg) {
        if (delegate.isInfoEnabled(marker)) {
            contextAwareLogger().info(marker, msg);
        }
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        if (delegate.isInfoEnabled(marker)) {
            contextAwareLogger().info(marker, format, arg);
        }
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        if (delegate.isInfoEnabled(marker)) {
            contextAwareLogger().info(marker, format, arg1, arg2);
        }
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        if (delegate.isInfoEnabled(marker)) {
            contextAwareLogger().info(marker, format, arguments);
        }
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        if (delegate.isInfoEnabled(marker)) {
            contextAwareLogger().info(marker, msg, t);
        }
    }

    @Override
    public void warn(String msg) {
        if (delegate.isWarnEnabled()) {
            contextAwareLogger().warn(msg);
        }
    }

    @Override
    public void warn(String format, Object arg) {
        if (delegate.isWarnEnabled()) {
            contextAwareLogger().warn(format, arg);
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (delegate.isWarnEnabled()) {
            contextAwareLogger().warn(format, arguments);
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if (delegate.isWarnEnabled()) {
            contextAwareLogger().warn(format, arg1, arg2);
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (delegate.isWarnEnabled()) {
            contextAwareLogger().warn(msg, t);
        }
    }

    @Override
    public void warn(Marker marker, String msg) {
        if (delegate.isWarnEnabled(marker)) {
            contextAwareLogger().warn(marker, msg);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        if (delegate.isWarnEnabled(marker)) {
            contextAwareLogger().warn(marker, format, arg);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        if (delegate.isWarnEnabled(marker)) {
            contextAwareLogger().warn(marker, format, arg1, arg2);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        if (delegate.isWarnEnabled(marker)) {
            contextAwareLogger().warn(marker, format, arguments);
        }
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        if (delegate.isWarnEnabled(marker)) {
            contextAwareLogger().warn(marker, msg, t);
        }
    }

    @Override
    public void error(String msg) {
        if (delegate.isErrorEnabled()) {
            contextAwareLogger().error(msg);
        }
    }

    @Override
    public void error(String format, Object arg) {
        if (delegate.isErrorEnabled()) {
            contextAwareLogger().error(format, arg);
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (delegate.isErrorEnabled()) {
            contextAwareLogger().error(format, arg1, arg2);
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        if (delegate.isErrorEnabled()) {
            contextAwareLogger().error(format, arguments);
        }
    }

    @Override
    public void error(String msg, Throwable t) {
        if (delegate.isErrorEnabled()) {
            contextAwareLogger().error(msg, t);
        }
    }

    @Override
    public void error(Marker marker, String msg) {
        if (delegate.isErrorEnabled(marker)) {
            contextAwareLogger().error(marker, msg);
        }
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        if (delegate.isErrorEnabled(marker)) {
            contextAwareLogger().error(marker, format, arg);
        }
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        if (delegate.isErrorEnabled(marker)) {
            contextAwareLogger().error(marker, format, arg1, arg2);
        }
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        if (delegate.isErrorEnabled(marker)) {
            contextAwareLogger().error(marker, format, arguments);
        }
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        if (delegate.isErrorEnabled(marker)) {
            contextAwareLogger().error(marker, msg, t);
        }
    }
}
