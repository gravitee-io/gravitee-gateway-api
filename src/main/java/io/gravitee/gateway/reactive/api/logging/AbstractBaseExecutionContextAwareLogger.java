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

import static io.gravitee.gateway.reactive.api.context.ContextAttributes.ATTR_API;
import static io.gravitee.gateway.reactive.api.context.ContextAttributes.ATTR_API_NAME;
import static io.gravitee.gateway.reactive.api.context.ContextAttributes.ATTR_APPLICATION;
import static io.gravitee.gateway.reactive.api.context.ContextAttributes.ATTR_ENVIRONMENT;
import static io.gravitee.gateway.reactive.api.context.ContextAttributes.ATTR_ORGANIZATION;
import static io.gravitee.gateway.reactive.api.context.ContextAttributes.ATTR_PLAN;
import static io.gravitee.gateway.reactive.api.context.ContextAttributes.ATTR_USER;
import static io.gravitee.gateway.reactive.api.context.InternalContextAttributes.ATTR_INTERNAL_API_TYPE;

import io.gravitee.gateway.reactive.api.context.ContextAttributes;
import io.gravitee.gateway.reactive.api.context.base.BaseExecutionContext;
import io.gravitee.node.api.Node;
import io.gravitee.node.logging.LogEntry;
import io.gravitee.node.logging.LogEntryFactory;
import io.gravitee.node.logging.NodeAwareLogger;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;

public abstract class AbstractBaseExecutionContextAwareLogger<C extends BaseExecutionContext> extends NodeAwareLogger {

    private static final Set<LogEntry<BaseExecutionContext>> BASE_EXECUTION_CONTEXT_LOG_ENTRIES = Set.of(
        // API will not change for a given execution context, so cached
        LogEntryFactory.cached("apiId", BaseExecutionContext.class, context -> context.getAttribute(ATTR_API)),
        LogEntryFactory.cached("apiName", BaseExecutionContext.class, context -> context.getAttribute(ATTR_API_NAME)),
        LogEntryFactory.cached("apiType", BaseExecutionContext.class, context -> context.getInternalAttribute(ATTR_INTERNAL_API_TYPE)),
        LogEntryFactory.cached("environment", BaseExecutionContext.class, context -> context.getAttribute(ATTR_ENVIRONMENT)),
        LogEntryFactory.cached("organization", BaseExecutionContext.class, context -> context.getAttribute(ATTR_ORGANIZATION)),
        // Plan and Application will change for each request, so refreshable
        LogEntryFactory.refreshable("application", BaseExecutionContext.class, context -> context.getAttribute(ATTR_APPLICATION)),
        LogEntryFactory.refreshable("plan", BaseExecutionContext.class, context -> context.getAttribute(ATTR_PLAN)),
        LogEntryFactory.refreshable("user", BaseExecutionContext.class, context -> context.getAttribute(ATTR_USER))
    );

    protected final C context;

    public AbstractBaseExecutionContextAwareLogger(C context, Logger logger) {
        super(Objects.requireNonNull(context).getComponent(Node.class), logger);
        this.context = context;
    }

    @Override
    protected void registerLogEntries(Set<LogEntry<?>> entries) {
        super.registerLogEntries(entries);
        entries.addAll(BASE_EXECUTION_CONTEXT_LOG_ENTRIES);
    }

    @Override
    protected void registerLogSources(Map<Class<?>, Object> logSources) {
        super.registerLogSources(logSources);
        logSources.putIfAbsent(BaseExecutionContext.class, context);
    }
}
