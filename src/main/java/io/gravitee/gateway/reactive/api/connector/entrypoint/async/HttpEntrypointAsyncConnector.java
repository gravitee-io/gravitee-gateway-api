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
package io.gravitee.gateway.reactive.api.connector.entrypoint.async;

import io.gravitee.common.service.AbstractService;
import io.gravitee.common.utils.RxHelper;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.connector.Connector;
import io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnector;
import io.gravitee.gateway.reactive.api.connector.entrypoint.HttpEntrypointConnector;
import io.gravitee.gateway.reactive.api.message.DefaultMessage;
import io.gravitee.gateway.reactive.api.message.Message;
import io.gravitee.gateway.reactive.api.qos.QosRequirement;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.processors.BehaviorProcessor;

/**
 * Specialized {@link HttpEntrypointConnector} for {@link ApiType#MESSAGE}
 */
public abstract class HttpEntrypointAsyncConnector
    extends AbstractService<Connector>
    implements HttpEntrypointConnector, EntrypointConnector {

    public static final String STOP_MESSAGE_ID = "goaway";
    public static final String STOP_MESSAGE_CONTENT = "Stopping, please reconnect";

    protected final BehaviorProcessor<Message> stopHook = BehaviorProcessor.create();

    @Override
    public ApiType supportedApi() {
        return ApiType.MESSAGE;
    }

    /**
     * @return {@link QosRequirement} defined by this entrypoint.
     */
    public abstract QosRequirement qosRequirement();

    protected void emitStopMessage() {
        if (stopHook.hasSubscribers()) {
            try {
                stopHook.onNext(
                    DefaultMessage.builder().id(STOP_MESSAGE_ID).error(true).content(Buffer.buffer(STOP_MESSAGE_CONTENT)).build()
                );
                stopHook.onComplete();
            } catch (UndeliverableException unused) {
                // Undeliverable exception may occur if the subscriber already cancelled the subscription.We can safely ignore it.
            }
        }
    }

    protected FlowableTransformer<Message, Message> applyStopHook() {
        return RxHelper.mergeWithFirst(stopHook);
    }
}
