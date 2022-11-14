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

    /**
     * Attribute used to store a reference to the {@link io.gravitee.gateway.jupiter.api.connector.entrypoint.EntrypointConnector}
     */
    public static final String ATTR_INTERNAL_ENTRYPOINT_CONNECTOR = "entrypointConnector";
    public static final String ATTR_INTERNAL_INVOKER = "invoker";
    public static final String ATTR_INTERNAL_INVOKER_SKIP = "invoker.skip";
    public static final String ATTR_INTERNAL_EXECUTION_FAILURE = "executionFailure";
    public static final String ATTR_INTERNAL_FLOW_STAGE = "flow.stage";
    public static final String ATTR_INTERNAL_SUBSCRIPTION = "subscription";
    public static final String ATTR_INTERNAL_SUBSCRIPTION_TYPE = "subscription_type";
    public static final String ATTR_INTERNAL_SECURITY_TOKEN = "securityChain.securityToken";
    public static final String ATTR_INTERNAL_SECURITY_SKIP = "securityChain.skip";

    /**
     * Adapted ExecutionContext for V3 compatibility.
     */
    public static final String ATTR_INTERNAL_ADAPTED_CONTEXT = "adaptedContext";

    public static final String ATTR_INTERNAL_LISTENER_TYPE = "listener.type";

    /**
     * <b>Feature: limit</b> <br/>
     * <i>Type: integer</i> <br/>
     * Attribute used to store the maximum number of messages to retrieve, asked by the client.
     */
    public static final String ATTR_INTERNAL_MESSAGES_LIMIT_COUNT = "messages.limit.count";

    /**
     * <b>Feature: limit</b> <br/>
     * <i>Type: long</i> <br/>
     * Attribute used to store the maximum duration of the consumption request, asked by the client.
     */
    public static final String ATTR_INTERNAL_MESSAGES_LIMIT_DURATION_MS = "messages.limit.durationMs";

    /**
     * <b>Feature: recovery</b> <br/>
     * <i>Type: string</i> <br/>
     * Attribute used to store the ID of the last message received by the client.
     */
    public static final String ATTR_INTERNAL_MESSAGES_RECOVERY_LAST_ID = "messages.recovery.lastId";
}
