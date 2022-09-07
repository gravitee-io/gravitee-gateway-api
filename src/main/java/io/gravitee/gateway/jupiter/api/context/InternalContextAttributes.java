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
package io.gravitee.gateway.jupiter.api.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternalContextAttributes {

    public static final String ATTR_INTERNAL_EXECUTION_FAILURE = "execution-failure";
    public static final String ATTR_INTERNAL_FLOW_STAGE = "flow.stage";
    /**
     * Adapted ExecutionContext for V3 compatibility.
     */
    public static final String ATTR_ADAPTED_CONTEXT = "contextAdapter";
    public static final String ATTR_INTERNAL_SUBSCRIPTION_TYPE = "subscription.type";
    public static final String ATTR_INTERNAL_SUBSCRIPTION = "subscription";

    public static final String ATTR_INTERNAL_LISTENER_TYPE = "listener.type";
}
