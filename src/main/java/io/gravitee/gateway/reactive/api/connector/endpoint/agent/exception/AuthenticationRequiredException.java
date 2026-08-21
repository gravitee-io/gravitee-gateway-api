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

    private final String upstreamId;
    private final AuthenticationConfiguration authenticationConfiguration;

    /**
     * The scope the granted token belongs to, or {@code null} to let the handler fall back to the run's.
     *
     * <p>Whoever raises this knows which agent needs the authorization; the handler that stores the token does not.
     * Without it the token is filed against the run, so a workflow leaf's token is written under one key and looked
     * for under another, and the user is asked to connect again on every turn.</p>
     *
     * <p>It also decides how far a grant reaches. Filed against the leaf, two sub-agents naming the same upstream hold
     * separate grants — which is what keeps a leaf that asked for read access from inheriting one that asked for
     * write. Sharing is a thing to opt into, not a default to discover.</p>
     */
    private final Object memoryId;

    public AuthenticationRequiredException(String message, String upstreamId, AuthenticationConfiguration authenticationConfiguration) {
        this(message, upstreamId, authenticationConfiguration, null, null);
    }

    public AuthenticationRequiredException(
        String message,
        String upstreamId,
        AuthenticationConfiguration authenticationConfiguration,
        Throwable cause
    ) {
        this(message, upstreamId, authenticationConfiguration, cause, null);
    }

    public AuthenticationRequiredException(
        String message,
        String upstreamId,
        AuthenticationConfiguration authenticationConfiguration,
        Throwable cause,
        Object memoryId
    ) {
        super(message, cause);
        this.upstreamId = upstreamId;
        this.authenticationConfiguration = authenticationConfiguration;
        this.memoryId = memoryId;
    }

    public String getUpstreamId() {
        return upstreamId;
    }

    public AuthenticationConfiguration getAuthenticationConfiguration() {
        return authenticationConfiguration;
    }

    /** The scope to file the token under, or {@code null} when the raiser has no narrower one than the run. */
    public Object getMemoryId() {
        return memoryId;
    }
}
