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
package io.gravitee.gateway.reactive.api.invoker;

import io.gravitee.gateway.reactive.api.context.ExecutionContext;
import io.gravitee.gateway.reactive.api.context.http.HttpExecutionContext;
import io.reactivex.rxjava3.core.Completable;

/**
 * Dedicated interface inspired from original {@link io.gravitee.gateway.api.Invoker} interface allowing to invoke an invoker in a reactive manner.
 * @deprecated see {@link HttpInvoker}
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
@Deprecated(forRemoval = true)
public interface Invoker extends HttpInvoker {
    Completable invoke(ExecutionContext ctx);

    @Override
    default Completable invoke(HttpExecutionContext ctx) {
        return invoke((ExecutionContext) ctx);
    }
}
