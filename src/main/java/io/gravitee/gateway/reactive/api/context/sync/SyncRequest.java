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
import io.gravitee.gateway.reactive.api.context.Request;
import io.reactivex.*;

public interface SyncRequest extends Request<Buffer> {
    /**
     * Get the current body request as a {@link Maybe}.
     *
     * By getting the body as a {@link Maybe}, the current body chunks will be merged together to reconstruct the entire body in provide it in the form of a single {@link Buffer}
     * This is useful when the entire body is required to apply some transformation or any manipulation.
     *
     * <b>WARN:</b> beware that the entire body content will be loaded in memory. You should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Maybe} observable containing the current entire body request or empty if request body as not been set yet.
     * @see #getBodyOrEmpty()
     * @see #setBody(Maybe)
     * @see #setChunkedBody(Flowable)
     */
    Maybe<Buffer> getBody();

    /**
     * Same as {@link #getBody()} but returns a {@link Single} of {@link Buffer} instead.
     *
     * If no body request as been set yet, it returns a {@link Single} with an empty {@link Buffer}.
     * It is a convenient way that avoid checking if the body is set or not prior to manipulate it.
     *
     * @return a {@link Single} observable containing the current entire body request or empty an {@link Buffer) if request body as not been set yet.
     * @see #getBody()
     * @see #setBody(Maybe)
     * @see #setChunkedBody(Flowable)
     */
    Single<Buffer> getBodyOrEmpty();

    /**
     * Set the current body request as a {@link Maybe}.
     * This is useful when you want to replace the current body request with a specific content, ex:
     *
     * <code>
     *     request.setBody(Maybe.just("My custom content");
     * </code>
     *
     * <b>WARN:</b> replacing the request body will "drain" the previous request that was in place.
     *
     * @return a {@link Completable} that can be used to continue the reactive chain.
     * @see #getBody()
     * @see #setChunkedBody(Flowable)
     */
    Completable setBody(final Maybe<Buffer> buffer);

    /**
     * Set the current body request as a {@link Buffer}.
     * This is useful when you want to replace the current body request with a specific content that doesn't come from a reactive chain, ex:
     *
     * <code>
     *     request.setBody(Buffer.buffer("My custom content");
     * </code>
     *
     * <b>WARN:</b> replacing the request body will "drain" the previous request that was in place.
     *
     * @return a {@link Completable} that can be used to continue the reactive chain.
     * @see #getBody()
     * @see #setChunkedBody(Flowable)
     */
    Completable setBody(final Buffer buffer);

    /**
     * Set the current request body chunks from a {@link Flowable} of {@link Buffer}.
     * This is useful to directly pump the upstream chunks to the downstream without having to load all the chunks in memory.
     *
     * <b>WARN:</b> replacing the request body will "drain" the previous request that was in place.
     *
     * @param chunks the flowable of chunks representing the request body that will be pushed to the upstream.
     *
     * @return a {@link Completable} that can be chained with the rest of the execution flow.
     * @see #getBody()
     * @see #setBody(Maybe)
     */
    Completable setChunkedBody(final Flowable<Buffer> chunks);

    /**
     * Get the current request body chunks as {@link Flowable} of {@link Buffer}.
     * This is useful when you want to manipulate the entire body without having to load it in memory.
     *
     * <b>WARN:</b> you should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Flowable} containing the current body request chunks.
     */
    Flowable<Buffer> getChunkedBody();

    /**
     * Applies a transformation on each body chunks.
     * This is useful to apply a transformation directly on the request chunks in a convenient way.
     *
     * The following code:
     * <code>
     *     request.setChunkBody(request.getChunkBody().flatMap(chunk -> Buffer.buffer(chunk.toString().toUpperCase())));
     * </code>
     *
     * is equivalent with:
     * <code>
     *     request.onChunk(chunks -> chunks.map(chunk -> Buffer.buffer(chunk.toString().toUpperCase()));
     * </code>
     *
     * @param chunkTransformer the transformer that will be applied.
     * @return a {@link Completable} that can be chained with the rest of the execution flow.
     */
    Completable onChunk(final FlowableTransformer<Buffer, Buffer> chunkTransformer);

    /**
     * Applies a transformation on the complete body.
     * This is a convenient way to retrieve the whole body and apply a transformation once.
     *
     * The following code:
     * <code>
     *     request.setBody(request.getBody().flatMap(chunk -> Buffer.buffer(chunk.toString().toUpperCase())));
     * </code>
     *
     * is equivalent with:
     * <code>
     *     request.onBody(body -> body.map(buffer -> Buffer.buffer(buffer.toString().toUpperCase()));
     * </code>
     *
     * @param bodyTransformer the transformer that will be applied.
     * @return a {@link Completable} that can be chained with the rest of the execution flow.
     */
    Completable onBody(final MaybeTransformer<Buffer, Buffer> bodyTransformer);
}
