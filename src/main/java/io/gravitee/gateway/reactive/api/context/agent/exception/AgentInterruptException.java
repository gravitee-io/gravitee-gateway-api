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
package io.gravitee.gateway.reactive.api.context.agent.exception;

/**
 * Base for a control-flow pause where an agent run suspends to await external input (e.g. a
 * human-in-the-loop workflow step) rather than failing. The runtime unwinds the current invocation,
 * surfaces the interrupt to the caller (a prompt + a way to submit the answer), persists any resumable
 * state, and continues once the answer is provided.
 *
 * <p>Unlike the tool-scoped pauses (authentication / approval / elicitation, which extend
 * {@code ToolException}), an interrupt is not tied to a tool call.</p>
 *
 * @author GraviteeSource Team
 */
public class AgentInterruptException extends RuntimeException {

    public AgentInterruptException(String message) {
        super(message);
    }
}
