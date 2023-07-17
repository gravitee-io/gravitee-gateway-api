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
package io.gravitee.gateway.api;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.api.http2.HttpFrame;
import io.gravitee.gateway.api.stream.WriteStream;

/**
 * Represents a server-side HTTP response.
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Response extends WriteStream<Buffer> {
    Response status(int statusCode);

    int status();

    default void end() {}

    default Response endHandler(Handler<Void> endHandler) {
        return this;
    }

    /**
     * Reason-Phrase is intended to give a short textual description of the Status-Code.
     * @return
     */
    String reason();

    Response reason(String message);

    /**
     * @return the headers in the response.
     */
    HttpHeaders headers();

    /**
     * @return has the response already ended?
     */
    boolean ended();

    HttpHeaders trailers();

    default Response writeCustomFrame(HttpFrame frame) {
        return this;
    }
}
