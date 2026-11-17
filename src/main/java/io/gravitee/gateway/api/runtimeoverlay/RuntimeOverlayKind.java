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
package io.gravitee.gateway.api.runtimeoverlay;

import java.util.Objects;

/**
 * Typed key pairing a kind name with its payload type, meant to be declared once as a constant.
 *
 * @param name the exact {@link io.gravitee.gateway.api.genericevent.GenericEvent#kind()}
 *     (<code>&lt;feature&gt;.&lt;type&gt;</code>), matched exactly, not as a prefix
 * @param payloadType the class the payload is deserialized to
 */
public record RuntimeOverlayKind<T>(String name, Class<T> payloadType) {
    public RuntimeOverlayKind {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(payloadType, "payloadType");
    }
}
