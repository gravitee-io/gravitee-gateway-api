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
package io.gravitee.gateway.reactive.api.connector.endpoint.sync;

import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Tracks an in-flight OAuth authorization that has been initiated but not yet
 * completed. Keyed by the random {@code state} parameter sent to the IdP.
 *
 * <p>Self-contained: stores the OAuth configuration fields directly so that
 * neither the reactor nor the endpoint needs to share a configuration class
 * across classloader boundaries.
 *
 * <p>When the upstream uses PKCE, {@code codeVerifier} holds the verifier generated for
 * this authorization only: the reactor sends its {@code S256} challenge on the authorize
 * request and the verifier itself on the token request. It is {@code null} without PKCE.
 * A verifier that is not one of RFC 7636 (43 to 128 characters of {@code [A-Za-z0-9-._~]}) is refused,
 * so a blank one can never turn PKCE on. {@link #toString()} masks it and the client secret so neither
 * ends up in logs.
 *
 * @author Antoine CORDIER (antoine.cordier at graviteesource.com)
 * @author GraviteeSource Team
 */
public record PendingAuth(
    String subject,
    String endpointGroupId,
    String authorizeUrl,
    String tokenUrl,
    String clientId,
    String clientSecret,
    List<String> scopes,
    String audience,
    Instant createdAt,
    String elicitationId,
    String codeVerifier
) {
    private static final String MASKED = "****";
    private static final Pattern CODE_VERIFIER = Pattern.compile("[A-Za-z0-9\\-._~]{43,128}");

    public PendingAuth {
        if (codeVerifier != null && !CODE_VERIFIER.matcher(codeVerifier).matches()) {
            // The verifier itself is not in the message: it is a secret
            throw new IllegalArgumentException(
                "codeVerifier must be 43 to 128 characters of [A-Za-z0-9-._~] (RFC 7636), got " + codeVerifier.length() + " characters"
            );
        }
    }

    /**
     * A pending authorization without PKCE, kept for callers built before {@code codeVerifier} existed.
     */
    public PendingAuth(
        String subject,
        String endpointGroupId,
        String authorizeUrl,
        String tokenUrl,
        String clientId,
        String clientSecret,
        List<String> scopes,
        String audience,
        Instant createdAt,
        String elicitationId
    ) {
        this(subject, endpointGroupId, authorizeUrl, tokenUrl, clientId, clientSecret, scopes, audience, createdAt, elicitationId, null);
    }

    public PendingAuth(
        String subject,
        String endpointGroupId,
        String authorizeUrl,
        String tokenUrl,
        String clientId,
        String clientSecret,
        List<String> scopes,
        String audience,
        Instant createdAt
    ) {
        this(subject, endpointGroupId, authorizeUrl, tokenUrl, clientId, clientSecret, scopes, audience, createdAt, null, null);
    }

    /** Whether this authorization uses PKCE, that is whether it holds a code verifier. */
    public boolean usesPkce() {
        return codeVerifier != null;
    }

    @Override
    public String toString() {
        return (
            "PendingAuth[subject=" +
            subject +
            ", endpointGroupId=" +
            endpointGroupId +
            ", authorizeUrl=" +
            authorizeUrl +
            ", tokenUrl=" +
            tokenUrl +
            ", clientId=" +
            clientId +
            ", clientSecret=" +
            mask(clientSecret) +
            ", scopes=" +
            scopes +
            ", audience=" +
            audience +
            ", createdAt=" +
            createdAt +
            ", elicitationId=" +
            elicitationId +
            ", codeVerifier=" +
            mask(codeVerifier) +
            "]"
        );
    }

    private static String mask(String secret) {
        return secret == null ? null : MASKED;
    }
}
