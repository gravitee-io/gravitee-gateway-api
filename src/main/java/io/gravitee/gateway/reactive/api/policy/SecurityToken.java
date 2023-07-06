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
package io.gravitee.gateway.reactive.api.policy;

import static io.gravitee.gateway.reactive.api.policy.SecurityToken.TokenType.API_KEY;
import static io.gravitee.gateway.reactive.api.policy.SecurityToken.TokenType.CLIENT_ID;
import static io.gravitee.gateway.reactive.api.policy.SecurityToken.TokenType.NONE;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class SecurityToken {

    public enum TokenType {
        CLIENT_ID,
        API_KEY,
        NONE,
    }

    private String tokenType;
    private String tokenValue;
    private boolean invalid;

    /**
     * Creates an empty security token.
     * Used by security plans that don't need any security token (for example, a keyless plan).
     *
     * @return empty security token
     */
    public static SecurityToken none() {
        return SecurityToken.builder().tokenType(NONE.name()).build();
    }

    /**
     * Creates an API key based security token.
     *
     * @return security token
     */
    public static SecurityToken forApiKey(String apiKey) {
        return SecurityToken.builder().tokenType(API_KEY.name()).tokenValue(apiKey).build();
    }

    /**
     * Creates a client id based security token.
     *
     * @return security token
     */
    public static SecurityToken forClientId(String clientId) {
        return SecurityToken.builder().tokenType(CLIENT_ID.name()).tokenValue(clientId).build();
    }

    /**
     * Creates an invalid security token with the given type used by security plans to express the fact that a token as been found by it is invalid.
     *
     * @return empty security token
     */
    public static SecurityToken invalid(final TokenType tokenType) {
        return SecurityToken.builder().tokenType(tokenType.name()).invalid(true).build();
    }
}
