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
package io.gravitee.gateway.reactive.api.context.kafka;

import java.time.Duration;

public interface NetworkController {
    /**
     * Pauses the acceptance of incoming data for the specified duration.
     * The flow is resumed once the duration is over or an explicit call to {@link #resume()} is performed.
     *
     * @param duration the duration to apply before calling resume.
     */
    void pause(Duration duration);

    /**
     * Immediately resume the flow of incoming data.
     */
    void resume();
}
