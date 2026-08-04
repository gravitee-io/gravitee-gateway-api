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
package io.gravitee.gateway.reactive.api.context.llm;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Vendor-agnostic view over an llm request's parameters.
 * <p>
 * Common parameters are exposed as typed accessors, normalized regardless of the provider's actual wire field
 * names. Anything not covered by a typed accessor remains reachable through {@link #vendorExtras()}.
 *
 * @author GraviteeSource Team
 */
public interface LlmParameters {
    Optional<String> model();

    LlmParameters model(String model);

    /**
     * @return the tool/function definitions made available to the model, in their provider-specific shape.
     */
    List<JsonNode> tools();

    LlmParameters tools(List<JsonNode> tools);

    /**
     * Escape hatch for provider-specific parameters that don't have a normalized accessor.
     *
     * @return a read-write map of the raw, non-normalized parameters.
     */
    Map<String, Object> vendorExtras();
}
