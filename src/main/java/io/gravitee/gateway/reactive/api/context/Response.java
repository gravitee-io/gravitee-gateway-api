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
package io.gravitee.gateway.reactive.api.context;

import io.gravitee.gateway.api.http.HttpHeaders;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface Response<T> {
    Response<T> status(int statusCode);

    int status();

    /**
     * @return Reason-Phrase is intended to give a short textual description of the Status-Code.
     */
    String reason();

    Response<T> reason(final String message);

    /**
     * @return the headers in the response.
     */
    HttpHeaders headers();

    HttpHeaders trailers();

    Completable end();

    /**
     * Indicates if the response is ended or not.
     * Ended response means the response has been fully push to the client, including response body.
     *
     * @return <code>true</code> if the response has been fully pushed to the client, <code>false</code> else.
     */
    boolean ended();

    /**
     * Get the response content as a {@link Flowable} of T representing each chunk of data.
     * For example, for {@link io.gravitee.gateway.reactive.api.context.sync.SyncResponse}, chunks are {@link io.gravitee.gateway.api.buffer.Buffer}
     * and for {@link io.gravitee.gateway.reactive.api.context.async.AsyncResponse}, it will be {@link io.gravitee.gateway.reactive.api.context.async.Message}
     *
     * @return a {@link Flowable} representing the data manipulated by the response.
     */
    Flowable<T> content();

    /**
     * Replaces the response content with the given {@link Flowable}.
     * The implementation must guaranty the reactive chain will be preserved by composing with the previous response content to make sure it will be well consumed and replaced.
     *
     * @return a {@link Completable} that can be used to continue the reactive chain.
     */
    Completable content(Flowable<T> content);
}
