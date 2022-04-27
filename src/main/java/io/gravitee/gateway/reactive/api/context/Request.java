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

import io.gravitee.common.http.HttpMethod;
import io.gravitee.common.http.HttpVersion;
import io.gravitee.common.util.MultiValueMap;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.reporter.api.http.Metrics;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import javax.net.ssl.SSLSession;

public interface Request<T> {
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

    Metrics metrics();

    /**
     * Indicates if that request is ended or not meaning that all the request headers and the request body have been fully read.
     *
     * @return <code>true</code> if the headers and body have been read, <code>false</code> else.
     */
    boolean ended();

    /**
     * Gets the request content as a {@link Flowable} of T representing each chunk of data.
     * For example, for {@link io.gravitee.gateway.reactive.api.context.sync.SyncRequest}, chunks are {@link io.gravitee.gateway.api.buffer.Buffer}
     * and for {@link io.gravitee.gateway.reactive.api.context.async.AsyncRequest}, it will be {@link io.gravitee.gateway.reactive.api.context.async.Message}
     *
     * @return a {@link Flowable} representing the data manipulated by the request.
     */
    Flowable<T> content();

    /**
     * Replaces the request content with the given {@link Flowable}.
     * The implementation must guaranty the reactive chain will be preserved by composing with the previous request content to make sure it will be well consumed and replaced.
     *
     * @return a {@link Completable} that can be used to continue the reactive chain.
     */
    Completable content(Flowable<T> content);
}
