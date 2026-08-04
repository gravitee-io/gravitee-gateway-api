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

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Why the model stopped generating.
 * <p>
 * Not a closed enum, for the same reason as {@link Role}: the constants below cover what every provider
 * expresses one way or another, but {@link #of(String)} accepts any value so a reason introduced tomorrow (or a
 * provider-specific one today) is carried through instead of being discarded.
 * <p>
 * The constants are <b>normalized</b>, not raw provider values: the same situation is {@code length} for OpenAI,
 * {@code max_tokens} for Anthropic and {@code MAX_TOKENS} for Gemini, and a policy that reacts to a truncated
 * answer should not have to know which provider it is talking to. Mapping the provider vocabulary onto these
 * constants is the connector's job.
 *
 * @author GraviteeSource Team
 */
public final class StopReason {

    /** The model reached a natural end of turn, or one of the configured stop sequences. */
    public static final StopReason STOP = new StopReason("stop");
    /** Generation was truncated because the token limit was reached. */
    public static final StopReason LENGTH = new StopReason("length");
    /** The model stopped to let the harness run the tool calls it requested. */
    public static final StopReason TOOL_CALLS = new StopReason("tool_calls");
    /** Generation was stopped by a content filter, either the provider's or a policy's. */
    public static final StopReason CONTENT_FILTER = new StopReason("content_filter");
    /** Generation was stopped by a failure. */
    public static final StopReason ERROR = new StopReason("error");

    private static final Map<String, StopReason> KNOWN = Map.of(
        STOP.value,
        STOP,
        LENGTH.value,
        LENGTH,
        TOOL_CALLS.value,
        TOOL_CALLS,
        CONTENT_FILTER.value,
        CONTENT_FILTER,
        ERROR.value,
        ERROR
    );

    private final String value;

    private StopReason(String value) {
        this.value = value;
    }

    /**
     * Returns the {@link StopReason} matching the given value, reusing one of the well-known constants when
     * possible, or wrapping the raw value otherwise.
     *
     * @param value the reason, already normalized when it maps onto a well-known constant.
     * @return the corresponding {@link StopReason}, never {@code null}.
     */
    public static StopReason of(String value) {
        Objects.requireNonNull(value, "value");
        return KNOWN.getOrDefault(value.toLowerCase(Locale.ROOT), new StopReason(value));
    }

    /**
     * @return the raw reason value.
     */
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof StopReason other && value.equalsIgnoreCase(other.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
