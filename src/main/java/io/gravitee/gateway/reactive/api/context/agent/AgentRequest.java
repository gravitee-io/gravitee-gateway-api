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

import io.gravitee.gateway.reactive.api.context.http.HttpPlainRequest;

/**
 * Represents a request handled by the agent layer.
 * Inherits the http-plain surface (headers, body, query parameters, …) and exposes the typed
 * accessors that entrypoints populate from the incoming protocol payload before the agent invoker
 * runs.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface AgentRequest extends HttpPlainRequest {
    /**
     * Get the natural-language query the agent should process for this request.
     *
     * @return the query attached to this request, or {@code null} if the entrypoint has not
     *         populated it yet.
     */
    String query();

    /**
     * Set the natural-language query the agent should process.
     *
     * @param query the query to attach to this request.
     * @return this request.
     */
    AgentRequest query(String query);
}
