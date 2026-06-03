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

import io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext;
import io.reactivex.rxjava3.core.Completable;

/**
 * Agent execution context allowing access to {@link AgentRequest} and {@link AgentResponse}.
 * Inherits the http-plain surface so the reactor's existing policy chain keeps working unchanged.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface AgentExecutionContext extends HttpPlainExecutionContext {
    /**
     * Get the current request attached to this execution context.
     *
     * @return the request attached to this execution context.
     */
    @Override
    AgentRequest request();

    /**
     * Get the current response attached to this execution context.
     *
     * @return the response attached to this execution context.
     */
    @Override
    AgentResponse response();

    /**
     * Flushes the current response to the client and detaches the context from the wire.
     * The HTTP status and body currently set on the response are sent immediately; subsequent
     * writes by policies or the invoker can still be performed but are kept in memory and will
     * not reach the client. Useful when an entrypoint needs to acknowledge the caller quickly
     * (e.g. a Slack webhook expecting a 200 within 3 seconds) and continue processing the
     * request asynchronously.
     *
     * @return a {@link Completable} that completes once the response has been flushed and the
     *         context has been detached.
     */
    Completable async();
}
