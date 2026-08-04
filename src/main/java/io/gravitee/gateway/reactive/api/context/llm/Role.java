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
 * The author of a {@link Turn}.
 * <p>
 * Not a closed enum: {@link #SYSTEM}, {@link #USER}, {@link #ASSISTANT} and {@link #TOOL} cover the roles
 * common to every provider, but {@link #of(String)} accepts any value so a role introduced by a provider
 * tomorrow (or a provider-specific one today) can still be carried through without discarding it.
 *
 * @author GraviteeSource Team
 */
public final class Role {

    public static final Role SYSTEM = new Role("system");
    public static final Role USER = new Role("user");
    public static final Role ASSISTANT = new Role("assistant");
    public static final Role TOOL = new Role("tool");

    private static final Map<String, Role> KNOWN = Map.of(
        SYSTEM.value,
        SYSTEM,
        USER.value,
        USER,
        ASSISTANT.value,
        ASSISTANT,
        TOOL.value,
        TOOL
    );

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    /**
     * Returns the {@link Role} matching the given value, reusing one of the well-known constants when possible,
     * or wrapping the raw value otherwise.
     *
     * @param value the raw, provider-reported role (ex: {@code "user"}, {@code "developer"}, {@code "thinking"}).
     * @return the corresponding {@link Role}, never {@code null}.
     */
    public static Role of(String value) {
        Objects.requireNonNull(value, "value");
        return KNOWN.getOrDefault(value.toLowerCase(Locale.ROOT), new Role(value));
    }

    /**
     * @return the raw role value, as reported by the provider.
     */
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Role other && value.equalsIgnoreCase(other.value);
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
