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

import io.gravitee.gateway.reactive.api.policy.SecurityToken;
import java.util.Optional;

/**
 * This manages subscriptions.
 */
public interface SubscriptionService {
    /**
     * Get subscription by its API, client ID, and plan.
     *
     * @param api Searched API
     * @param clientId Searched client ID
     * @param plan Searched plan ID
     * @return Found subscription
     */
    Optional<Subscription> getByApiAndClientIdAndPlan(String api, String clientId, String plan);

    /**
     * Get subscription by its ID.
     *
     * @param subscriptionId Searched subscription ID
     * @return Found subscription
     */
    Optional<Subscription> getById(String subscriptionId);

    /**
     * Get subscription by its API, security token, and plan.
     *
     * @param api Searched API
     * @param securityToken Searched security token
     * @param plan Searched plan ID
     * @return Found subscription
     */
    Optional<Subscription> getByApiAndSecurityToken(String api, SecurityToken securityToken, String plan);

    /**
     * Register the given subscription.
     * Will load it if active, or unload it otherwise.
     *
     * @param subscription the subscription to save
     */
    void register(Subscription subscription);

    void unregister(Subscription subscription);

    /**
     * Unregister all subscriptions key from the given api id.
     *
     * @param apiId the api id attached to subscriptions to unregister
     */
    void unregisterByApiId(String apiId);
}
