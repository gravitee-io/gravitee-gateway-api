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

import io.gravitee.common.util.LinkedCaseInsensitiveMap;
import io.gravitee.common.util.MultiValueMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implements {@link MultiValueMap<String,String>} for backward compatibility.
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class DefaultHttpHeaders implements io.gravitee.gateway.api.http.HttpHeaders, MultiValueMap<String, String> {

    private final Map<String, List<String>> headers;

    /**@
     * Constructs a new, empty instance of the {@code HttpHeaders} object.
     */
    DefaultHttpHeaders() {
        this.headers = new LinkedCaseInsensitiveMap<>(8);
    }

    private DefaultHttpHeaders(int initialCapacity) {
        this.headers = new LinkedCaseInsensitiveMap<>(initialCapacity);
    }

    DefaultHttpHeaders(io.gravitee.gateway.api.http.HttpHeaders headers) {
        this(headers.size());
        headers.toListValuesMap().forEach((key, values) -> values.forEach(value -> add(key, value)));
    }

    @Override
    public int size() {
        return headers.size();
    }

    @Override
    public boolean isEmpty() {
        return headers.isEmpty();
    }

    @Override
    public String get(CharSequence name) {
        List<String> headerValues = this.headers.get(name.toString());
        return (headerValues != null ? headerValues.get(0) : null);
    }

    @Override
    public List<String> getAll(CharSequence name) {
        return headers.get(name.toString());
    }

    @Override
    public boolean contains(CharSequence name) {
        return headers.containsKey(name.toString());
    }

    @Override
    public Set<String> names() {
        return headers.keySet();
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders add(CharSequence name, CharSequence value) {
        List<String> headerValues = this.headers.computeIfAbsent(name.toString(), s -> new LinkedList<>());
        headerValues.add(value.toString());
        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders add(CharSequence name, Iterable<CharSequence> values) {
        if (values != null) {
            final LinkedList<String> list = new LinkedList<>();
            values.forEach(charSequence -> list.add(charSequence.toString()));
            headers.put(name.toString(), list);
        }

        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders set(CharSequence name, CharSequence value) {
        List<String> headerValues = new LinkedList<>();
        headerValues.add(value.toString());
        this.headers.put(name.toString(), headerValues);
        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders set(CharSequence name, Iterable<CharSequence> values) {
        if (values != null) {
            final LinkedList<String> list = new LinkedList<>();
            values.forEach(charSequence -> list.add(charSequence.toString()));
            headers.put(name.toString(), list);
        }

        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders remove(CharSequence name) {
        headers.remove(name.toString());
        return this;
    }

    @Override
    public void clear() {
        headers.clear();
    }

    @Override
    public boolean equals(Object o) {
        return headers.equals(o);
    }

    @Override
    public int hashCode() {
        return headers.hashCode();
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        final Iterator<Map.Entry<String, List<String>>> entries = headers.entrySet().iterator();

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return entries.hasNext();
            }

            @Override
            public Map.Entry<String, String> next() {
                Map.Entry<String, List<String>> next = entries.next();
                return new Map.Entry<>() {
                    @Override
                    public String getKey() {
                        return next.getKey();
                    }

                    @Override
                    public String getValue() {
                        return next.getValue().get(0);
                    }

                    @Override
                    public String setValue(String value) {
                        throw new IllegalStateException();
                    }
                };
            }
        };
    }

    @Override
    public Map<String, String> toSingleValueMap() {
        return HttpHeaders.super.toSingleValueMap();
    }

    @Override
    public boolean containsAllKeys(Collection<String> names) {
        return HttpHeaders.super.containsAllKeys(names);
    }

    @Override
    public boolean containsKey(Object key) {
        return headers.containsKey(key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        return headers.containsValue(value);
    }

    /**
     * @see LinkedCaseInsensitiveMap#get(Object)
     * Contrary to {@link DefaultHttpHeaders#get(CharSequence)}, the list of values is returned, not only the first element
     */
    @Override
    public List<String> get(Object key) {
        return headers.get(key.toString());
    }

    /**
     * @see HashMap#putVal(int, Object, Object, boolean, boolean), returns the previous value if present, else null.
     */
    @Override
    public List<String> put(String key, List<String> value) {
        return headers.put(key, value);
    }

    /**
     * @see HashMap#remove(Object), returns the previous value (can bee {@code null}) associated with {@code key} or null if none.
     */
    @Override
    public List<String> remove(Object key) {
        return headers.remove(key.toString());
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> map) {
        headers.putAll(map);
    }

    @Override
    public Set<String> keySet() {
        return headers.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        return headers.values();
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        return headers.entrySet();
    }

    @Override
    public String getFirst(String header) {
        List<String> headerValues = this.headers.get(header);
        return (headerValues != null ? headerValues.get(0) : null);
    }

    @Override
    public void add(String name, String value) {
        List<String> headerValues = this.headers.computeIfAbsent(name, s -> new LinkedList<>());
        headerValues.add(value);
    }

    @Override
    public void set(String name, String value) {
        List<String> headerValues = new LinkedList<>();
        headerValues.add(value);
        this.headers.put(name, headerValues);
    }

    @Override
    public void setAll(Map<String, String> values) {
        for (Entry<String, String> entry : values.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }
}
