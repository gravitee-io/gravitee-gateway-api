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
package io.gravitee.gateway.reactive.api.connector.entrypoint;

import io.gravitee.gateway.reactive.api.context.ExecutionContext;
import io.gravitee.gateway.reactive.api.context.http.HttpExecutionContext;
import io.reactivex.rxjava3.core.Completable;

/**
 * Interface describing Entrypoint Connector which could be implemented to deal with new protocol specification
 * @deprecated see {@link HttpEntrypointConnector}
 * @author GraviteeSource Team
 */
@Deprecated(forRemoval = true)
public interface EntrypointConnector extends HttpEntrypointConnector {
    /**
     * Check if incoming request matches entrypoint criteria.
     *
     * @return <code>true</code> if the incoming request matches all criteria, <code>false</code> otherwise
     */
    boolean matches(final ExecutionContext executionContext);

    @Override
    default boolean matches(final HttpExecutionContext ctx) {
        return matches((ExecutionContext) ctx);
    }

    /**
     * Handle incoming request by doing or adding anything to the context anything required by the entrypoint.
     *
     * @return <code>Completable</code>
     */
    Completable handleRequest(final ExecutionContext executionContext);

    @Override
    default Completable handleRequest(final HttpExecutionContext ctx) {
        return handleRequest((ExecutionContext) ctx);
    }

    /**
     * Handle outgoing response by doing or adding anything to the context anything required by the entrypoint.
     *
     * @return <code>Completable</code>
     */
    Completable handleResponse(final ExecutionContext executionContext);

    @Override
    default Completable handleResponse(final HttpExecutionContext ctx) {
        return handleResponse((ExecutionContext) ctx);
    }
}
