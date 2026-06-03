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

/**
 * Tracks an in-flight OAuth authorization that has been initiated but not yet
 * completed. Keyed by the random {@code state} parameter sent to the IdP.
 *
 * <p>Self-contained: stores the OAuth configuration fields directly so that
 * neither the reactor nor the endpoint needs to share a configuration class
 * across classloader boundaries.
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
    String elicitationId
) {
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
        this(subject, endpointGroupId, authorizeUrl, tokenUrl, clientId, clientSecret, scopes, audience, createdAt, null);
    }
}
