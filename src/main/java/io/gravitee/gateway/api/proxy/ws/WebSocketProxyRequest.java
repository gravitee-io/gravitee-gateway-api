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
package io.gravitee.gateway.api.proxy.ws;

import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.api.proxy.ProxyRequest;
import io.gravitee.gateway.api.ws.WebSocketFrame;
import java.util.concurrent.CompletableFuture;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface WebSocketProxyRequest extends ProxyRequest {
    CompletableFuture<WebSocketProxyRequest> upgrade();

    WebSocketProxyRequest reject(int statusCode);

    WebSocketProxyRequest write(WebSocketFrame frame);

    WebSocketProxyRequest close();

    WebSocketProxyRequest frameHandler(Handler<WebSocketFrame> handler);

    WebSocketProxyRequest closeHandler(Handler<Void> handler);
}
