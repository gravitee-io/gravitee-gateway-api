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
package io.gravitee.gateway.jupiter.api.message;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
class DefaultMessageTest {

    @Test
    void shouldReturnUnmodifiableMetadataMapWithBuilder() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key", "value");
        Message defaultMessage = DefaultMessage.builder().metadata(metadata).build();
        assertThrows(java.lang.UnsupportedOperationException.class, () -> defaultMessage.metadata().put("test", "test"));
    }

    @Test
    void shouldReturnUnmodifiableMetadataMapWithSetter() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key", "value");
        DefaultMessage defaultMessage = DefaultMessage.builder().build();
        defaultMessage.metadata(metadata);
        assertThrows(java.lang.UnsupportedOperationException.class, () -> defaultMessage.metadata().put("test", "test"));
    }
}
