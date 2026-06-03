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
 * Transport-neutral descriptor of an endpoint's RFC 9728 Protected Resource
 * Metadata configuration. The reactor reads this to serve the gateway's
 * front-door {@code /.well-known/oauth-protected-resource} metadata without
 * depending on endpoint-internal configuration classes.
 *
 * @author Antoine CORDIER (antoine.cordier at graviteesource.com)
 * @author GraviteeSource Team
 */
public record ResourceMetadataDescriptor(
    List<String> authorizationServers,
    List<String> scopesSupported,
    List<String> bearerMethodsSupported,
    String resourceDocumentation
) {
    public boolean hasAuthorizationServers() {
        return authorizationServers != null && !authorizationServers.isEmpty();
    }
}
