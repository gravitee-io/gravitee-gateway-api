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

import java.util.List;

/**
 * Transport-neutral descriptor of an endpoint's upstream authentication configuration.
 * Exposed by {@link McpEndpointSyncConnector#upstreamAuthDescriptor()} so that the
 * reactor's credential resolver can perform pre-resolution without depending on
 * endpoint-specific configuration classes.
 *
 * @param type one of: {@code bearer}, {@code api-key}, {@code basic}, {@code passthrough}, {@code elicitation-oauth}, {@code none}
 * @param token static Bearer token (for {@code bearer} type)
 * @param apiKey static API key (for {@code api-key} type)
 * @param apiKeyHeader HTTP header name for the API key (defaults to {@code X-API-Key})
 * @param username HTTP Basic username (for {@code basic} type)
 * @param password HTTP Basic password (for {@code basic} type)
 * @param authorizeUrl OAuth authorization endpoint URL (for {@code elicitation-oauth})
 * @param tokenUrl OAuth token endpoint URL (for {@code elicitation-oauth})
 * @param clientId OAuth client ID (for {@code elicitation-oauth})
 * @param clientSecret OAuth client secret (for {@code elicitation-oauth})
 * @param scopes OAuth scopes (for {@code elicitation-oauth})
 * @param audience OAuth audience (for {@code elicitation-oauth})
 * @param pkce whether the authorization code grant uses PKCE with {@code S256} (for {@code elicitation-oauth});
 *             with PKCE the client may have no secret (a public client). This record does not check it:
 *             the reactor leaves {@code client_secret} out of the token request when it is blank
 *
 * <p>{@link #toString()} masks {@code token}, {@code apiKey}, {@code password} and {@code clientSecret}
 * so none of them ends up in logs.
 *
 * @author Antoine CORDIER (antoine.cordier at graviteesource.com)
 * @author GraviteeSource Team
 */
public record UpstreamAuthDescriptor(
    String type,
    String token,
    String apiKey,
    String apiKeyHeader,
    String username,
    String password,
    String authorizeUrl,
    String tokenUrl,
    String clientId,
    String clientSecret,
    List<String> scopes,
    String audience,
    boolean pkce
) {
    private static final String MASKED = "****";

    /**
     * A descriptor without PKCE, kept for endpoints built before {@code pkce} existed.
     */
    public UpstreamAuthDescriptor(
        String type,
        String token,
        String apiKey,
        String apiKeyHeader,
        String username,
        String password,
        String authorizeUrl,
        String tokenUrl,
        String clientId,
        String clientSecret,
        List<String> scopes,
        String audience
    ) {
        this(
            type,
            token,
            apiKey,
            apiKeyHeader,
            username,
            password,
            authorizeUrl,
            tokenUrl,
            clientId,
            clientSecret,
            scopes,
            audience,
            false
        );
    }

    public boolean isPassthrough() {
        return "passthrough".equals(type);
    }

    public boolean isBearer() {
        return "bearer".equals(type);
    }

    public boolean isApiKey() {
        return "api-key".equals(type);
    }

    public boolean isBasic() {
        return "basic".equals(type);
    }

    public boolean isElicitationOAuth() {
        return "elicitation-oauth".equals(type);
    }

    /**
     * Whether the elicitation authorization code grant must use PKCE. PKCE applies to
     * {@code elicitation-oauth} only, so this is {@code false} for any other type.
     */
    public boolean usesPkce() {
        return isElicitationOAuth() && pkce;
    }

    public String effectiveApiKeyHeader() {
        return apiKeyHeader != null && !apiKeyHeader.isBlank() ? apiKeyHeader : "X-API-Key";
    }

    @Override
    public String toString() {
        return (
            "UpstreamAuthDescriptor[type=" +
            type +
            ", token=" +
            mask(token) +
            ", apiKey=" +
            mask(apiKey) +
            ", apiKeyHeader=" +
            apiKeyHeader +
            ", username=" +
            username +
            ", password=" +
            mask(password) +
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
            ", pkce=" +
            pkce +
            "]"
        );
    }

    private static String mask(String secret) {
        return secret == null ? null : MASKED;
    }
}
