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
package io.gravitee.gateway.api.http.client;

import io.gravitee.common.component.LifecycleComponent;
import io.gravitee.common.http.HttpMethod;
import io.gravitee.gateway.api.ClientRequest;
import io.gravitee.gateway.api.ClientResponse;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.handler.Handler;

/**
 * An HttpClient is an extension of the {@link LifecycleComponent} to provide lifecyle methods.
 * HTTP client initialization must be done in <code>start()</code> and must be closed from the <code>stop()</code>
 * method.
 *
 * @author David BRASSELY (brasseld at gmail.com)
 * @author GraviteeSource Team
 */
public interface HttpClient extends LifecycleComponent<HttpClient> {

    ClientRequest request(String host, int port, HttpMethod method, String requestURI, Request serverRequest, Handler<ClientResponse> responseHandler);
}
