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
package io.gravitee.gateway.reactive.api.context.agent;

import io.gravitee.gateway.reactive.api.context.base.BaseExecutionContext;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface ToolExecutionContext extends BaseExecutionContext {
    /**
     * The {@link AgentExecutionContext} this tool call was spawned from, or {@code null} when the
     * tool is invoked outside an agent request (bare-context fallback).
     *
     * <p>Gives tool connectors access to the originating agent request — its identity (transaction
     * id, request id, client identifier), tracer and attributes — so that work performed on behalf
     * of a tool call (e.g. an in-process sub-request) can be correlated to the same agent request.</p>
     *
     * @return the originating agent execution context, or {@code null} if none.
     */
    AgentExecutionContext agentContext();

    ToolRequest request();

    /**
     * The scope this tool call belongs to — what anything keyed by identity must agree on: a stored approval, an
     * upstream OAuth token.
     *
     * <p>The run's by default, which is right whenever the run is the only agent involved. A narrower context
     * substitutes its own for the same reason it may substitute a component: a workflow leaf is not the run, its
     * memory lives under its own key, and a pause inside it is recorded there. Asking the run instead looks somewhere
     * nothing was ever written — an approval granted for a leaf's tool was never found again, so the agent asked for
     * it on every turn.</p>
     */
    default AgentMemoryId memoryId() {
        return agentContext() != null ? agentContext().memoryId() : null;
    }
}
