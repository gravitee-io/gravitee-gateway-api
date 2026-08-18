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
package io.gravitee.gateway.reactive.api.policy.http;

import io.gravitee.gateway.reactive.api.context.DeploymentContext;
import io.reactivex.rxjava3.core.Completable;

/**
 * Optional interface for policies that need to pre-load external resources during API deployment.
 *
 * <p>Policies implementing this interface receive a warmup callback while the API reactor starts,
 * after resources have been initialized. This runs on the deployment thread, so blocking I/O
 * (HTTP calls, schema compilation) is permitted.</p>
 *
 * <p>If {@code warmup()} fails, the API deployment fails with a
 * {@code io.gravitee.gateway.policy.PolicyWarmupException} (fail-fast).</p>
 *
 * @author GraviteeSource Team
 */
public interface WarmablePolicy {
    /**
     * Called during API deployment to pre-load resources.
     *
     * @param deploymentContext provides access to the {@code ResourceManager} and {@code TemplateEngine}
     * @return a {@code Completable} that completes when warmup succeeds, or errors to fail deployment
     */
    Completable warmup(DeploymentContext deploymentContext);
}
