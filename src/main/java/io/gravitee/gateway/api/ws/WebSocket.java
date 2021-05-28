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
package io.gravitee.gateway.api.ws;

import io.gravitee.gateway.api.handler.Handler;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a server-side WebSocket request.
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface WebSocket {

    /**
     * Upgrade the HTTP request to a WS connection
     * @return
     */
    CompletableFuture<WebSocket> upgrade();

    /**
     * WS connection has been rejected by upstream.
     * @param statusCode
     * @return
     */
    WebSocket reject(int statusCode);

    WebSocket write(WebSocketFrame frame);

    WebSocket close();

    WebSocket frameHandler(Handler<WebSocketFrame> frameHandler);

    WebSocket closeHandler(Handler<Void> closeHandler);

    boolean upgraded();
}
