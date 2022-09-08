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

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import java.util.Map;
import java.util.Set;

public interface Message {
    /**
     * Returns the value of the attribute cast in the expected type T, or <code>null</code> if no attribute for the given
     * name exists.
     *
     * @param name the name of the attribute.
     * @param <T> the expected type of the attribute value. Specify {@link Object} if you expect value of any type.
     *
     * @return an Object containing the value of the attribute, or null if the attribute does not exist.
     */
    <T> T attribute(final String name);

    /**
     * Stores an attribute in this message.
     *
     * @param name a String specifying the name of the attribute.
     * @param value the Object to be stored.Returns:
     * @return reference to itself for easily chain calls
     */
    Message attribute(final String name, final Object value);

    /**
     * Removes an attribute from this request. This method is not generally needed as attributes only persist as
     * long as the message is being handled.
     *
     * @param name the name of the attribute to remove.
     * @return reference to itself for easily chain calls
     */
    Message removeAttribute(final String name);

    /**
     * Returns a <code>Set</code> containing the names of the attributes available to this message. This method returns
     * an empty <code>Set</code> if the message has no attributes available to it.
     *
     * @return a set of strings containing the names of the message's attributes.
     */
    Set<String> attributeNames();

    /**
     * Get all attributes available.
     *
     * @param <T> the expected type of the attribute values. Specify {@link Object} if you expect values of any types.
     * @return the list of all the attributes.
     */
    <T> Map<String, T> attributes();

    String id();

    Map<String, Object> metadata();

    HttpHeaders headers();

    Message headers(final HttpHeaders headers);

    Buffer content();

    Message content(final Buffer buffer);
}
