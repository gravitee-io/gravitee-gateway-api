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
package io.gravitee.gateway.reactive.api.message;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.reactivex.rxjava3.annotations.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Message {
    /**
     * Get the current id of the message.
     * The id may be <code>null</code>.
     *
     * @return the id of the message or <code>null</code> in case the message doesn't have any id.
     */
    String id();

    /**
     * The correlation id of the message.
     * This id is never null and help to track messages across the gateway.
     *
     * @return the correlation id of the message.
     */
    String correlationId();
    /**
     * The parent correlation id of the message.
     * This id refers to a possible parent when the message has been created from another one.
     *
     * @return the parent correlation id of the message or <code>null</code> in case the message doesn't have any parent.
     */
    String parentCorrelationId();

    /**
     * The timestamp for when this message was created.
     *
     * @return the instant timestamp for the message.
     */
    long timestamp();

    /**
     * Flag indicating the message is in error.
     * An error can occur during the processing. In that case, it is flagged in <code>error</code> to indicate that a special behavior may have to be applied for it.
     *
     * @return <code>true</code> if the message is in error, <code>false</code> otherwise.
     */
    boolean error();

    /**
     * Flag indicating the message is in error.
     * An error can occur during the processing. In that case, it is flagged in <code>error</code> to indicate that a special behavior may have to be applied for it.
     *
     */
    Message error(boolean error);

    /**
     * Get the metadata of the message. Metadata are read-only. They usually come from the source that emits the message and can't be changed.
     *
     * @return the metadata of the message or an empty map if the message doesn't have any metadata.
     */
    Map<String, Object> metadata();

    /**
     * Get the headers of the message.
     *
     * @return a read-write map of headers.
     */
    HttpHeaders headers();

    /**
     * Set the headers of the message.
     *
     * @param headers the headers to set on the message.
     * @return reference to itself for easily chain calls.
     */
    Message headers(final HttpHeaders headers);

    /**
     * The current content of the message.
     * The content may be of any type and is represented by a {@link Buffer} structure.
     *
     * @return the content of the message as a {@link Buffer}.
     * @see Buffer
     */
    @Nullable
    Buffer content();

    /**
     * Set the content of the message.
     *
     * @param content the buffer representing the content of the message.
     * @return reference to itself for easily chain calls.
     */
    Message content(final Buffer content);

    /**
     * Set the content of the message.
     *
     * @param content the buffer representing the content of the message.
     * @return reference to itself for easily chain calls.
     */
    Message content(final String content);

    /**
     * MUST be called to acknowledge this message when it has been well-processed.
     */
    void ack();

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
     * Return the attribute as an immutable {@link List}.
     * This method acts differently depending on the type of the attribute's value.
     * Elements of the {@link List} are mapped as follows:
     * <p>
     * <b>For String:</b>
     *     <ul>
     *          <li>comma separated string are spitted and returned trimmed</li>
     *          <li>Parsable JSON Array elements are returned as strings (including objects)</li>
     *          <li>if none of the above, the string is simply wrapped</li>
     *      </ul>
     * </p>
     * <p>
     *     <b>For Collection:</b>
     *     <ul>
     *         <li>Elements of the collection are wrapped in a new List</li>
     *     </ul>
     * </p>
     * <p>
     *     <b>For Array:</b>
     *     <ul>
     *         <li>Each elements of the is wrapped into the returned List</li>
     *     </ul>
     * </p>
     * <p>
     * <b>For any other cases:</b> the value is simply wrapped into the returned List.
     * <b>Notes:</b>
     * One calling {@link #attribute(String, Object)} with a mutable collection will retrieve an immutable one.
     * </p>
     *
     * @param name the name of the attribute.
     * @param <T>  the expected type of the attribute value. Specify {@link Object} if you expect value of any type.
     * @return an immutable List with the attribute values.
     */
    <T> List<T> attributeAsList(String name);

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

    <T> T internalAttribute(String name);

    /**
     * Stores an internal attribute in this message.
     *
     * Internal attributes can be used to avoid mixin with attributes set by the user during the request processing..
     * You can safely use internal attribute to temporary store useful objects required during different phases of the execution.
     *
     * @param name a String specifying the name of the attribute.
     * @param value the Object to be stored.Returns:
     * @return reference to itself for easily chain calls
     */
    Message internalAttribute(String name, Object value);

    /**
     * Removes an internal attribute from this request. This method is not generally needed as internal attributes only persist as
     * long as the message is being handled.
     *
     * @param name the name of the internal attribute to remove.
     * @return reference to itself for easily chain calls.
     */
    Message removeInternalAttribute(String name);

    /**
     * Returns a <code>Set</code> containing the names of the internal attributes available to this message. This method returns
     * an empty <code>Set</code> if the message has no internal attributes available to it.
     *
     * @return a set of strings containing the names of the message's internal attributes.
     */
    Set<String> internalAttributeNames();

    /**
     * Get all internal attributes available.
     * Internal attributes are not intended to be manipulated by end-users.
     * You can safely use internal attribute to temporary store useful objects required during different phases of the execution.
     *
     * @param <T> the expected type of the internal attribute values. Specify {@link Object} if you expect values of any types.
     * @return the list of all the internal attributes.
     */
    <T> Map<String, T> internalAttributes();
}
