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

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * One turn addressed to a remote agent: the message to answer, the named inputs it was given, and the conversation it
 * belongs to.
 *
 * <p>{@code conversationId} is the <b>remote's own</b> continuation handle, as it came back in
 * {@link AgentCallResult#getConversationId()} on the previous turn (the Responses API's {@code previous_response_id},
 * A2A's {@code contextId}): the caller keeps it and quotes it back, which is what makes the remote agent remember.
 * {@code null} starts a fresh conversation. A stateless protocol ignores it. Handing the handle round-trip this way
 * keeps every connector stateless: the caller already persists state, the connector never has to.</p>
 *
 * @author GraviteeSource Team
 */
@Builder
@Getter
public class AgentCall {

    private final String message;

    /** The named inputs the agent declared, as resolved by the caller. */
    @Builder.Default
    private final Map<String, Object> inputs = Map.of();

    private final String conversationId;
}
