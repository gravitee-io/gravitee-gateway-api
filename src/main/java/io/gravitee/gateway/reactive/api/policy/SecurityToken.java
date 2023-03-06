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

import static io.gravitee.gateway.reactive.api.policy.SecurityToken.TokenType.*;

public class SecurityToken {

    public enum TokenType {
        CLIENT_ID,
        API_KEY,
        NONE,
    }

    private String tokenType;

    private String tokenValue;

    public SecurityToken(String tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }

    public SecurityToken(TokenType tokenType, String tokenValue) {
        this(tokenType.name(), tokenValue);
    }

    /**
     * Creates an empty security token.
     * Used by security plans that don't need any security token (for example, a keyless plan).
     *
     * @return empty security token
     */
    public static SecurityToken none() {
        return new SecurityToken(NONE, null);
    }

    /**
     * Creates an API key based security token.
     *
     * @return security token
     */
    public static SecurityToken forApiKey(String apiKey) {
        return new SecurityToken(API_KEY, apiKey);
    }

    /**
     * Creates a client id based security token.
     *
     * @return security token
     */
    public static SecurityToken forClientId(String clientId) {
        return new SecurityToken(CLIENT_ID, clientId);
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
}
