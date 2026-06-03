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
 * OAuth tokens obtained from an upstream IdP on behalf of a user.
 * Stored in the reactor's vault cache; token expiry is handled by
 * the {@link io.gravitee.node.api.cache.Cache} TTL set at {@code put} time.
 *
 * @author Antoine CORDIER (antoine.cordier at graviteesource.com)
 * @author GraviteeSource Team
 */
public record UpstreamOAuthCredentials(String accessToken, String refreshToken, Instant expiresAt, List<String> scopes) {}
