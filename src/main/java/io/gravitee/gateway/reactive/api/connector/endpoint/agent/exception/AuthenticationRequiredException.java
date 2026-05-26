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
package io.gravitee.gateway.reactive.api.connector.endpoint.agent.exception;

import io.gravitee.gateway.reactive.api.connector.endpoint.agent.auth.AuthenticationConfiguration;

/**
 * Thrown when a tool endpoint requires OAuth 2.0 authorization that has not yet been granted.
 * Carries the OAuth config needed to initiate the authorization code flow.
 */
public class AuthenticationRequiredException extends ToolAuthenticationException {

    private final String endpointGroupId;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final boolean shared;

    public AuthenticationRequiredException(
        String message,
        String endpointGroupId,
        AuthenticationConfiguration authenticationConfiguration,
        boolean shared
    ) {
        super(message);
        this.endpointGroupId = endpointGroupId;
        this.authenticationConfiguration = authenticationConfiguration;
        this.shared = shared;
    }

    public AuthenticationRequiredException(
        String message,
        String endpointGroupId,
        AuthenticationConfiguration authenticationConfiguration,
        boolean shared,
        Throwable cause
    ) {
        super(message, cause);
        this.endpointGroupId = endpointGroupId;
        this.authenticationConfiguration = authenticationConfiguration;
        this.shared = shared;
    }

    public String getEndpointGroupId() {
        return endpointGroupId;
    }

    public AuthenticationConfiguration getAuthenticationConfiguration() {
        return authenticationConfiguration;
    }

    public boolean isShared() {
        return shared;
    }
}
