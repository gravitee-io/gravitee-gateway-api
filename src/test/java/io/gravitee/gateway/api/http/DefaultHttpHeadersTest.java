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
package io.gravitee.gateway.api.http;

import static io.gravitee.gateway.api.http.HttpHeaderNames.ACCEPT_LANGUAGE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Yann TAVERNIER (yann.tavernier at graviteesource.com)
 * @author GraviteeSource Team
 */
class DefaultHttpHeadersTest {

    public static final String FIRST_HEADER = "First-Header";
    public static final String SECOND_HEADER = "Second-Header";
    public static final String FIRST_HEADER_VALUE_1 = "first-header-value-1";
    public static final String FIRST_HEADER_VALUE_2 = "first-header-value-2";
    public static final String SECOND_HEADER_VALUE = "second-header-value";
    private DefaultHttpHeaders cut;

    @BeforeEach
    void setUp() {
        cut = new DefaultHttpHeaders();
        cut.add(FIRST_HEADER, FIRST_HEADER_VALUE_1);
        cut.add(FIRST_HEADER, FIRST_HEADER_VALUE_2);
        cut.add(SECOND_HEADER, SECOND_HEADER_VALUE);
    }

    @Test
    @DisplayName("Should convert headers to single value map")
    void shouldConvertToSingleValueMap() {
        final Map<String, String> result = cut.toSingleValueMap();
        assertThat(result).containsEntry(FIRST_HEADER, FIRST_HEADER_VALUE_1).containsEntry(SECOND_HEADER, SECOND_HEADER_VALUE);

        // In case of multiple values for one header name, this conversion is only taking the first value
        assertThat(result.values()).doesNotContain(FIRST_HEADER_VALUE_2);
    }

    @Test
    @DisplayName("Should contain all keys")
    void shouldContainAllKeys() {
        assertThat(cut.containsAllKeys(List.of())).isTrue();
        assertThat(cut.containsAllKeys(List.of(FIRST_HEADER))).isTrue();
        assertThat(cut.containsAllKeys(List.of(FIRST_HEADER, SECOND_HEADER))).isTrue();

        assertThat(cut.containsAllKeys(List.of(FIRST_HEADER, SECOND_HEADER, ACCEPT_LANGUAGE))).isFalse();
        assertThat(cut.containsAllKeys(List.of(ACCEPT_LANGUAGE))).isFalse();
    }

    @Test
    @DisplayName("Should contain key")
    void shouldContainKey() {
        Object key = new Object();
        assertThat(cut.containsKey(key)).isFalse();

        key = FIRST_HEADER;
        assertThat(cut.containsKey(key)).isTrue();

        key = ACCEPT_LANGUAGE;
        assertThat(cut.containsKey(key)).isFalse();
    }

    @Test
    @DisplayName("Should contain value")
    void shouldContainValue() {
        Object value = new Object();
        assertThat(cut.containsValue(value)).isFalse();

        value = List.of(FIRST_HEADER_VALUE_1, FIRST_HEADER_VALUE_2);
        assertThat(cut.containsValue(value)).isTrue();

        value = List.of(SECOND_HEADER_VALUE);
        assertThat(cut.containsValue(value)).isTrue();

        value = List.of(ACCEPT_LANGUAGE);
        assertThat(cut.containsValue(value)).isFalse();
    }

    @Test
    @DisplayName("Should get values")
    void shouldGet() {
        Object key = new Object();
        assertThat(cut.get(key)).isNull();

        key = FIRST_HEADER;
        assertThat(cut.get(key)).hasSize(2);

        key = SECOND_HEADER;
        assertThat(cut.get(key)).hasSize(1);

        key = ACCEPT_LANGUAGE;
        assertThat(cut.get(key)).isNull();
    }

