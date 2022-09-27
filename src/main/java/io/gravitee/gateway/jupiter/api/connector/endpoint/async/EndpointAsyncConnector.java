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
package io.gravitee.gateway.jupiter.api.connector.endpoint.async;

import static io.gravitee.gateway.jupiter.api.context.InternalContextAttributes.ATTR_INTERNAL_ENTRYPOINT_CONNECTOR;

import io.gravitee.common.service.AbstractService;
import io.gravitee.gateway.jupiter.api.ApiType;
import io.gravitee.gateway.jupiter.api.ConnectorMode;
import io.gravitee.gateway.jupiter.api.connector.Connector;
import io.gravitee.gateway.jupiter.api.connector.endpoint.EndpointConnector;
import io.gravitee.gateway.jupiter.api.context.ExecutionContext;
import io.gravitee.gateway.jupiter.api.qos.Qos;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Specialized {@link EndpointConnector} for {@link ApiType#ASYNC}
 */
public abstract class EndpointAsyncConnector extends AbstractService<Connector> implements EndpointConnector {

    @Override
    public ApiType supportedApi() {
        return ApiType.ASYNC;
    }

    /**
     * Returns a set of {@link ConnectorMode} supported by this connector. It will be used to resolve the proper connector.
     *
     * @return set of {@link ConnectorMode} supported by this connector.
     */
    public abstract Set<Qos> supportedQos();

    @Override
    public Completable connect(final ExecutionContext ctx) {
        return Completable.defer(() -> {
            List<CompletableSource> completableList = new ArrayList<>();
            Connector entrypointConnector = ctx.getInternalAttribute(ATTR_INTERNAL_ENTRYPOINT_CONNECTOR);
            if (entrypointConnector != null) {
                if (entrypointConnector.supportedModes().contains(ConnectorMode.PUBLISH)) {
                    completableList.add(publish(ctx));
                }
                if (entrypointConnector.supportedModes().contains(ConnectorMode.SUBSCRIBE)) {
                    completableList.add(subscribe(ctx));
                }
            }
            return Completable.merge(completableList);
        });
    }

    protected abstract Completable subscribe(final ExecutionContext ctx);

    protected abstract Completable publish(final ExecutionContext ctx);
}
