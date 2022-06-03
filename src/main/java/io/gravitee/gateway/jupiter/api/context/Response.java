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
import io.gravitee.gateway.api.http.HttpHeaders;
import io.reactivex.*;

public interface Response {
    Response status(int statusCode);

    int status();

    /**
     * @return Reason-Phrase is intended to give a short textual description of the Status-Code.
     */
    String reason();

    Response reason(final String message);

    /**
     * @return the headers in the response.
     */
    HttpHeaders headers();

    HttpHeaders trailers();

    /**
     * Indicates if the response is ended or not.
     * Ended response means the response has been fully push to the client, including response body.
     *
     * @return <code>true</code> if the response has been fully pushed to the client, <code>false</code> else.
     */
    boolean ended();

    /**
     * Get the current body response as a {@link Maybe}. If no body has not been set on the response yet, an empty {@link Maybe} will returned.
     *
     * By getting the body as a {@link Maybe}, the current body chunks will be merged together to reconstruct the entire body in provide it in the form of a single {@link Buffer}
     * This is useful when the entire body is required to apply some transformation or any manipulation.
     *
     * <b>WARN:</b> beware that the entire body content will be loaded in memory. You should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Maybe} observable containing the current entire body response or empty if response body as not been set yet.
     * @see #bodyOrEmpty()
     */
    Maybe<Buffer> body();

    /**
     * Same as {@link #body()} but returns a {@link Single} of {@link Buffer} instead.
     *
     * If no body response as been set yet, it returns a {@link Single} with an empty {@link Buffer}.
     * It is a convenient way that avoid checking if the body is set or not prior to manipulate it.
     *
     * @return a {@link Single} observable containing the current entire body response or empty an {@link Buffer) if response body as not been set yet.
     * @see #body()
     */
    Single<Buffer> bodyOrEmpty();

    /**
     * Set the current body response as a {@link Buffer}.
     * This is useful when you want to replace the current body response with a specific content that doesn't come from a reactive chain, ex:
     *
     * <code>
     *     response.body(Buffer.buffer("My custom content");
     * </code>
     *
     <b>WARN:</b> replacing the request body will "drain" the previous request that was in place but occurs outside the reactive chain.
     * You should consider using {@link #onBody(MaybeTransformer)} to change the body during the chain execution.
     *
     * @see #onBody(MaybeTransformer)
     * @see #chunks(Flowable)
     */
    void body(final Buffer buffer);

    /**
     * Applies a transformation on the complete body given as a single {@link Buffer}.
     *
     * Ex:
     * <code>
     *     response.onBody(body -> body.map(buffer ->Buffer.buffer(buffer.toString().toUpperCase())));
     * </code>
     *
     * <b>IMPORTANT: applying a transformation on the body content loads the whole body in memory.
     * It's up to the consumer to make sure it is safe to do that without consuming too much memory.</b>
     *
     * @param onBody the transformation that will be applied on the body.
     * @return a {@link Completable} that completes once the body transformation has occurred.
     */
    Completable onBody(final MaybeTransformer<Buffer, Buffer> onBody);

    /**
     * Set the current response body chunks from a {@link Flowable} of {@link Buffer}.
     * This is useful to directly pump the upstream chunks to the downstream without having to load all the chunks in memory.
     * <br/>
     * <b>WARN:</b> replacing the response body will "drain" the previous response that was in place.
     *
     * @param chunks the flowable of chunks representing the response to push back to the downstream.
     *
     * @see #body()
     */
    void chunks(final Flowable<Buffer> chunks);

    /**
     * Get the current response body chunks as {@link Flowable} of {@link Buffer}.
     * This is useful when you want to manipulate the entire body without having to load it in memory.
     *
     * <b>WARN:</b> you should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Flowable} containing the current body response chunks.
     */
    Flowable<Buffer> chunks();

    /**
     * Applies a transformation on each body chunks and completes when all the chunks have been processed.
     * Ex:
     * <code>
     *     response.onChunk(chunks -> chunks.map(buffer -> Buffer.buffer(buffer.toString().toUpperCase())));
     * </code>
     *
     * <b>IMPORTANT: applying a transformation on the body chunks loads the whole body in memory.
     * It's up to the consumer to make sure it is safe to do that without consuming too much memory.</b>
     *
     * <b>IMPORTANT: applying a transformation on chunks completes when all chunks have been transformed.
     * If used in a policy chain, it means that the next policy will start once all chunks have been transformed</b>
     *
     * @param onChunk the transformer that will be applied.
     * @return a {@link Completable} that completes once the body transformation has occurred on all the chunks.
     */
    Completable onChunk(final FlowableTransformer<Buffer, Buffer> onChunk);

    /**
     * End the response.
     *
     * @return an observable that can be easily chained.
     */
    Completable end();
}
