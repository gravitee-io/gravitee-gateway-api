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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

/**
 * Specialized {@link HttpEndpointConnector} for {@link ApiType#LLM_PROXY}
 */
public interface LLMProxyEndpointSyncConnector extends HttpEndpointConnector {
    @Override
    default ApiType supportedApi() {
        return apiType();
    }

    @Override
    default Set<ConnectorMode> supportedModes() {
        return modes();
    }

    /**
     * @deprecated kept for APIM 4.10.x version
     */
    @Deprecated(forRemoval = true, since = "5.0.0")
    default Collection<String> models() {
        return List.of();
    }

    /**
     * List of models supported by the provider as it will be displayed in the models list API.
     * @param providerName the provider name (endpoint group name)
     * @param separator the standard separator to use to split or concat the model name
     * @return list of supported models
     */
    default Collection<String> models(String providerName, String separator) {
        return models()
            .stream()
            .map(model -> providerName + separator + model)
            .toList();
    }

    /**
     * Creates a lookup function to check if a model is supported by this connector.
     * The returned function takes a model name (with provider prefix and separator) and returns
     * a SortedSet containing the SupportedModel if it matches one of the supported models,
     * or an empty set if the model is not supported.
     *
     * @param providerName the provider name (endpoint group name)
     * @param separator the standard separator to use to split or concat the model name
     * @return a function that maps a model name to a set of supported models sorted by priority (empty if not supported)
     */
    default Function<String, SortedSet<SupportedModel>> supportedModelsLookupFunction(String providerName, String separator) {
        var supportedModels = models(providerName, separator);
        return model -> supportedModels.contains(model) ? new TreeSet<>(List.of(new SupportedModel(model))) : new TreeSet<>();
    }

    static ApiType apiType() {
        return ApiType.LLM_PROXY;
    }

    static Set<ConnectorMode> modes() {
        return Set.of(ConnectorMode.REQUEST_RESPONSE);
    }

    record SupportedModel(String modelName, int priority) implements Comparable<SupportedModel> {
        public static final int EXACT_PRIORITY = 0;
        public static final int DEFAULT_PRIORITY = 5;

        public SupportedModel(String modelName) {
            this(modelName, DEFAULT_PRIORITY);
        }

        @Override
        public int compareTo(SupportedModel o) {
            int result = Integer.compare(priority, o.priority);
            return result != 0 ? result : Comparator.nullsLast(String::compareTo).compare(this.modelName, o.modelName);
        }
    }
}
