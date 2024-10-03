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
package io.gravitee.gateway.reactive.api.context.http;

import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.reactive.api.context.base.BaseResponse;

/**
 * Base interface for all http-based responses.
 * Defines the common information that can be accessed for any http-based responses.
 *
 * @see HttpPlainResponse
 * @see HttpMessageResponse
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface HttpBaseResponse extends BaseResponse {
    /**
     * Set the status code of the http response.
     *
     * @param statusCode the status code to set.
     *
     * @return a self-reference.
     */
    HttpBaseResponse status(int statusCode);

    /**
     * Return the current http status.
     *
     * @return the current status code.
     */
    int status();

    /**
     * @return Reason-Phrase is intended to give a short textual description of the Status-Code.
     */
    String reason();

    /**
     * Set the reason message.
     *
     * @param message the message to set as a reason.
     *
     * @return a self-reference.
     */
    HttpBaseResponse reason(final String message);

    /**
     * @return the headers in the response.
     */
    HttpHeaders headers();

    /**
     * @return the trailers in the response (http/2).
     */
    HttpHeaders trailers();

    /**
     * Indicates if the response is ended or not.
     * Ended response means the response has been fully push to the client, including response body.
     *
     * @return <code>true</code> if the response has been fully pushed to the client, <code>false</code> else.
     */
    boolean ended();

    /**
     * Indicates if response has status in 100-199 range.
     * @return true if status is 1xx
     */
    default boolean isStatus1xx() {
        return hasHundredsDigit(status(), 1);
    }

    /**
     * Indicates if response has status in 200-299 range.
     * @return true if status is 2xx
     */
    default boolean isStatus2xx() {
        return hasHundredsDigit(status(), 2);
    }

    /**
     * Indicates if response has status in 300-399 range.
     * @return true if status is 3xx
     */
    default boolean isStatus3xx() {
        return hasHundredsDigit(status(), 3);
    }

    /**
     * Indicates if response has status in 400-499 range.
     * @return true if status is 4xx
     */
    default boolean isStatus4xx() {
        return hasHundredsDigit(status(), 4);
    }

    /**
     * Indicates if response has status in 500-599 range.
     * @return true if status is 5xx
     */
    default boolean isStatus5xx() {
        return hasHundredsDigit(status(), 5);
    }

    private boolean hasHundredsDigit(int numberToTest, int hundredsDigit) {
        return numberToTest / 100 == hundredsDigit;
    }
}
