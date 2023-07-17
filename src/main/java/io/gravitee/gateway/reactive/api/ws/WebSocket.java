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
package io.gravitee.gateway.reactive.api.ws;

import io.gravitee.gateway.api.buffer.Buffer;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface WebSocket {
    /**
     * Upgrades the associated http request to websocket and returns the same instance once the upgrade has been performed.
     *
     * @return a {@link Single} of the same {@link WebSocket} instance once the upgrade performed.
     */
    Single<WebSocket> upgrade();

    /**
     * Writes the buffer to the websocket as a binary frame.
     *
     * @param buffer the buffer to write.
     *
     * @return a {@link Completable} that completes once writing has been performed.
     */
    Completable write(Buffer buffer);

    /**
     * Writes a ping frame to the connection.
     * <p>
     * This method should only be used for implementing a keep alive or to ensure the client is still responsive,
     * see RFC 6455 Section <a href="https://tools.ietf.org/html/rfc6455#section-5.5.2">section 5.5.2</a>.
     * <p>
     *
     * @return a {@link Completable} that completes once writing has been performed.
     */
    Completable writePing();

    /**
     * Returns the websocket incoming {@link Flowable} of {@link Buffer}.
     *
     * @return a {@link Flowable} of {@link Buffer}.
     */
    Flowable<Buffer> read();

    /**
     * Closes the websocket without any status.
     *
     * @return a {@link Completable} that completes once the close has been performed.
     */
    Completable close();

    /**
     * Closes the websocket with the specified status.
     *
     * @return a {@link Completable} that completes once the close has been performed.
     */
    Completable close(int status);

    /**
     * Closes the websocket with the specified status and reason.
     *
     * @return a {@link Completable} that completes once the close has been performed.
     */
    Completable close(int status, String reason);

    /**
     * Indicates if the http request has already been upgraded to websocket or not.
     *
     * @return <code>true</code> if it has been upgraded, <code>false</code> otherwise.
     * @see #upgrade()
     */
    boolean upgraded();

    /**
     * Indicates if websocket is closed or not.
     *
     * @return <code>true</code> if it is already closed, <code>false</code> otherwise.
     * @see #close()
     * @see #close(int, String)
     */
    boolean closed();
}
