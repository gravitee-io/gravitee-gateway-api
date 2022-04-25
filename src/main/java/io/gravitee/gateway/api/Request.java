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
package io.gravitee.gateway.api;

import io.gravitee.common.http.HttpHeaders;
import io.gravitee.common.http.HttpMethod;
import io.gravitee.common.http.HttpVersion;
import io.gravitee.common.util.MultiValueMap;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.api.http2.HttpFrame;
import io.gravitee.gateway.api.stream.ReadStream;
import io.gravitee.gateway.api.ws.WebSocket;
import io.gravitee.reporter.api.http.Metrics;
import javax.net.ssl.SSLSession;

/**
 * Represents a server-side HTTP request.
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Request extends ReadStream<Buffer> {
    String id();

    /**
     * Propagated transaction id between multiple requests.
     *
     * @return Transaction ID.
     */
    String transactionId();

    /**
     * The URL the client used to make the request.
     *
     * @return the URI of the request.
     */
    String uri();

    /**
     * Returns the part of this request's URL from the protocol name up to the query string in the first line
     * of the HTTP request.
     *
     * @return The path part of the uri.
     */
    String path();

    /**
     * Return the <code>path</code> without the <code>context-path</code>.
     *
     * @return
     */
    String pathInfo();

    /**
     * Returns the portion of the request URI that indicates the context of the request.
     *
     * @return
     */
    String contextPath();

    /**
     * @return the query parameters in the request
     */
    MultiValueMap<String, String> parameters();

    /**
     * @return the path parameters in the request
     */
    MultiValueMap<String, String> pathParameters();

    /**
     * @return the headers in the request.
     */
    HttpHeaders headers();

    /**
     * @return the HTTP method for the request.
     */
    HttpMethod method();

    String scheme();

    /**
     * @return the HTTP version of the request
     */
    HttpVersion version();

    /**
     * The timestamp for when this request was received.
     * Specifically, this is the timestamp of creation of the request object.
     *
     * @return the instant timestamp for the request.
     */
    long timestamp();

    /**
     * Returns the Internet Protocol (IP) address of the client or last proxy that sent the request.
     *
     * @return a <code>String</code> containing the IP address of the client that sent the request.
     */
    String remoteAddress();

    /**
     * Returns the Internet Protocol (IP) address of the interface on which the request was received.
     *
     * @return a <code>String</code> containing the IP address on which the request was received.
     */
    String localAddress();

    /**
     * @return SSLSession associated to the request. Returns <code>null</code> if not an SSL connection.
     * @see javax.net.ssl.SSLSession
     *
     */
    SSLSession sslSession();

    Metrics metrics();

    boolean ended();

    /**
     * Define an {@code Handler} when the request timeout has been reached
     * @param timeoutHandler The handler to call when the timeout has been reached
     * @return The current request
     */
    Request timeoutHandler(Handler<Long> timeoutHandler);

    /**
     * Returns the timeout handler
     * @return
     */
    Handler<Long> timeoutHandler();

    boolean isWebSocket();

    WebSocket websocket();

    /**
     * For HTTP/2 request
     *
     * @param frameHandler The handler to call when getting a custom HTTP Frame
     * @return
     */
    Request customFrameHandler(Handler<HttpFrame> frameHandler);
}
