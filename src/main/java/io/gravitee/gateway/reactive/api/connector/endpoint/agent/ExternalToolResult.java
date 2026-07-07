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
package io.gravitee.gateway.reactive.api.connector.endpoint.agent;

/**
 * One caller-supplied result for a previously-issued external tool call, fed back on the
 * follow-up request to resume the agent loop.
 *
 * @param toolCallId Correlates this result with the {@code toolCallId} carried by the
 *                    {@code ExternalToolCallRequired} event the caller received earlier.
 * @param output     The tool's result, as computed by the caller.
 * @param isError    Whether the caller's execution failed; surfaced to the model as a tool error.
 */
public record ExternalToolResult(String toolCallId, String output, boolean isError) {}
