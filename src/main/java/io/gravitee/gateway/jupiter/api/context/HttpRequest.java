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
import io.gravitee.gateway.jupiter.api.ws.WebSocket;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeTransformer;
import io.reactivex.Single;

public interface HttpRequest extends GenericRequest {
    /**
     * Indicates if that request is a websocket request.
     * A websocket request must respond to the following criteria:
     * <ul>
     *     <li>Has a <code>Connection: Upgrade</code> header</li>
     *     <li>Has a <code>Upgrade: websocket</code> header</li>
     *     <li>Is a <code>GET</code> request</li>
     *     <li>Is an HTTP 1.0 or HTTP 1.1 request</li>
     * </ul>
     *
     * @return <code>true</code> is the request is a websocket, <code>false</code> otherwise.
     */
    boolean isWebSocket();

    /**
     * Returns the underlying websocket associated to this request or <code>null</code> if the current request is not a websocket request.
     *
     * @return the underlying websocket associated to this request.
     * @see #isWebSocket()
     */
    WebSocket webSocket();

    /**
     * Get the current body request as a {@link Maybe}.
     *
     * By getting the body as a {@link Maybe}, the current body chunks will be merged together to reconstruct the entire body in provide it in the form of a single {@link Buffer}
     * This is useful when the entire body is required to apply some transformation or any manipulation.
     *
     * <b>WARN:</b> beware that the entire body content will be loaded in memory. You should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Maybe} observable containing the current entire body request or empty if request body as not been set yet.
     * @see #bodyOrEmpty()
     */
    Maybe<Buffer> body();

    /**
     * Same as {@link #body()} but returns a {@link Single} of {@link Buffer} instead.
     *
     * If no body request as been set yet, it returns a {@link Single} with an empty {@link Buffer}.
     * It is a convenient way that avoid checking if the body is set or not prior to manipulate it.
     *
     * @return a {@link Single} observable containing the current entire body request or empty an {@link Buffer) if request body as not been set yet.
     * @see #body()
     */
    Single<Buffer> bodyOrEmpty();

    /**
     * Set the current body request as a {@link Buffer}.
     * This is useful when you want to replace the current body request with a specific content that doesn't come from a reactive chain, ex:
     *
     * <code>
     *     request.body(Buffer.buffer("My custom content");
     * </code>
     *
     * <b>WARN:</b>
     * <ul>
     *  <li>replacing the request body will <b>NOT</b> "drain" the previous request that was in place.</li>
     *  <li>You <b>MUST</b> ensure to consume the previous body by yourself when using it.</li>
     *  <li>You <b>SHOULD</b> consider using {@link #onBody(MaybeTransformer)} to change the body during the chain execution.</li>
     * </ul>
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
     *     request.onBody(body -> body.map(buffer ->Buffer.buffer(buffer.toString().toUpperCase())));
     * </code>
     *
     * <b>IMPORTANT: applying a transformation on the body content loads the whole body in memory.
     * It's up to the consumer to make sure it is safe to do that without consuming too much memory.</b>
     *
     * @param onBody the transformation that will be applied on the body.
     * @return a {@link Completable} that completes once the body transformation has occurred.
     */
    Completable onBody(MaybeTransformer<Buffer, Buffer> onBody);

    /**
     * Get the current request body chunks as {@link Flowable} of {@link Buffer}.
     * This is useful when you want to manipulate the entire body without having to load it in memory.
     *
     * <b>WARN:</b> you should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     *
     * @return a {@link Flowable} containing the current body request chunks.
     */
    Flowable<Buffer> chunks();

    /**
     * Set the current request body chunks from a {@link Flowable} of {@link Buffer}.
     * This is useful to directly pump the upstream chunks to the downstream without having to load all the chunks in memory.
     *
     * <b>WARN:</b>
     * <ul>
     *  <li>replacing the request chunks will <b>NOT</b> "drain" the previous request that was in place.</li>
     *  <li>You <b>MUST</b> ensure to consume the previous chunks by yourself when using it.</li>
     *  <li>You <b>SHOULD</b> consider using {@link #onChunks(FlowableTransformer)} to change the chunks during the chain execution.</li>
     * </ul>
     *
     * @param chunks the {@link Flowable} of chunks representing the request body that will be pushed to the upstream.
     *
     * @see #body()
     */
    void chunks(final Flowable<Buffer> chunks);

    /**
     * Applies a transformation on each body chunks and completes when all the chunks have been processed.
     * Ex:
     * <code>
     *     request.onChunks(chunks -> chunks.map(buffer -> Buffer.buffer(buffer.toString().toUpperCase())));
     * </code>
     *
     * <b>IMPORTANT: applying a transformation on the body chunks loads the whole body in memory.
     * It's up to the consumer to make sure it is safe to do that without consuming too much memory.</b>
     *
     * <b>IMPORTANT: applying a transformation on chunks completes when all chunks have been transformed.
     * If used in a policy chain, it means that the next policy will start once all chunks have been transformed</b>
     *
     * @param onChunks the transformer that will be applied.
     * @return a {@link Completable} that completes once the body transformation has occurred on all the chunks.
     */
    Completable onChunks(final FlowableTransformer<Buffer, Buffer> onChunks);
}
