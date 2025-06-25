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
package io.gravitee.gateway.reactive.api.context.http;

import io.gravitee.gateway.reactive.api.context.base.BaseExecutionContext;
import io.gravitee.reporter.api.v4.metric.Metrics;

/**
 * Base interface any http-based execution context interface can inherit from.
 *
 *  @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 *  @author GraviteeSource Team
 */
public interface HttpBaseExecutionContext extends BaseExecutionContext {
    /**
     * Get the metrics associated with the context.
     *
     * @return a {@link Metrics} object.
     */
    Metrics metrics();

    /**
     * Get the current request stuck to this execution context.
     *
     * @return the request attached to this execution context.
     */
    HttpBaseRequest request();

    /**
     * Get the current response stuck to this execution context.
     *
     * @return the response attached to this execution context.
     */
    HttpBaseResponse response();
}
