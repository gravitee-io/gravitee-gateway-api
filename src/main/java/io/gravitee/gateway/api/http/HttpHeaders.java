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
package io.gravitee.gateway.api.http;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface HttpHeaders extends Iterable<Map.Entry<String, String>> {
    static HttpHeaders create() {
        return new DefaultHttpHeaders();
    }

    static HttpHeaders create(HttpHeaders headers) {
        return new DefaultHttpHeaders(headers);
    }

    /**
     * Ensure backward compatibility, should be removed in the future
     * @deprecated
     */
    default String getFirst(CharSequence name) {
        return get(name);
    }

    String get(CharSequence name);

    List<String> getAll(CharSequence name);

    /**
     * Ensure backward compatibility, should be removed in the future
     * @deprecated
     */
    default boolean containsKey(CharSequence name) {
        return containsKey(String.valueOf(name));
    }

    default boolean containsKey(String name) {
        return contains(name);
    }

    boolean contains(CharSequence name);

    default boolean contains(String name) {
        return contains((CharSequence) name);
    }

    Set<String> names();

    HttpHeaders add(CharSequence name, CharSequence value);

    HttpHeaders add(CharSequence name, Iterable<CharSequence> values);

    HttpHeaders set(CharSequence name, CharSequence value);

    HttpHeaders set(CharSequence name, Iterable<CharSequence> values);

    HttpHeaders remove(CharSequence name);

    void clear();

    int size();

    boolean isEmpty();

    default List<String> getOrDefault(CharSequence key, List<String> defaultValue) {
        List<String> v;
        return (((v = getAll(key)) != null) || containsKey(key.toString())) ? v : defaultValue;
    }

    default Map<String, String> toSingleValueMap() {
        LinkedHashMap<String, String> singleValueMap = new LinkedHashMap<>(size());
        for (Map.Entry<String, String> entry : this) {
            singleValueMap.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return singleValueMap;
    }

    default Map<String, List<String>> toListValuesMap() {
        return names().stream().collect(Collectors.toMap(s -> s, this::getAll, (o1, o2) -> o1, () -> new LinkedHashMap<>(size())));
    }

    default boolean containsAllKeys(Collection<String> names) {
        return names().containsAll(names.stream().map(String::toLowerCase).collect(Collectors.toList()));
    }

    /**
     * Indicates if this instance of HttpHeaders is deeply equal to another one.
     * Comparison is made on size equality, key-set equality, then on equality of collection of values for each key.
     * @param other the other HttpHeaders instance to compare with this one
     * @return true if instance are deeply equal, else returns false
     */
    default boolean deeplyEquals(HttpHeaders other) {
        if (this.size() != other.size() || !this.names().containsAll(other.names())) {
            return false;
        }

        for (String name : this.names()) {
            final List<String> otherValues = other.getAll(name);
            if (!otherValues.equals(this.getAll(name))) {
                return false;
            }
        }
        return true;
    }
}
