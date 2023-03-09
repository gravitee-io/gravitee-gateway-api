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
package io.gravitee.gateway.jupiter.api.connector.entrypoint.async;

import io.gravitee.gateway.jupiter.api.ConnectorMode;
import io.gravitee.gateway.jupiter.api.ListenerType;
import io.gravitee.gateway.jupiter.api.context.ExecutionContext;
import io.gravitee.gateway.jupiter.api.qos.QosRequirement;
import io.reactivex.rxjava3.core.Completable;
import java.util.Set;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
class EntrypointAsyncConnectorImpl extends EntrypointAsyncConnector {

    @Override
    public String id() {
        return null;
    }

    @Override
    public Set<ConnectorMode> supportedModes() {
        return null;
    }

    @Override
    public ListenerType supportedListenerType() {
        return null;
    }

    @Override
    public int matchCriteriaCount() {
        return 0;
    }

    @Override
    public boolean matches(ExecutionContext executionContext) {
        return false;
    }

    @Override
    public Completable handleRequest(ExecutionContext executionContext) {
        return null;
    }

    @Override
    public Completable handleResponse(ExecutionContext executionContext) {
        return null;
    }

    @Override
    public QosRequirement qosRequirement() {
        return null;
    }
}
