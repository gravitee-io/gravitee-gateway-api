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
package io.gravitee.gateway.jupiter.api.invoker;

import io.gravitee.gateway.jupiter.api.context.RequestExecutionContext;
import io.gravitee.gateway.jupiter.api.endpoint.EndpointConnector;
import io.reactivex.Completable;

/**
 * Dedicated interface inspired from original {@link io.gravitee.gateway.api.Invoker} interface allowing to invoke an invoker in a reactive manner.
 *
 * <b>WARN</b>: the Invoker concept will certainly be replaced by {@link EndpointConnector}.
 * <b>Implementing a new Invoker may not be a good choice!</b>
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Invoker {
    String getId();

    Completable invoke(RequestExecutionContext ctx);
}