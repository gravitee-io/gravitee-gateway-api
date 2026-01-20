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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternalContextAttributes {

    public static final String ATTR_INTERNAL_REACTABLE_API = "reactableApi";
    /**
     * Attribute used to store a reference to the {@link io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnector}
     */
    public static final String ATTR_INTERNAL_ENTRYPOINT_CONNECTOR = "entrypointConnector";
    public static final String ATTR_INTERNAL_ENDPOINT_CONNECTOR_ID = "endpointConnector.id";
    public static final String ATTR_INTERNAL_INVOKER = "invoker";
    public static final String ATTR_INTERNAL_INVOKER_SKIP = "invoker.skip";
    public static final String ATTR_INTERNAL_EXECUTION_FAILURE = "executionFailure";
    public static final String ATTR_INTERNAL_EXECUTION_WARN = "executionWarn";
    public static final String ATTR_INTERNAL_EXECUTION_COMPONENT_SCOPE = "execution.component.scope";
    public static final String ATTR_INTERNAL_FLOW_STAGE = "flow.stage";
    public static final String ATTR_INTERNAL_SUBSCRIPTION = "subscription";
    public static final String ATTR_INTERNAL_SUBSCRIPTION_TYPE = "subscriptionType";
    public static final String ATTR_INTERNAL_SECURITY_TOKEN = "securityChain.securityToken";
    public static final String ATTR_INTERNAL_SECURITY_SKIP = "securityChain.skip";
    public static final String ATTR_INTERNAL_SECURITY_DIAGNOSTIC = "securityChain.diagnostic";
    public static final String ATTR_INTERNAL_ANALYTICS_CONTEXT = "analytics.context";
    public static final String ATTR_INTERNAL_TRACING_ENABLED = "analytics.tracing.enabled";
    public static final String ATTR_INTERNAL_TRACING_VERBOSE_ENABLED = "analytics.tracing.verbose.enabled";
    public static final String ATTR_INTERNAL_TRACING_ROOT_SPAN = "analytics.tracing.root.span";
    public static final String ATTR_INTERNAL_MESSAGE_EXECUTION_PHASE = "message.execution-phase";
    public static final String ATTR_INTERNAL_MESSAGE_THROWABLE = "message.throwable";
    public static final String ATTR_INTERNAL_MESSAGE_RECORDABLE = "message.recordable";
    public static final String ATTR_INTERNAL_MESSAGE_RECORDABLE_WITH_LOGGING = "message.recordable.withLogging";
    public static final String ATTR_INTERNAL_VALIDATE_SUBSCRIPTION = "api.validateSubscription";
    public static final String ATTR_INTERNAL_TRACING_ERROR = "message.tracing.error";
    public static final String ATTR_INTERNAL_TRACING_MESSAGE_SPAN = "message.tracing.span";
    public static final String ATTR_INTERNAL_API_TYPE = "api.type";

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
    /**
     * <i>Type: string</i> <br/>
     * Attribute key used to indicate whether a message has been sent to the Dead Letter Queue (DLQ).
     * This attribute is typically used in message processing scenarios to track messages
     * that could not be successfully processed and were redirected to a DLQ for further analysis or retry.
     */
    public static final String ATTR_INTERNAL_MESSAGE_SENT_TO_DLQ = "message.sent-to-dlq";

    /**
     * <b>Feature: metrics</b> <br/>
     * <i>Type: Collection&lt;AdditionalMetrics&gt;</i> <br/>
     * Attribute used to store additional metrics to be added to the metrics.
     */
    public static final String ATTR_INTERNAL_MESSAGE_ADDITIONAL_METRICS = "message.metrics.additional";

    /**
     * <i>Type: Pattern</i> <br/>
     * Attribute used to override the content type filtering for reporting.
     */
    public static final String ATTR_INTERNAL_OVERRIDE_LOGGABLE_CONTENT_TYPE_PATTERN = "override.loggable.content.type.pattern";

    /**
     * <i>Type: String</i> <br/>
     * Attribute used to store the server ID related to the current request.
     */
    public static final String ATTR_INTERNAL_SERVER_ID = "serverId";
}
