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
    String audience
) {
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

    public String effectiveApiKeyHeader() {
        return apiKeyHeader != null && !apiKeyHeader.isBlank() ? apiKeyHeader : "X-API-Key";
    }
}
