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
package io.gravitee.gateway.reactive.api.context.sync;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.reactive.api.context.Response;
import io.reactivex.*;

public interface SyncResponse extends Response<Buffer> {
    /**
     * Get the current body response as a {@link Maybe}. If no body has not been set on the response yet, an empty {@link Maybe} will returned.
     *
     * By getting the body as a {@link Maybe}, the current body chunks will be merged together to reconstruct the entire body in provide it in the form of a single {@link Buffer}
     * This is useful when the entire body is required to apply some transformation or any manipulation.
     *
     * <b>WARN:</b> beware that the entire body content will be loaded in memory. You should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Maybe} observable containing the current entire body response or empty if response body as not been set yet.
     * @see #getBodyOrEmpty()
     * @see #setBody(Maybe)
     * @see #setChunkedBody(Flowable)
     */
    Maybe<Buffer> getBody();

    /**
     * Same as {@link #getBody()} but returns a {@link Single} of {@link Buffer} instead.
     *
     * If no body response as been set yet, it returns a {@link Single} with an empty {@link Buffer}.
     * It is a convenient way that avoid checking if the body is set or not prior to manipulate it.
     *
     * @return a {@link Single} observable containing the current entire body response or empty an {@link Buffer) if response body as not been set yet.
     * @see #getBody()
     * @see #setBody(Maybe)
     * @see #setChunkedBody(Flowable)
     */
    Single<Buffer> getBodyOrEmpty();

    /**
     * Set the current body response as a {@link Maybe}.
     * This is useful when you want to replace the current body response with a specific content, ex:
     *
     * <code>
     *     response.setBody(Maybe.just("My custom content");
     * </code>
     *
     * <b>WARN:</b> replacing the response body will "drain" the previous response that was in place.
     *
     * @return a {@link Completable} that can be used to continue the reactive chain.
     * @see #getBody()
     * @see #setChunkedBody(Flowable)
     */
    Completable setBody(Maybe<Buffer> buffer);

    /**
     * Set the current body response as a {@link Buffer}.
     * This is useful when you want to replace the current body response with a specific content that doesn't come from a reactive chain, ex:
     *
     * <code>
     *     response.setBody(Buffer.buffer("My custom content");
     * </code>
     *
     * <b>WARN:</b> replacing the response body will "drain" the previous response that was in place.
     *
     * @return a {@link Completable} that can be used to continue the reactive chain.
     * @see #setBody(Maybe)
     * @see #setChunkedBody(Flowable)
     */
    Completable setBody(final Buffer buffer);

    /**
     * Set the current response body chunks from a {@link Flowable} of {@link Buffer}.
     * This is useful to directly pump the upstream chunks to the downstream without having to load all the chunks in memory.
     * <br/>
     * <b>WARN:</b> replacing the response body will "drain" the previous response that was in place.
     *
     * @param chunks the flowable of chunks representing the response to push back to the downstream.
     *
     * @return a {@link Completable} that can be chain with the rest of the execution flow.
     * @see #getBody()
     * @see #setBody(Maybe)
     */
    Completable setChunkedBody(final Flowable<Buffer> chunks);

    /**
     * Get the current response body chunks as {@link Flowable} of {@link Buffer}.
     * This is useful when you want to manipulate the entire body without having to load it in memory.
     *
     * <b>WARN:</b> you should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Flowable} containing the current body response chunks.
     */
    Flowable<Buffer> getChunkedBody();

    /**
     * End the response.
     *
     * @return an observable that can be easily chained.
     */
    Completable end();

    Completable onBuffer(final FlowableTransformer<Buffer, Buffer> bufferTransformer);

    Completable onBody(final FlowableTransformer<Buffer, Buffer> bodyTransformer);
}
