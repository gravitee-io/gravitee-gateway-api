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

import io.gravitee.gateway.reactive.api.ExecutionFailure;
import java.util.Map;
import lombok.EqualsAndHashCode;

/**
 * An {@link ExecutionFailure} carrying the vendor-specific attributes an llm error body may need.
 * <p>
 * Meant for the <b>http-level</b> termination of an llm call: a policy interrupting during
 * {@link io.gravitee.gateway.reactive.api.policy.llm.LlmPolicy#onRequest(LlmExecutionContext)}, or during
 * {@link io.gravitee.gateway.reactive.api.policy.llm.LlmPolicy#onResponse(LlmExecutionContext)} before anything
 * has been flushed downstream. Once the response is streaming, the status code is already sent and there is no
 * error body left to write: end the stream with an {@link ErrorFrame} instead.
 * <p>
 * It is passed to the inherited {@link LlmExecutionContext#interruptWith(ExecutionFailure)}, so the response
 * templates configured on the api apply exactly as they do for a plain http call: {@link #key()} selects the
 * template and {@link #parameters()} feeds it. What this type adds is {@link #metadata()}, the escape hatch for
 * the attributes a given provider's error body has and the others do not (ex: OpenAI's {@code param}).
 * <p>
 * Deliberately absent from this type:
 * <ul>
 *   <li><b>the error type</b> ({@code invalid_request_error}, {@code rate_limit_error}, {@code api_error}, ...):
 *       providers derive it from the status code, so the connector does too rather than asking every policy to
 *       repeat it. {@link #key()} stays the gravitee-side identifier, used for templating, not for the wire.</li>
 *   <li><b>the stop reason</b>: nothing was generated, so nothing stopped. It belongs to {@link ErrorFrame},
 *       which ends a generation that had already started.</li>
 * </ul>
 *
 * @author GraviteeSource Team
 */
@EqualsAndHashCode(callSuper = true)
public class LlmFailure extends ExecutionFailure {

    private Map<String, Object> metadata = Map.of();

    public LlmFailure() {}

    public LlmFailure(int statusCode) {
        super(statusCode);
    }

    /**
     * @return the vendor specific attributes to add to the error body, never {@code null}.
     */
    public Map<String, Object> metadata() {
        return metadata;
    }

    /**
     * @param metadata the vendor specific attributes to add to the error body, {@code null} being read as none.
     */
    public LlmFailure metadata(Map<String, Object> metadata) {
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        return this;
    }

    @Override
    public LlmFailure statusCode(int statusCode) {
        super.statusCode(statusCode);
        return this;
    }

    @Override
    public LlmFailure message(String message) {
        super.message(message);
        return this;
    }

    @Override
    public LlmFailure key(String key) {
        super.key(key);
        return this;
    }

    @Override
    public LlmFailure parameters(Map<String, Object> parameters) {
        super.parameters(parameters);
        return this;
    }

    @Override
    public LlmFailure contentType(String contentType) {
        super.contentType(contentType);
        return this;
    }

    @Override
    public LlmFailure cause(Throwable cause) {
        super.cause(cause);
        return this;
    }
}
