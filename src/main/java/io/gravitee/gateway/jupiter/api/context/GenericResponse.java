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

import io.gravitee.gateway.api.http.HttpHeaders;

public interface GenericResponse {
    GenericResponse status(int statusCode);

    int status();

    /**
     * @return Reason-Phrase is intended to give a short textual description of the Status-Code.
     */
    String reason();

    GenericResponse reason(final String message);

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
}
