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

import io.gravitee.common.service.AbstractService;
import io.gravitee.common.utils.RxHelper;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.jupiter.api.ApiType;
import io.gravitee.gateway.jupiter.api.ConnectorMode;
import io.gravitee.gateway.jupiter.api.connector.Connector;
import io.gravitee.gateway.jupiter.api.connector.entrypoint.EntrypointConnector;
import io.gravitee.gateway.jupiter.api.message.DefaultMessage;
import io.gravitee.gateway.jupiter.api.message.Message;
import io.gravitee.gateway.jupiter.api.qos.Qos;
import io.gravitee.gateway.jupiter.api.qos.QosOptions;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.processors.BehaviorProcessor;
import java.util.Set;

/**
 * Specialized {@link EntrypointConnector} for {@link ApiType#ASYNC}
 */
public abstract class EntrypointAsyncConnector extends AbstractService<Connector> implements EntrypointConnector {

    public static final String STOP_MESSAGE_ID = "goaway";
    public static final String STOP_MESSAGE_CONTENT = "Stopping, please reconnect";

    protected final BehaviorProcessor<Message> stopHook = BehaviorProcessor.create();

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

    /**
     * @return {@link QosOptions} defined by this entrypoint.
     */
    public abstract QosOptions qosOptions();

    protected void emitStopMessage() {
        stopHook.onNext(DefaultMessage.builder().id(STOP_MESSAGE_ID).error(true).content(Buffer.buffer(STOP_MESSAGE_CONTENT)).build());
        stopHook.onComplete();
    }

    protected FlowableTransformer<Message, Message> applyStopHook() {
        return RxHelper.mergeWithFirst(stopHook);
    }
}
