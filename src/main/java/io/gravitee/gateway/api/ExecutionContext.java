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
package io.gravitee.gateway.api;

import io.gravitee.gateway.api.expression.TemplateEngine;

import java.util.Enumeration;

/**
 * @author David BRASSELY (brasseld at gmail.com)
 */
public interface ExecutionContext {

    String ATTR_APPLICATION = "gravitee.attribute.application";
    String ATTR_API_KEY = "gravitee.attribute.api-key";
    String ATTR_REQUEST_BODY_CONTENT = "gravitee.attr.request.body-content";
    String ATTR_REQUEST_METHOD = "gravitee.attr.request.method";

    <T> T getComponent(Class<T> componentClass);

    /*
     * Execution attributes management.
     * This is a copy of the ServletRequest API.
     */

    /**
     * Stores an attribute in this request. Attributes are reset between requests.
     *
     * @param name a String specifying the name of the attribute
     * @param value the Object to be stored
     */
    void setAttribute(String name, Object value);

    /**
     * Removes an attribute from this request. This method is not generally needed as attributes only persist as
     * long as the request is being handled.
     *
     * @param name a String specifying the name of the attribute to remove
     */
    void removeAttribute(String name);

    /**
     * Returns the value of the named attribute as an Object, or <code>null</code> if no attribute of the given
     * name exists.
     *
     * @param name a String specifying the name of the attribute
     * @return an Object containing the value of the attribute, or null if the attribute does not exist
     */
    Object getAttribute(String name);

    /**
     * Returns an Enumeration containing the names of the attributes available to this request. This method returns
     * an empty Enumeration if the request has no attributes available to it.
     *
     * @return an Enumeration of strings containing the names of the request's attributes
     */
    Enumeration<String> getAttributeNames();

    TemplateEngine getTemplateEngine();
}
