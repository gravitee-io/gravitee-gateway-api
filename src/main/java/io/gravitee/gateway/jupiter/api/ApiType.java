/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.jupiter.api;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
@Getter
public enum ApiType {
    REQUEST_RESPONSE("request-response"),
    EVENT_NATIVE("event-native");

    private static final Map<String, ApiType> LABELS_MAP = Map.of(
        REQUEST_RESPONSE.label,
        REQUEST_RESPONSE,
        EVENT_NATIVE.label,
        EVENT_NATIVE
    );

    @JsonValue
    private final String label;

    public static ApiType fromLabel(final String label) {
        if (label != null) {
            return LABELS_MAP.get(label);
        }
        return null;
    }
}
