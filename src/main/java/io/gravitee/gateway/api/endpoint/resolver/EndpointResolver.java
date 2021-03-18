/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.api.endpoint.resolver;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface EndpointResolver {

    /**
     * A reference to the best endpoint / endpoint group according to the target.
     *
     * Reference could be of three different types:
     * - A classical URL: http[s]://my_backend/api
     * - An endpoint or endpoint group reference: endpoint:/my_api
     * - Just a path: /my_api in that case the gateway will automatically select the default endpoint
     *
     * @param reference A reference to something (an endpoint)
     * @return Endpoint reference. Can be <code>null</code>
     */
    ProxyEndpoint resolve(String reference);
}
