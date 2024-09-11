/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import javax.net.ssl.SSLSession;

public interface GenericRequest {
    String id();

    /**
     * Propagated transaction id between multiple requests.
     *
     * @return Transaction ID.
     */
    String transactionId();

    /**
     * Propagated client identifier between multiple requests.
     *
     * @return Client Identifier.
     */
    String clientIdentifier();

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
     * Allows to retrieve the request original host.
     * Unlike the {@link #host()}, it remains the same and can't be altered during the request processing.
     *
     * @return the original reuqest host.
     */
    String originalHost();

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
     * @deprecated use {@link #tlsSession()} instead
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    SSLSession sslSession();

    /**
     * @return TlsSession associated to the request. Acts as a SSLSession, with additional mechanisms. It can for example extract a client certificate from a given header.
     * @see javax.net.ssl.SSLSession
     */
    TlsSession tlsSession();

    /**
     * Indicates if that request is ended or not meaning that all the request headers and the request body have been fully read.
     *
     * @return <code>true</code> if the headers and body have been read, <code>false</code> else.
     */
    boolean ended();
}
