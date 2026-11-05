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
package io.gravitee.gateway.api.genericevent;

/**
 * A feature's view of its generic events: it keeps its own state, built from {@link GenericEvent#payload()}.
 * <p>
 * Callbacks run one at a time on the sync threads, before APIs are deployed: they must return quickly and hand slow work
 * off. Request threads read the state concurrently, so it must be safely published. Each change is delivered once: a
 * callback that throws is not called again for it.
 */
public interface GenericEventSubscriber {
    void onUpsert(GenericEvent event);

    /**
     * Called when the event is unpublished or has expired, which {@link GenericEvent#isExpired(java.time.Instant)} tells
     * apart. Removing an event the subscriber never received must be harmless.
     */
    void onRemove(GenericEvent event);

    /**
     * Called once the changes of a sync cycle are delivered, so the state can be swapped in whole.
     */
    default void onCommit() {}
}
