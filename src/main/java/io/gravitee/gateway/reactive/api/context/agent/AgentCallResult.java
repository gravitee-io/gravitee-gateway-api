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
package io.gravitee.gateway.reactive.api.context.agent;

import lombok.Getter;

/**
 * What a remote agent answered: its text, and, for a stateful protocol, the handle to quote to continue the same
 * conversation on the next turn ({@code previous_response_id}, A2A {@code contextId}, …). {@code null} when the
 * protocol keeps no state.
 *
 * <p>A failed turn is not a result: unlike {@link ToolResult}, which carries an {@code error} flag because an LLM can
 * read a tool's failure and try something else, a remote agent's answer feeds a workflow's scope, where there is no
 * such reader. A connector that could not get an answer errors its {@code Maybe} instead.</p>
 *
 * @author GraviteeSource Team
 */
@Getter
public class AgentCallResult {

    private final String text;
    private final String conversationId;

    public AgentCallResult(String text, String conversationId) {
        this.text = text;
        this.conversationId = conversationId;
    }
}
