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
 * Thrown when an MCP backend server issues an {@code elicitation/create} request during a tool
 * call — i.e. the server needs additional user input before it can complete the tool invocation.
 *
 * <p>The agent invoker catches this to abort the current turn, store a {@code PendingElicitation}
 * keyed by {@code elicitationId}, and return a structured {@code elicitationRequired} response to
 * the consumer. The consumer collects the user's input and POSTs it to
 * {@code /.elicitation/respond/{elicitationId}}; the invoker then retries the agent with the
 * answer injected so the elicitation handler can respond to the server.</p>
 */
public class ElicitationRequiredException extends ToolException {

    private final String elicitationId;
    private final String message;
    private final String url;

    /**
     * The scope the answer belongs to, or {@code null} to let the handler fall back to the run's.
     *
     * <p>Whoever raises this knows which agent is being asked; the handler that files the answer does not. Without it
     * the answer is stored against the run while the tool that will read it looks under its own key, so the two never
     * meet and the server goes on asking. The same reasoning, and the same fix, as
     * {@link AuthenticationRequiredException#getMemoryId()}.</p>
     */
    private final Object memoryId;

    public ElicitationRequiredException(String elicitationId, String message, String url) {
        this(elicitationId, message, url, null);
    }

    public ElicitationRequiredException(String elicitationId, String message, String url, Object memoryId) {
        super("MCP server requested URL elicitation: " + message);
        this.elicitationId = elicitationId;
        this.message = message;
        this.url = url;
        this.memoryId = memoryId;
    }

    /** The scope to file the answer under, or {@code null} when the raiser has no narrower one than the run. */
    public Object getMemoryId() {
        return memoryId;
    }

    public String getElicitationId() {
        return elicitationId;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
}
