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
package io.gravitee.gateway.jupiter.api.context;

import io.gravitee.gateway.api.buffer.Buffer;
import io.reactivex.Completable;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Response extends HttpResponse, MessageResponse {
    /**
     * Ends the response.
     *
     * @return a {@link Completable} that completes once the response ends.
     */
    Completable end();

    /**
     * Ends the response with the provided content.
     *
     * @return a {@link Completable} that completes once the response ends.
     */
    Completable end(final Buffer buffer);

    /**
     * Writes the provided content to the response.
     *
     * @return a {@link Completable} that completes once write has been performed.
     */
    Completable write(final Buffer buffer);

    /**
     * Write the headers to the response.
     * <b>WARN:</b> this operation should not be done more than once!
     *
     * @return a {@link Completable} that completes once the headers has been written.
     */
    Completable writeHeaders();
}
