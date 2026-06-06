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

import io.gravitee.gateway.reactive.api.context.http.HttpPlainResponse;
import io.reactivex.rxjava3.core.Flowable;

/**
 * Represents a response produced by the agent layer.
 * Inherits the http-plain surface (status, headers, body) for the wire envelope and adds a typed
 * event channel the invoker populates with the agent's streamed output. Entrypoints consume the
 * event channel and translate it into whatever shape their protocol speaks.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface AgentResponse extends HttpPlainResponse {
    /**
     * Get the current stream of events produced by the agent.
     *
     * @return the {@link Flowable} of events attached to this response, or {@code null} if the
     *         invoker has not populated it.
     */
    Flowable<AgentEvent> events();

    /**
     * Set the stream of events that the entrypoint will consume.
     *
     * @param events the {@link Flowable} of events emitted by the agent.
     */
    void events(Flowable<AgentEvent> events);
}
