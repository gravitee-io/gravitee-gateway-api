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

import io.gravitee.common.http.HttpMethod;
import io.gravitee.common.http.HttpVersion;
import io.gravitee.common.util.MultiValueMap;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.reporter.api.http.Metrics;
import io.reactivex.*;
import javax.net.ssl.SSLSession;

public interface Request {
    String id();

    /**
     * Propagated transaction id between multiple requests.
     *
     * @return Transaction ID.
     */
    String transactionId();

    /**
     * @return The URL the client used to make the request.
     */
    String uri();

    /**
     * Allows to retrieve the request host.
     * This method should be preferred over retrieving the host from http headers.
     *
     * @return the host.
     */
    String host();

    /**
     * @return the part of this request's URL from the protocol name up to the query string in the first line
     * of the HTTP request.
     */
    String path();

    /**
     * @return the <code>path</code> without the <code>context-path</code>.
     */
    String pathInfo();

    /**
     * @return the portion of the request URI that indicates the context of the request.
     */
    String contextPath();

    /**
     * @return the query parameters in the request
     */
    MultiValueMap<String, String> parameters();

    /**
     * @return the path parameters in the request
     */
    MultiValueMap<String, String> pathParameters();

    /**
     * @return the headers in the request.
     */
    HttpHeaders headers();

    /**
     * @return the HTTP method for the request.
     */
    HttpMethod method();

    String scheme();

    /**
     * @return the HTTP version of the request
     */
    HttpVersion version();

    /**
     * The timestamp for when this request was received.
     * Specifically, this is the timestamp of creation of the request object.
     *
     * @return the instant timestamp for the request.
     */
    long timestamp();

    /**
     * Returns the Internet Protocol (IP) address of the client or last proxy that sent the request.
     *
     * @return a <code>String</code> containing the IP address of the client that sent the request.
     */
    String remoteAddress();

    /**
     * Returns the Internet Protocol (IP) address of the interface on which the request was received.
     *
     * @return a <code>String</code> containing the IP address on which the request was received.
     */
    String localAddress();

    /**
     * @return SSLSession associated to the request. Returns <code>null</code> if not an SSL connection.
     * @see javax.net.ssl.SSLSession
     */
    SSLSession sslSession();

    /**
     * Get the metrics associated to the request.
     *
     * @return a {@link Metrics} object.
     */
    Metrics metrics();

    /**
     * Indicates if that request is ended or not meaning that all the request headers and the request body have been fully read.
     *
     * @return <code>true</code> if the headers and body have been read, <code>false</code> else.
     */
    boolean ended();

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
     * <b>WARN:</b> replacing the request body will "drain" the previous request that was in place but occurs outside the reactive chain.
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
     * <b>WARN:</b> replacing the request body will "drain" the previous request that was in place.
     *
     * @param chunks the flowable of chunks representing the request body that will be pushed to the upstream.
     *
     * @see #body()
     */
    void chunks(final Flowable<Buffer> chunks);

    /**
     * Applies a transformation on each body chunks and completes when all the chunks have been processed.
     * Ex:
     * <code>
     *     request.onChunk(chunks -> chunks.map(buffer -> Buffer.buffer(buffer.toString().toUpperCase())));
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
}
