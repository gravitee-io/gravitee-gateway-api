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
package io.gravitee.gateway.jupiter.api.service.dlq;

import io.gravitee.gateway.jupiter.api.connector.entrypoint.EntrypointConnector;

/**
 * Factory allowing to instantiate a {@link DlqService} for the specified {@link EntrypointConnector}.
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface DlqServiceFactory {
    /**
     * Create and return a {@link DlqService} that can be used for the specified connector.
     * In any cases, it always returns a {@link DlqService} that can be safely used without checking for nullability..
     *
     * @param connector the entrypoint connector for which create a {@link DlqService}.
     * @return the created {@link DlqService}.
     */
    DlqService create(final EntrypointConnector connector);
}