    @Test
    @DisplayName("Should put values")
    void shouldPut() {
        // Putting a new header
        final List<String> headerTestValues = List.of("test-value1", "test-value2");
        final List<String> testHeader = cut.put("test", headerTestValues);

        assertThat(testHeader).isNull();
        assertThat(cut.getAll("test")).isEqualTo(headerTestValues);
        assertThat(cut.size()).isEqualTo(3);

        // Putting a header already present in the map
        final List<String> firstHeaderNewValues = List.of("first-overridden-1", "first-overridden-2", "first-overridden-3");
        final List<String> firstHeaderOverridden = cut.put(FIRST_HEADER, firstHeaderNewValues);

        assertThat(firstHeaderOverridden).hasSize(2).containsExactlyElementsOf(List.of(FIRST_HEADER_VALUE_1, FIRST_HEADER_VALUE_2));
        assertThat(cut.getAll(FIRST_HEADER)).containsExactlyElementsOf(firstHeaderNewValues);
    }

    @Test
    @DisplayName("Should remove header")
    void shouldRemove() {
        final HttpHeaders headers = cut.remove(FIRST_HEADER);
        assertThat(headers.contains(FIRST_HEADER)).isFalse();
    }

    @Test
    @DisplayName("Should put all")
    void shouldPutAll() {
        final List<String> putAllHeaders = List.of("test-value1", "test-value2");
        final Map<String, List<String>> mapToAdd = Map.of("Put-All-Header", putAllHeaders, FIRST_HEADER, List.of("new-value"));

        cut.putAll(mapToAdd);

        assertThat(cut.getAll("Put-All-Header")).hasSize(2).containsExactlyElementsOf(putAllHeaders);

        // Existing headers in the map should be overridden by this operation
        assertThat(cut.getAll(FIRST_HEADER)).hasSize(1).containsExactly("new-value");
    }

    @Test
    @DisplayName("Should get keySet")
    void shouldGetKeySet() {
        assertThat(cut.keySet()).hasSize(2).containsExactly(FIRST_HEADER, SECOND_HEADER);
    }

    @Test
    @DisplayName("Should get values")
    void shouldGetValues() {
        assertThat(cut.values())
            .hasSize(2)
            .containsExactly(List.of(FIRST_HEADER_VALUE_1, FIRST_HEADER_VALUE_2), List.of(SECOND_HEADER_VALUE));
    }

    @Test
    @DisplayName("Should get entrySet")
    void shouldGetEntrySet() {
        assertThat(cut.entrySet())
            .hasSize(2)
            .containsExactly(
                Map.entry(FIRST_HEADER, List.of(FIRST_HEADER_VALUE_1, FIRST_HEADER_VALUE_2)),
                Map.entry(SECOND_HEADER, List.of(SECOND_HEADER_VALUE))
            );
    }

    @Test
    @DisplayName("Should get first")
    void shouldGetFirst() {
        assertThat(cut.getFirst(FIRST_HEADER)).isEqualTo(FIRST_HEADER_VALUE_1);
    }

    @Test
    @DisplayName("Should not get first when absent")
    void shouldNotGetFirst() {
        assertThat(cut.getFirst("Content-Type")).isNull();
    }

    @Test
    @DisplayName("Should add value for an existing key")
    void shouldAddValue() {
        cut.add(FIRST_HEADER, "new-value");
        assertThat(cut.getAll(FIRST_HEADER)).hasSize(3).containsExactly(FIRST_HEADER_VALUE_1, FIRST_HEADER_VALUE_2, "new-value");
    }

    @Test
    @DisplayName("Should add value for a non existing key")
    void shouldAddValueNonExistingKey() {
        cut.add("New-Header", "new-value");
        assertThat(cut.getAll("New-Header")).hasSize(1).containsExactly("new-value");
    }

    @Test
    @DisplayName("Should set value and override if present")
    void shouldSetValue() {
        cut.set(FIRST_HEADER, "new-value");
        cut.set("New-Header", "new-value");

        assertThat(cut.getAll(FIRST_HEADER)).hasSize(1).containsExactly("new-value");

        assertThat(cut.getAll("New-Header")).hasSize(1).containsExactly("new-value");
    }

    @Test
    @DisplayName("Should set all values and override if present")
    void shouldSetAll() {
        final Map<String, String> mapToAdd = Map.of("Put-All-Header", "test-value1", FIRST_HEADER, "new-value");

        cut.setAll(mapToAdd);

        assertThat(cut.getAll("Put-All-Header")).hasSize(1).containsExactly("test-value1");

        // Existing headers in the map should be overridden by this operation
        assertThat(cut.getAll(FIRST_HEADER)).hasSize(1).containsExactly("new-value");
    }
}
