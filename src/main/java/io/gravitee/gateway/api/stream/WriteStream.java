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
package io.gravitee.gateway.api.stream;

import io.gravitee.gateway.api.handler.Handler;

/**
 * Stream writer.
 *
 * Mainly inspired from Vertx.io
 * @see io.vertx.core.streams.WriteStream
 *
 * @author David BRASSELY (david at gravitee.io)
 * @author GraviteeSource Team
 */
public interface WriteStream<T> {

    WriteStream<T> write(T content);

    void end();

    default void end(T t) {
        write(t);
        end();
    }

    default WriteStream<T> drainHandler(Handler<Void> drainHandler) { return this; }

    default boolean writeQueueFull() { return false; }
}
