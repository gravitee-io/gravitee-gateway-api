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

import io.gravitee.gateway.api.buffer.Buffer;

/**
 * Represents a WebSocket frame.
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface WebSocketFrame {
    Type type();

    /**
     * This one is only valid in case of a <code>TEXT</code> or <code>BINARY</code> frame type.
     * @return
     */
    Buffer data();

    boolean isFinal();

    enum Type {
        // Continuation / 0
        CONTINUATION,
        // Text / 1
        TEXT,
        // Binary / 2
        BINARY,
        // Connection Close / 8
        CLOSE,
        // Ping / 9
        PING,
        // Pong / 10
        PONG,
    }
}
