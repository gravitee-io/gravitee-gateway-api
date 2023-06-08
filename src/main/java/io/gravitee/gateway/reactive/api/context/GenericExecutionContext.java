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
package io.gravitee.gateway.reactive.api.context;

import io.gravitee.el.TemplateEngine;
import io.gravitee.reporter.api.v4.metric.Metrics;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GenericExecutionContext {
    /**
     * Get the current request stuck to this execution context.
     *
     * @return the request attached to this execution context.
     */
    GenericRequest request();

    /**
     * Get the current response stuck to this execution context.
     *
     * @return the response attached to this execution context.
     */
    GenericResponse response();
    /**
     * Get the metrics associated to the context.
     *
     * @return a {@link Metrics} object.
     */
    Metrics metrics();

    <T> T getComponent(Class<T> componentClass);

    /**
     * Same a {@link #putAttribute(String, Object)}.
     *
     * @param name a String specifying the name of the attribute.
     * @param value the Object to be stored.
     */
    void setAttribute(String name, Object value);

    /**
     * Stores an attribute in this request. Attributes are reset between requests.
     * <b>Note</b>: prefer use of {@link #putInternalAttribute(String, Object)} in case the attribute is not set by the user itself.
     *
     * @param name a String specifying the name of the attribute.
     * @param value the Object to be stored.
     */
    void putAttribute(String name, Object value);

    /**
     * Removes an attribute from this request. This method is not generally needed as attributes only persist as
     * long as the request is being handled.
     *
     * @param name the name of the attribute to remove.
     */
    void removeAttribute(String name);

    /**
     * Returns the value of the attribute cast in the expected type T, or <code>null</code> if no attribute for the given
     * name exists.
     *
     * @param name the name of the attribute.
     * @param <T>  the expected type of the attribute value. Specify {@link Object} if you expect value of any type.
     * @return an Object containing the value of the attribute, or null if the attribute does not exist.
     */
    <T> T getAttribute(String name);

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
     * One calling {@link #setAttribute(String, Object)} with a mutable collection will retrieve an immutable one.
     * </p>
     *
     * @param name the name of the attribute.
     * @param <T>  the expected type of the attribute value. Specify {@link Object} if you expect value of any type.
     * @return an immutable List with the attribute values.
     */
    <T> List<T> getAttributeAsList(String name);

    /**
     * Returns an Enumeration containing the names of the attributes available to this request. This method returns
     * an empty Enumeration if the request has no attributes available to it.
     *
     * @return a set of strings containing the names of the request's attributes.
     */
    Set<String> getAttributeNames();

    /**
     * Get all attributes available.
     *
     * @param <T> the expected type of the attribute values. Specify {@link Object} if you expect values of any types.
     * @return the list of all the attributes.
     */
    <T> Map<String, T> getAttributes();

    /**
     * Same as {@link #putInternalAttribute(String, Object)}
     *
     * @param name the name of the attribute.
     * @param value the Object to be stored.
     */
    void setInternalAttribute(String name, Object value);

    /**
     * Stores an internal attribute in this request. Attributes are reset between requests.
     * Internal attributes can be used to avoid mixin with attributes set by the user during the request processing.
     *
     * @param name the name of the attribute.
     * @param value the Object to be stored.
     */
    void putInternalAttribute(String name, Object value);

    /**
     * Removes an internal attribute from this request.
     *
     * @param name the name of the internal attribute to remove.
     */
    void removeInternalAttribute(String name);

    /**
     * Returns the value of the internal attribute cast in the expected type T, or <code>null</code> if no attribute for the given
     * name exists.
     *
     * @param name a String specifying the name of the attribute.
     * @param <T> the expected type of the attribute value. Specify {@link Object} if you expect value of any type.
     * @return an Object containing the value of the attribute, or null if the attribute does not exist
     */
    <T> T getInternalAttribute(String name);

    /**
     * Get all internal attributes available.
     * Internal attributes are not intended to be manipulated by end-users.
     * You can safely use internal attribute to temporary store useful objects required during different phases of the execution.
     *
     * @param <T> the expected type of the values. Specify {@link Object} if you expect values of any types.
     * @return the list of all the internal attributes.
     */
    <T> Map<String, T> getInternalAttributes();

    /**
     * Get the {@link TemplateEngine} that can be used to evaluate EL expressions.
     *
     * @return the El {@link TemplateEngine}.
     */
    TemplateEngine getTemplateEngine();
}
