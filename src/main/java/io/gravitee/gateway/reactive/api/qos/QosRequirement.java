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
package io.gravitee.gateway.reactive.api.qos;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class QosRequirement {

    /**
     * This refers to the {@link Qos} configured on the {@link io.gravitee.gateway.reactive.api.connector.entrypoint.EntrypointConnector}.
     * By default, the qos is {@link Qos#AUTO}
     */
    @Builder.Default
    private Qos qos = Qos.AUTO;

    /**
     * Set of {@link QosCapability} required to support the related {@link QosRequirement#qos}
     */
    @Builder.Default
    private Set<QosCapability> capabilities = new HashSet<>();
}
