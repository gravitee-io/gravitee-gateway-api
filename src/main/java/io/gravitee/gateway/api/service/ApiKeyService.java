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
package io.gravitee.gateway.api.service;

import java.util.Optional;

/**
 * This manages api keys.
 */
public interface ApiKeyService {
    /**
     * Register the given api key.
     * Will load it if active, or unload it otherwise.
     *
     * @param apiKey the api key to register
     */
    void register(final ApiKey apiKey);

    void unregister(ApiKey apiKey);

    /**
     * Unregister all api key from the given api id.
     *
     * @param apiId the api id attached to apikeys to unregister
     */
    void unregisterByApiId(String apiId);

    /**
     * Get api key by its api and key.
     *
     * @param api Searched api
     * @param key Searched key
     * @return Found ApiKey
     */
    Optional<ApiKey> getByApiAndKey(String api, String key);
}
