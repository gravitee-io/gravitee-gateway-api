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
package io.gravitee.gateway.reactive.api.exception;

import io.gravitee.gateway.api.buffer.Buffer;

/**
 * Signals that a payload written with {@link io.gravitee.gateway.reactive.api.ws.WebSocket#writeText(Buffer)} is not valid UTF-8.
 * Nothing was sent, and the websocket is still open.
 * <p>
 * The message never contains the payload.
 *
 * @author Mateusz Jóźwiak (mateusz.jozwiak at incubly.com)
 * @author GraviteeSource Team
 */
public class InvalidTextPayloadException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidTextPayloadException(String message) {
        super(message);
    }

    public InvalidTextPayloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
