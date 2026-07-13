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
package io.gravitee.gateway.reactive.api.connector.endpoint.agent.auth;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Which OAuth 2.0 grant the upstream expects, and therefore <b>who</b> the token represents.
 *
 * <ul>
 *   <li>{@link #AUTHORIZATION_CODE}: the end user. The connector cannot mint it, so it throws
 *       {@code AuthenticationRequiredException} and the reactor drives the browser round-trip, caching the result in
 *       the token vault. This is the historical behaviour, hence the default.</li>
 *   <li>{@link #CLIENT_CREDENTIALS}: the gateway itself (machine-to-machine). The connector mints the token from
 *       {@code clientId}/{@code clientSecret} against {@code tokenUrl} and caches it. No user is involved, so the end
 *       user's identity does <b>not</b> reach the upstream.</li>
 * </ul>
 *
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
@Getter
public enum OAuth2GrantType {
    AUTHORIZATION_CODE("authorization_code"),
    CLIENT_CREDENTIALS("client_credentials");

    @JsonValue
    private final String value;
}
