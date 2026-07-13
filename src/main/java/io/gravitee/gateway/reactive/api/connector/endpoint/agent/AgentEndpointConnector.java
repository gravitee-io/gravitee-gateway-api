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
import io.gravitee.gateway.reactive.api.context.agent.AgentCallContext;
import io.gravitee.gateway.reactive.api.context.agent.AgentCallResult;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import java.util.Set;

/**
 * An upstream that is an <b>agent</b>, not a tool: one connector per agent-to-agent protocol (OpenAI Responses, A2A,
 * …), exactly as {@link ToolEndpointConnector} is one connector per tool protocol (HTTP, MCP, …). The caller hands it
 * a message and gets an answer back; how that travels the wire, and how the remote is authenticated, is the
 * connector's business alone.
 *
 * <p>Authentication follows the tool contract: the connector attaches what it can on its own (a bearer token, a
 * machine-to-machine client-credentials token it mints and caches), and <b>throws</b>
 * {@link io.gravitee.gateway.reactive.api.connector.endpoint.agent.exception.AuthenticationRequiredException} carrying
 * its {@code AuthenticationConfiguration} when the remote needs an end-user authorization it cannot obtain by itself.
 * The reactor pauses the run and drives the browser round-trip.</p>
 *
 * @author GraviteeSource Team
 */
public abstract class AgentEndpointConnector extends AbstractService<Connector> implements BaseEndpointConnector<AgentCallContext> {

    static final Set<ConnectorMode> SUPPORTED_MODES = Set.of(ConnectorMode.REQUEST_RESPONSE);

    @Override
    public Set<ConnectorMode> supportedModes() {
        return SUPPORTED_MODES;
    }

    @Override
    public ApiType supportedApi() {
        return ApiType.AGENT;
    }

    /**
     * Invokes the remote agent for one turn.
     *
     * @param context the call (message, named inputs, conversation id), plus the originating agent request.
     * @return the agent's answer, or empty when the remote produced none.
     */
    public abstract Maybe<AgentCallResult> execute(AgentCallContext context);

    @Override
    public Completable connect(AgentCallContext context) {
        return execute(context).ignoreElement();
    }
}
