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
package io.gravitee.gateway.jupiter.api.context;

import io.gravitee.el.TemplateEngine;
import io.gravitee.gateway.jupiter.api.ExecutionFailure;
import io.reactivex.Completable;
import java.util.Map;
import java.util.Set;

public interface ExecutionContext {
    String ATTR_PREFIX = "gravitee.attribute.";

    String ATTR_INTERNAL_PREFIX = "gravitee.internal.attribute.";
    String ATTR_INTERNAL_EXECUTION_FAILURE = ATTR_INTERNAL_PREFIX + "execution-failure";
    String ATTR_INTERNAL_FLOW_STAGE = ATTR_INTERNAL_PREFIX + "flow.stage";

    String ATTR_CONTEXT_PATH = ATTR_PREFIX + "context-path";
    String ATTR_RESOLVED_PATH = ATTR_PREFIX + "resolved-path";
    String ATTR_APPLICATION = ATTR_PREFIX + "application";
    String ATTR_API = ATTR_PREFIX + "api";
    String ATTR_API_DEPLOYED_AT = ATTR_PREFIX + "api.deployed-at";
    String ATTR_SUBSCRIPTION_ID = ATTR_PREFIX + "user-id";
    String ATTR_PLAN = ATTR_PREFIX + "plan";
    String ATTR_REQUEST_METHOD = ATTR_PREFIX + "request.method";
    String ATTR_REQUEST_ENDPOINT = ATTR_PREFIX + "request.endpoint";
    String ATTR_INVOKER = ATTR_PREFIX + "request.invoker";
    String ATTR_QUOTA_COUNT = ATTR_PREFIX + "quota.count";
    String ATTR_QUOTA_REMAINING = ATTR_PREFIX + "quota.remaining";
    String ATTR_QUOTA_LIMIT = ATTR_PREFIX + "quota.limit";
    String ATTR_QUOTA_RESET_TIME = ATTR_PREFIX + "quota.reset.time";
    String ATTR_USER = ATTR_PREFIX + "user";
    String ATTR_USER_ROLES = ATTR_PREFIX + "user.roles";
    String ATTR_ORGANIZATION = ATTR_PREFIX + "organization";
    String ATTR_ENVIRONMENT = ATTR_PREFIX + "environment";

    String ATTR_SUBSCRIPTION = "subscription";

    String ATTR_SUBSCRIPTION_TYPE = "subscription_type";

    /**
     * Adapted ExecutionContext for V3 compatibility.
     */
    String ATTR_ADAPTED_CONTEXT = ATTR_INTERNAL_PREFIX + "contextAdapter";

    /**
     * Interrupted the current execution while indicating that the response can be sent "as is" to the downstream.
     * This has direct impact on how the remaining execution flow will behave (ex: remaining policies in a policy chain won't be executed).
     */
    Completable interrupt();

    /**
     * Same as {@link #interrupt()} but with an {@link ExecutionFailure} object that indicates that the execution has failed. The {@link ExecutionFailure} can be processed in order to build a proper response (ex: based on templating, with appropriate accept-encoding, ...).
     */
    Completable interruptWith(final ExecutionFailure failure);

    // TODO will need to be introduce in future
    // ExecutableApi executableApi();

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
     * @param <T> the expected type of the attribute value. Specify {@link Object} if you expect value of any type.
     *
     * @return an Object containing the value of the attribute, or null if the attribute does not exist.
     */
    <T> T getAttribute(String name);

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
     * Internal attributes can be used to avoid mixin with attribute set by the user during the request processing.
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
     * Internal attributes are intended to not be manipulated by end-users.
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
