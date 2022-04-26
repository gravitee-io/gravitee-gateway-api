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
package io.gravitee.gateway.api.proxy.ws;

import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.api.proxy.builder.ProxyRequestImpl;
import io.gravitee.gateway.api.ws.WebSocket;
import io.gravitee.gateway.api.ws.WebSocketFrame;
import java.util.concurrent.CompletableFuture;

public class WebSocketProxyRequestImpl extends ProxyRequestImpl implements WebSocketProxyRequest {

    private final WebSocket websocket;

    public WebSocketProxyRequestImpl(final Request request) {
        super(request);
        this.websocket = request.websocket();
    }

    @Override
    public CompletableFuture<WebSocketProxyRequest> upgrade() {
        return websocket.upgrade().thenApply(webSocket -> WebSocketProxyRequestImpl.this);
    }

    @Override
    public WebSocketProxyRequest reject(int statusCode) {
        websocket.reject(statusCode);
        return this;
    }

    @Override
    public WebSocketProxyRequest write(WebSocketFrame frame) {
        this.websocket.write(frame);
        return this;
    }

    @Override
    public WebSocketProxyRequest close() {
        this.websocket.close();
        return this;
    }

    @Override
    public WebSocketProxyRequest frameHandler(Handler<WebSocketFrame> handler) {
        this.websocket.frameHandler(handler);
        return this;
    }

    @Override
    public WebSocketProxyRequest closeHandler(Handler<Void> handler) {
        this.websocket.closeHandler(handler);
        return this;
    }
}
