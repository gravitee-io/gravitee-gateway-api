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
 * Thrown when a tool requires explicit user approval before the agent may invoke it.
 * The invoker catches this to pause the agent loop and return an approval-required response.
 */
public class ToolApprovalRequiredException extends ToolException {

    private final String toolId;
    private final String toolName;

    public ToolApprovalRequiredException(String toolId, String toolName) {
        super("Tool '" + toolId + "' requires user approval before it can be invoked.");
        this.toolId = toolId;
        this.toolName = toolName;
    }

    public String getToolId() {
        return toolId;
    }

    public String getToolName() {
        return toolName;
    }
}
