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
package io.gravitee.gateway.reactive.api.context.llm;

import java.util.Map;
import java.util.Objects;

/**
 * The last frame of a response flow that ended on a failure, rendered by the entrypoint in the provider's own
 * stream format (ex: for OpenAI, a chunk carrying the stop reason followed by {@code [DONE]}; for Anthropic, an
 * {@code error} event).
 * <p>
 * Emitted either by the connector, when the provider itself failed mid-generation, or by a policy ending the
 * flow through {@link LlmResponse#interruptDeltasWith(ErrorFrame)}.
 * <p>
 * Deliberately absent from this type:
 * <ul>
 *   <li><b>a status code</b>: by the time a frame is written the status and the headers are already sent. A
 *       failure raised before anything was flushed is an
 *       {@link io.gravitee.gateway.reactive.api.ExecutionFailure} passed to
 *       {@link LlmExecutionContext#interruptWith(io.gravitee.gateway.reactive.api.ExecutionFailure)}, not a
 *       frame.</li>
 *   <li><b>an error type</b> ({@code invalid_request_error}, {@code api_error}, ...): what a client switches on
 *       mid-stream is why generation stopped, which {@link #stopReason()} already says.</li>
 * </ul>
 *
 * @param stopReason why generation stopped, ex {@link StopReason#CONTENT_FILTER} for a guardrail, must not be {@code null}.
 * @param message the human-readable error message, or {@code null} to leave the entrypoint its default wording.
 * @param metadata error attributes vendor specific.
 *
 * @author GraviteeSource Team
 */
public record ErrorFrame(StopReason stopReason, String message, Map<String, Object> metadata) implements Frame {
    public ErrorFrame {
        Objects.requireNonNull(stopReason, "stopReason");
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public ErrorFrame(StopReason stopReason, String message) {
        this(stopReason, message, Map.of());
    }
}
