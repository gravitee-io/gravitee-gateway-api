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
package io.gravitee.gateway.reactive.api.connector.endpoint.agent.exception;

/**
 * Thrown when the model calls a tool whose execution is delegated to the caller (an "external
 * execution tool", declared per-request rather than resolved from a configured connector). The
 * invoker catches this to pause the agent loop and return an {@code ExternalToolCallRequired}
 * event instead of executing anything server-side.
 *
 * <p>{@code memoryId} carries the information needed to later replace the placeholder result
 * message injected into memory with the real result once the caller reports it back.</p>
 */
public class ExternalToolExecutionRequiredException extends ToolException {

    private final String toolCallId;
    private final String toolName;
    private final String arguments;

    public ExternalToolExecutionRequiredException(String toolCallId, String toolName, String arguments) {
        super("Tool '" + toolName + "' is executed externally by the caller and cannot be invoked server-side.");
        this.toolCallId = toolCallId;
        this.toolName = toolName;
        this.arguments = arguments;
    }

    public String getToolCallId() {
        return toolCallId;
    }

    public String getToolName() {
        return toolName;
    }

    public String getArguments() {
        return arguments;
    }
}
