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
package io.gravitee.gateway.api.endpoint;

import io.gravitee.common.http.HttpHeaders;
import io.gravitee.gateway.api.Connector;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Endpoint {

    /**
     * Endpoint name.
     * @return Endpoint name.
     */
    String name();

    /**
     * Endpoint target.
     * @return Endpoint target.
     */
    String target();

    /**
     * Endpoint weight.
     *
     * @return
     */
    int weight();

    /**
     * Default HTTP headers set on the endpoint.
     *
     * @return {@link HttpHeaders}
     */
    HttpHeaders headers();

    /**
     * The {@link Connector} used to invoke the endpoint.
     * @return {@link Connector} used to invoke the endpoint.
     */
    Connector connector();

    /**
     * Does the endpoint can be reach ?
     *
     * @return
     */
    boolean available();
}
