/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.jupiter.api.connector.entrypoint;

import io.gravitee.gateway.jupiter.api.ListenerType;
import io.gravitee.gateway.jupiter.api.connector.Connector;
import io.gravitee.gateway.jupiter.api.context.ExecutionContext;
import io.reactivex.Completable;

/**
 * Interface describing Entrypoint Connector which could be implemented to deal with new protocol specification
 *
 * @author GraviteeSource Team
 */
public interface EntrypointConnector extends Connector {
    /**
     * Returns the {@link ListenerType} supported by this entrypoint. It will be used to filter available entrypoint when creating a new API
     *
     * @return {@link ListenerType} supported by this entrypoint.
     */
    ListenerType supportedListenerType();

    /**
     * Returns the number of criteria used in {{@link #matches(ExecutionContext)}. This number is used to sort compatible entrypoint from a request in the descending order.
     *
     * @return Number of criteria used in {{@link #matches(ExecutionContext)}
     */
    int matchCriteriaCount();

    /**
     * Check if incoming request matches entrypoint criteria.
     *
     * @return <code>true</code> if the incoming request matches all criteria, <code>false</code> otherwise
     */
    boolean matches(final ExecutionContext executionContext);

    /**
     * Handle incoming request by doing or adding anything to the context anything required by the entrypoint.
     *
     * @return <code>Completable</code>
     */
    Completable handleRequest(final ExecutionContext executionContext);

    /**
     * Handle outgoing response by doing or adding anything to the context anything required by the entrypoint.
     *
     * @return <code>Completable</code>
     */
    Completable handleResponse(final ExecutionContext executionContext);
}
