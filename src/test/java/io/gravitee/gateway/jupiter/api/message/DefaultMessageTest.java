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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.*;

import io.gravitee.gateway.api.buffer.Buffer;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
class DefaultMessageTest {

    protected static final String ATTR_TEST = "test";
    protected static final String ATTR_VALUE = "value";
    protected static final String INTERNAL_ATTR_TEST = "internal-test";
    protected static final String INTERNAL_ATTR_VALUE = "internal-value";
    protected static final Buffer BUFFER = Buffer.buffer();

    @Test
    void shouldReturnUnmodifiableMetadataMapWithBuilder() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key", INTERNAL_ATTR_VALUE);
        Message cut = DefaultMessage.builder().metadata(metadata).build();
        assertThrows(java.lang.UnsupportedOperationException.class, () -> cut.metadata().put(INTERNAL_ATTR_TEST, INTERNAL_ATTR_TEST));
    }

    @Test
    void shouldReturnUnmodifiableMetadataMapWithSetter() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key", INTERNAL_ATTR_VALUE);
        DefaultMessage cut = DefaultMessage.builder().build();
        cut.metadata(metadata);
        assertThrows(java.lang.UnsupportedOperationException.class, () -> cut.metadata().put(INTERNAL_ATTR_TEST, INTERNAL_ATTR_TEST));
    }

    @Test
    void shouldPutAttribute() {
        DefaultMessage cut = DefaultMessage.builder().build();
        assertNull(cut.attribute(ATTR_TEST));

        cut.attribute(ATTR_TEST, ATTR_VALUE);

        assertEquals(ATTR_VALUE, cut.attribute(ATTR_TEST));
        assertTrue(cut.attributeNames().contains(ATTR_TEST));
        assertEquals(ATTR_VALUE, cut.attributes().get(ATTR_TEST));
    }

    @Test
    void shouldRemoveAttribute() {
        DefaultMessage cut = DefaultMessage.builder().build();
        cut.attribute(ATTR_TEST, ATTR_VALUE);

        cut.removeAttribute(ATTR_TEST);
        assertFalse(cut.attributeNames().contains(ATTR_TEST));
    }

    @Test
    void shouldPutInternalAttribute() {
        DefaultMessage cut = DefaultMessage.builder().build();
        assertNull(cut.internalAttribute(INTERNAL_ATTR_TEST));

        cut.internalAttribute(INTERNAL_ATTR_TEST, INTERNAL_ATTR_VALUE);

        assertEquals(INTERNAL_ATTR_VALUE, cut.internalAttribute(INTERNAL_ATTR_TEST));
        assertTrue(cut.internalAttributeNames().contains(INTERNAL_ATTR_TEST));
        assertEquals(INTERNAL_ATTR_VALUE, cut.internalAttributes().get(INTERNAL_ATTR_TEST));
    }

    @Test
    void shouldRemoveInternalAttribute() {
        DefaultMessage cut = DefaultMessage.builder().build();
        cut.internalAttribute(INTERNAL_ATTR_TEST, INTERNAL_ATTR_VALUE);

        cut.removeInternalAttribute(INTERNAL_ATTR_TEST);
        assertFalse(cut.internalAttributeNames().contains(INTERNAL_ATTR_TEST));
    }

    @Test
    void shouldSetContent() {
        DefaultMessage cut = DefaultMessage.builder().build();

        assertNull(cut.content());

        cut.content(BUFFER);
        assertEquals(BUFFER, cut.content());
    }

    @Test
    void shouldSetContentString() {
        DefaultMessage cut = DefaultMessage.builder().build();

        assertNull(cut.content());

        cut.content("Hello");
        assertEquals("Hello", cut.content().toString());
    }

    @Test
    void shouldSetContentToNullIfNullString() {
        DefaultMessage cut = DefaultMessage.builder().build();

        assertNull(cut.content());

        cut.content((String) null);
        assertNull(cut.content());
    }

    @Test
    void shouldHaveCorrelationIdFromBuilder() {
        DefaultMessage cut = DefaultMessage.builder().build();
        assertNotNull(cut.correlationId());
    }

    @Test
    void shouldHaveCorrelationIdFromEmptyConstructor() {
        DefaultMessage cut = new DefaultMessage();
        assertNotNull(cut.correlationId());
    }

    @Test
    void shouldHaveCorrelationIdFromBufferConstructor() {
        DefaultMessage cut = new DefaultMessage(null);
        assertNotNull(cut.correlationId());
    }

    @Test
    void shouldAddSourceTimeStampToMetadata() {
        final long twoDaysAgo = Instant.now().minus(2, ChronoUnit.DAYS).toEpochMilli();
        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("an-entry", "its value");
        metadata.put("another-entry", "its other value");
        final DefaultMessage cut = DefaultMessage.builder().metadata(metadata).sourceTimestamp(twoDaysAgo).build();

        assertThat(cut.metadata()).contains(entry(DefaultMessage.SOURCE_TIMESTAMP, twoDaysAgo));
        assertThatThrownBy(() -> cut.metadata().put("forbidden", "cannot add because unmodifiable map"))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldUseDefaultTimestampAsSourceTimestamp() {
        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("an-entry", "its value");
        metadata.put("another-entry", "its other value");
        final long messageTimestamp = Instant.now().minus(3, ChronoUnit.DAYS).toEpochMilli();
        final DefaultMessage cut = DefaultMessage.builder().timestamp(messageTimestamp).metadata(metadata).build();

        assertThat(cut.metadata()).contains(entry(DefaultMessage.SOURCE_TIMESTAMP, cut.timestamp()));
        assertThatThrownBy(() -> cut.metadata().put("forbidden", "cannot add because unmodifiable map"))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
