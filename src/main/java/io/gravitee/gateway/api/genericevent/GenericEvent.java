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
package io.gravitee.gateway.api.genericevent;

import java.time.Instant;

/**
 * An event the control plane publishes to the gateways of an environment: configuration, state or a time-boxed action that
 * the feature owning its kind applies without redeploying an API.
 *
 * @param kind <code>&lt;feature&gt;.&lt;type&gt;</code>
 * @param organizationId the organization of the environment, for checking its license
 * @param expiresAt when the event stops applying; the gateway removes it at the first commit after that time, which only
 *     happens after a successful sync, so a subscriber that must stop on time also checks {@link #isExpired(Instant)}
 * @param payload the feature's own JSON, deserialized by the subscriber that owns the kind
 */
public record GenericEvent(
    String kind,
    String id,
    String environmentId,
    String organizationId,
    Instant expiresAt,
    Instant updatedAt,
    String payload
) {
    public boolean isExpired(Instant now) {
        return expiresAt != null && !expiresAt.isAfter(now);
    }
}
