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
package io.gravitee.gateway.reactive.api.connector.endpoint.async;

import static io.gravitee.gateway.reactive.api.context.InternalContextAttributes.ATTR_INTERNAL_ENTRYPOINT_CONNECTOR;

import io.gravitee.common.service.AbstractService;
import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.ConnectorMode;
import io.gravitee.gateway.reactive.api.connector.Connector;
import io.gravitee.gateway.reactive.api.connector.endpoint.EndpointConnector;
import io.gravitee.gateway.reactive.api.context.ExecutionContext;
import io.gravitee.gateway.reactive.api.qos.Qos;
import io.gravitee.gateway.reactive.api.qos.QosCapability;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Specialized {@link EndpointConnector} for {@link ApiType#MESSAGE}
 */
public abstract class EndpointAsyncConnector extends AbstractService<Connector> implements EndpointConnector {

    public static final String FAILURE_ENDPOINT_CONNECTION_FAILED = "FAILURE_ENDPOINT_CONNECTION_FAILED";
    public static final String FAILURE_ENDPOINT_CONNECTION_CLOSED = "FAILURE_ENDPOINT_CONNECTION_CLOSED";
    public static final String FAILURE_ENDPOINT_CONFIGURATION_INVALID = "FAILURE_ENDPOINT_CONFIGURATION_INVALID";
    public static final String FAILURE_ENDPOINT_UNKNOWN_ERROR = "FAILURE_ENDPOINT_UNKNOWN_ERROR";

    public static final String FAILURE_ENDPOINT_PUBLISH_FAILED = "FAILURE_ENDPOINT_PUBLISH_FAILED";
    public static final String FAILURE_ENDPOINT_SUBSCRIBE_FAILED = "FAILURE_ENDPOINT_SUBSCRIBE_FAILED";
    public static final String FAILURE_PARAMETERS_EXCEPTION = "exception";
    public static final int DEFAULT_FAILURE_CODE = 500;

    @Override
    public ApiType supportedApi() {
        return ApiType.MESSAGE;
    }

    /**
     * Returns a set of {@link Qos} supported by this connector.
     *
     * @return set of {@link Qos} supported by this connector.
     */
    public abstract Set<Qos> supportedQos();

    /**
     * Returns a set of {@link QosCapability} supported by this connector.
     *
     * @return set of {@link QosCapability} supported by this connector.
     */
    public abstract Set<QosCapability> supportedQosCapabilities();

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

    public abstract Completable subscribe(final ExecutionContext ctx);

    public abstract Completable publish(final ExecutionContext ctx);
}
