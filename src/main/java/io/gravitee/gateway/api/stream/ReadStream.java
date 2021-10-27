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
 * Stream reader.
 *
 * Mainly inspired from Vertx.io
 * @see io.vertx.core.streams.ReadStream
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface ReadStream<T> {
    ReadStream<T> bodyHandler(Handler<T> bodyHandler);

    ReadStream<T> endHandler(Handler<Void> endHandler);

    default ReadStream<T> pause() {
        return this;
    }

    default ReadStream<T> resume() {
        return this;
    }
}
