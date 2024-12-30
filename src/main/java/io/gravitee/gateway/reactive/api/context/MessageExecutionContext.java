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
package io.gravitee.gateway.reactive.api.context;

import io.gravitee.gateway.reactive.api.context.http.HttpMessageExecutionContext;

/**
 * @deprecated see {@link io.gravitee.gateway.reactive.api.context.http.HttpMessageExecutionContext}
 */
@Deprecated(forRemoval = true)
public interface MessageExecutionContext extends GenericExecutionContext, HttpMessageExecutionContext {
    /**
     * Get the current request stuck to this execution context.
     *
     * @return the request attached to this execution context.
     */
    MessageRequest request();

    /**
     * Get the current response stuck to this execution context.
     *
     * @return the response attached to this execution context.
     */
    MessageResponse response();
}
