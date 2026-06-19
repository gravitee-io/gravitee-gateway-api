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
package io.gravitee.gateway.reactive.api.context;

/**
 * Maps a user-facing resource name to a catalog entity ID.
 *
 * <p>Each reactor registers its own instance at startup, populated from the
 * catalog. A single reactor may handle multiple entity kinds (e.g. MCP has
 * tools, prompts, and resources).
 *
 * <p>Registered as a component per reactor so that policies and EL expressions
 * can resolve stable entity IDs for FGA evaluation.
 */
public interface EntityResolver {
    /**
     * Register a mapping from a protocol-level name to a catalog entity ID.
     *
     * @param kind     the entity kind (e.g. "tool", "prompt", "resource", "model", "agent")
     * @param name     the protocol-level name as seen by the client
     * @param entityId the catalog entity ID to associate
     */
    void register(String kind, String name, String entityId);

    /**
     * Resolve the catalog entity ID for a given entity kind and name.
     *
     * @param kind the entity kind (e.g. "tool", "prompt", "resource", "model", "agent")
     * @param name the protocol-level name as seen by the client
     * @return the catalog entity ID, or {@code name} unchanged if no mapping exists
     */
    String resolve(String kind, String name);
}
