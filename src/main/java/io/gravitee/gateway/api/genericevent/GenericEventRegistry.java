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
 * Routes generic events to the features that subscribed to their kind.
 * <p>
 * The gateway exposes it as a bean of its Spring context, for plugins to inject.
 */
public interface GenericEventRegistry {
    /**
     * Replays the current events whose kind is <code>kindPrefix</code> or starts with its dot-separated segments, then
     * forwards every change.
     * <p>
     * The registry holds the subscriber until {@link Subscription#cancel()}: a plugin must cancel when it stops or is
     * undeployed, or its classloader leaks and later events call into closed code.
     */
    Subscription subscribe(String kindPrefix, GenericEventSubscriber subscriber);

    interface Subscription {
        void cancel();
    }
}
