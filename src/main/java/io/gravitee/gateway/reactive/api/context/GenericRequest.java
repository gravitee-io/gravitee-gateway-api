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

import io.gravitee.gateway.reactive.api.context.http.HttpBaseRequest;
import javax.net.ssl.SSLSession;

/**
 * @deprecated see {@link HttpBaseRequest}
 */
@Deprecated(forRemoval = true)
public interface GenericRequest extends HttpBaseRequest {
    /**
     * @return SSLSession associated to the request. Returns <code>null</code> if not an SSL connection.
     * @see SSLSession
     * @deprecated use {@link #tlsSession()} instead
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    SSLSession sslSession();
}
