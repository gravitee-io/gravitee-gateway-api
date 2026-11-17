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

import java.util.List;

/**
 * Read-only view of the runtime overlays active on deployed APIs.
 * <p>
 * The gateway exposes it as a bean of its Spring context, for reactors and reactor plugins to inject.
 */
public interface RuntimeOverlayRegistry {
    /**
     * Returns the active runtime overlays of <code>kind</code> on the API <code>apiId</code>, their payload deserialized as
     * {@link RuntimeOverlayKind#payloadType()}.
     * <p>
     * Expired and unpublished overlays are excluded: expiry is checked against the current time on each call, not left to
     * the next sync commit. An overlay whose payload does not deserialize to the kind's payload type is left out, not
     * thrown, so that a malformed payload never breaks the API traffic.
     * <p>
     * Called on the request path, so it must not block.
     * <p>
     * The payloads may be shared across calls and threads: readers must not modify them, and should call this method on
     * each request rather than keep an overlay beyond it.
     *
     * @return an unmodifiable list, in no guaranteed order, empty when there is none, never <code>null</code>
     */
    <T> List<RuntimeOverlay<T>> findActive(String apiId, RuntimeOverlayKind<T> kind);
}
