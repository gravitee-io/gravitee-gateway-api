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

import io.gravitee.gateway.reactive.api.ApiType;
import io.gravitee.gateway.reactive.api.ConnectorMode;
import io.gravitee.gateway.reactive.api.connector.endpoint.HttpEndpointConnector;
import io.reactivex.rxjava3.core.Completable;
import java.util.Set;

/**
 * Specialized {@link HttpEndpointConnector} for {@link ApiType#MCP_PROXY}.
 * Base contract for all MCP endpoint connectors (mcp-proxy, tools-http,
 * tools-mcp).
 *
 * <p>Provides typed capability methods that the reactor and entrypoint layers
 * can invoke without casting — e.g. post-OAuth capabilities refresh, upstream
 * auth support detection, capability declaration, and group-level routing identity.
 *
 * @author Antoine CORDIER (antoine.cordier at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface McpEndpointSyncConnector extends HttpEndpointConnector {
    static ApiType apiType() {
        return ApiType.MCP_PROXY;
    }

    static Set<ConnectorMode> modes() {
        return Set.of(ConnectorMode.REQUEST_RESPONSE);
    }

    @Override
    default ApiType supportedApi() {
        return apiType();
    }

    @Override
    default Set<ConnectorMode> supportedModes() {
        return modes();
    }

    /**
     * Signals the endpoint to synchronize capabilities (tools, resources, prompts)
     * from the upstream server. The reactor calls this after successful OAuth flow
     * completion to refresh indices that the entrypoint uses for facade aggregation.
     *
     * @return a {@link Completable} that completes when the sync finishes or is not needed
     */
    default Completable triggerCapabilitiesSync() {
        return Completable.complete();
    }

    /**
     * Signals the endpoint to invalidate any existing upstream session and
     * re-initialize with the provided bearer token. The reactor calls this after
     * a successful OAuth callback to establish an authenticated upstream session.
     *
     * <p>Default implementation delegates to {@link #triggerCapabilitiesSync()}.
     *
     * @param bearerToken the freshly obtained access token to use for upstream init
     * @return a {@link Completable} that completes when the re-initialization finishes
     */
    default Completable triggerCapabilitiesSyncWithToken(String bearerToken) {
        return triggerCapabilitiesSync();
    }

    /**
     * Whether this endpoint supports upstream authentication — vault-managed
     * credentials, elicitation OAuth, API key injection, or passthrough.
     * The reactor uses this to decide whether to run credential pre-resolution
     * for requests routed to this endpoint.
     */
    default boolean supportsUpstreamAuth() {
        return false;
    }

    /**
     * Returns the deployment-specific endpoint group identifier for this connector
     * instance. Used for routing (entrypoint → endpoint) and per-group credential
     * resolution in the reactor's vault layer.
     *
     * @return the group id, or {@code null} if the connector is not group-aware
     */
    default String endpointGroupId() {
        return null;
    }

    /**
     * Returns a transport-neutral descriptor of this endpoint's upstream auth
     * configuration. The reactor's credential resolver reads this to perform
     * pre-resolution (vault lookup, static credential injection, elicitation
     * setup) without depending on endpoint-internal configuration classes.
     *
     * @return the auth descriptor, or {@code null} if the endpoint has no auth config
     */
    default UpstreamAuthDescriptor upstreamAuthDescriptor() {
        return null;
    }

    /**
     * Returns a transport-neutral descriptor of the endpoint's RFC 9728
     * Protected Resource Metadata configuration. The reactor reads this
     * to serve the gateway's front-door {@code /.well-known/oauth-protected-resource}
     * metadata without depending on endpoint-internal configuration classes.
     *
     * @return the resource metadata descriptor, or {@code null} if the endpoint has no front-door metadata config
     */
    default ResourceMetadataDescriptor resourceMetadataDescriptor() {
        return null;
    }

    /**
     * Declares the MCP capabilities this endpoint supports.
     *
     * <p>Endpoints with static config ({@code tools-http}, {@code tools-mcp}) return a
     * fixed non-empty set — e.g. a tools-only HTTP endpoint returns {@code {TOOLS}}.
     * The passthrough {@code mcp-proxy} endpoint that derives capabilities from the
     * upstream returns an empty set meaning "upstream-determined" (resolved at
     * {@link #triggerCapabilitiesSync()}).
     *
     * <p>The studio entrypoint consumes these sets to build a capability-keyed index,
     * advertise the union in {@code initialize}, and route each method to a group
     * that declares the matching capability.
     *
     * @return the set of supported capabilities, or empty if upstream-determined
     */
    default Set<McpCapability> supportedCapabilities() {
        return Set.of();
    }
}
