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

/**
 * {@link io.gravitee.gateway.reactive.api.context.ExecutionContext} attribute keys
 * for the MCP credential contract between the reactor's credential resolver and
 * the endpoint's frame writer.
 *
 * <p>The reactor writes these attributes after credential pre-resolution; endpoints
 * read them to inject credentials into upstream requests or serialize JSON-RPC error
 * frames (e.g. {@code -32042} for elicitation).
 *
 * <h3>TYPE values</h3>
 * <ul>
 *   <li>{@code bearer} — static or vault-resolved Bearer token</li>
 *   <li>{@code api-key} — static API key injected via a configurable header</li>
 *   <li>{@code passthrough} — forward the inbound credential as-is</li>
 *   <li>{@code elicitation-oauth} — OAuth elicitation flow (client-driven)</li>
 *   <li>{@code none} — no upstream authentication</li>
 * </ul>
 *
 * @author Antoine CORDIER (antoine.cordier at graviteesource.com)
 * @author GraviteeSource Team
 */
public final class McpCredentialAttributes {

    /** Whether credential resolution completed successfully ({@code true}/{@code false}). */
    public static final String RESOLVED = "mcp.upstream.credentials.resolved";

    /** The resolved credential value (token or API key). */
    public static final String TOKEN = "mcp.upstream.credentials.token";

    /** The credential type — one of: {@code bearer}, {@code api-key}, {@code passthrough}, {@code elicitation-oauth}, {@code none}. */
    public static final String TYPE = "mcp.upstream.credentials.type";

    /** The HTTP header name to inject the credential into (e.g. {@code Authorization}, {@code X-Api-Key}). */
    public static final String HEADER_NAME = "mcp.upstream.credentials.header-name";

    /** The URL the client should navigate to for OAuth elicitation. */
    public static final String ELICITATION_URL = "mcp.upstream.elicitation.url";

    /** Opaque identifier for the in-flight elicitation request, used to correlate callback completion. */
    public static final String ELICITATION_ID = "mcp.upstream.elicitation.id";

    private McpCredentialAttributes() {}
}
