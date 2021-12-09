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
import java.util.*;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class DefaultHttpHeaders implements io.gravitee.gateway.api.http.HttpHeaders {

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
        headers.forEach(stringListEntry -> add(stringListEntry.getKey(), stringListEntry.getValue()));
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
        List<String> headerValues = this.headers.get(name);
        return (headerValues != null ? headerValues.get(0) : null);
    }

    @Override
    public List<String> getAll(CharSequence name) {
        return headers.get(name);
    }

    @Override
    public boolean contains(CharSequence name) {
        return headers.containsKey(name);
    }

    @Override
    public Set<String> names() {
        return headers.keySet();
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders add(CharSequence name, CharSequence value) {
        List<String> headerValues = this.headers.get(name);
        if (headerValues == null) {
            headerValues = new LinkedList<>();
            this.headers.put((String) name, headerValues);
        }
        headerValues.add((String) value);
        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders add(CharSequence name, Iterable<CharSequence> values) {
        if (values != null) {
            final LinkedList<String> list = new LinkedList<>();
            values.forEach(charSequence -> list.add((String) charSequence));
            headers.put((String) name, list);
        }

        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders set(CharSequence name, CharSequence value) {
        List<String> headerValues = new LinkedList<>();
        headerValues.add((String) value);
        this.headers.put((String) name, headerValues);

        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders set(CharSequence name, Iterable<CharSequence> values) {
        if (values != null) {
            final LinkedList<String> list = new LinkedList<>();
            values.forEach(charSequence -> list.add((String) charSequence));
            headers.put((String) name, list);
        }

        return this;
    }

    @Override
    public io.gravitee.gateway.api.http.HttpHeaders remove(CharSequence name) {
        headers.remove(name);
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
    public Iterator<Map.Entry<String, String>> iterator() {
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
}
