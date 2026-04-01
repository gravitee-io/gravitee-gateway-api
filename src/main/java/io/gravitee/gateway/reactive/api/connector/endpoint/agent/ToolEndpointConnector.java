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
package io.gravitee.gateway.reactive.api.connector.endpoint.agent;

import io.gravitee.common.service.AbstractService;
import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.ConnectorMode;
import io.gravitee.gateway.reactive.api.connector.Connector;
import io.gravitee.gateway.reactive.api.connector.endpoint.BaseEndpointConnector;
import io.gravitee.gateway.reactive.api.context.agent.ToolExecutionContext;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import java.util.Set;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class ToolEndpointConnector extends AbstractService<Connector> implements BaseEndpointConnector<ToolExecutionContext> {

    static final Set<ConnectorMode> SUPPORTED_MODES = Set.of(ConnectorMode.REQUEST_RESPONSE);

    @Override
    public Set<ConnectorMode> supportedModes() {
        return SUPPORTED_MODES;
    }

    @Override
    public ApiType supportedApi() {
        return ApiType.AGENT;
    }

    public abstract Completable execute(ToolExecutionContext context);

    @Override
    public Completable connect(ToolExecutionContext toolExecutionContext) {
        return execute(toolExecutionContext);
    }
    /**
     * Returns {@code true} if the given tool requires explicit user approval before the agent
     * may invoke it. Subclasses override this to consult their {@code toolsApproval} configuration.
     */
    public boolean requiresApproval(String toolId) {
        return false;
    }

    /**
     * List the tools exposed by this endpoint, scoped to the caller carried in {@code context}.
     * Connectors that hold per-caller state (per-subject MCP sessions, per-tenant tool catalogs, …)
     * use the context to route the call; stateless connectors may ignore it.
     *
     * @param context the tool execution context — same shape as the one passed to
     *                {@link #connect(ToolExecutionContext)}, carries subject and OAuth token.
     */
    public abstract Single<List<ToolSpecification>> getToolsSpecification(ToolExecutionContext context);
}
