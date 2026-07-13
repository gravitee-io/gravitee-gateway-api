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
 * The context of a call to a remote agent, sibling of {@link ToolExecutionContext}: same shape, other kind of
 * upstream. Where a tool call carries a function name and its JSON arguments, an agent call carries a message, its
 * named inputs and the conversation it continues.
 *
 * @author GraviteeSource Team
 */
public interface AgentCallContext extends BaseExecutionContext {
    /**
     * The {@link AgentExecutionContext} this call was spawned from, or {@code null} when the remote agent is invoked
     * outside an agent request. Gives the connector access to the originating request (its identity, subject, tracer
     * and attributes), so the remote call correlates to the same agent request.
     *
     * @return the originating agent execution context, or {@code null} if none.
     */
    AgentExecutionContext agentContext();

    AgentCall request();
}
