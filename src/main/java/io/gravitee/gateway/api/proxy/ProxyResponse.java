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
package io.gravitee.gateway.api.proxy;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.api.http2.HttpFrame;
import io.gravitee.gateway.api.stream.ReadStream;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface ProxyResponse extends ReadStream<Buffer> {
    /**
     * @return HTTP status code.
     */
    int status();

    /**
     * Reason-Phrase is intended to give a short textual description of the Status-Code.
     * @return
     */
    default String reason() {
        return null;
    }

    /**
     * @return the headers in the response.
     */
    HttpHeaders headers();

    /**
     * Is the response connected to an upstream or it is a 'direct' response from the proxy itself.
     *
     * @return
     */
    default boolean connected() {
        return true;
    }

    /**
     * For HTTP/2 request
     *
     * @param frameHandler The handler to call when getting a custom HTTP Frame.
     * @return
     */
    default ProxyResponse customFrameHandler(Handler<HttpFrame> frameHandler) {
        return this;
    }

    default HttpHeaders trailers() {
        return null;
    }

    default ProxyResponse cancelHandler(Handler<Void> cancelHandler) {
        return this;
    }

    default void cancel() {}
}
