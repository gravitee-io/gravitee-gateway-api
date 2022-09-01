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
package io.gravitee.gateway.jupiter.api.policy;

import io.gravitee.gateway.jupiter.api.context.GenericExecutionContext;
import io.gravitee.gateway.jupiter.api.context.HttpExecutionContext;
import io.gravitee.gateway.jupiter.api.context.MessageExecutionContext;
import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * {@link SecurityPolicy} is a {@link Policy} that can be used for securing a plan that can require a subscription.
 * Implementing a {@link SecurityPolicy} requires to implement some additional behavior in order to be used by the gateway during the security chain execution that will identify which plan the consumer is using:
 * <ul>
 *     <li>{@link #order()}: to define the priority compared to other security policies</li>
 *     <li>{@link #extractSecurityToken(HttpExecutionContext)}: to extract the {@link SecurityToken} from the request execution context</li>
 *     <li>{@link #requireSubscription()} : to indicate if it require a valid subscription or not</li>
 * </ul>
 */
public interface SecurityPolicy extends Policy {
    int DEFAULT_ORDER = 1000;

    /**
     * Extracts the {@link SecurityToken} from the request execution context.
     * If no relevant {@link SecurityToken} is found, it returns an empty Maybe, so that policy won't be executed.
     *
     * @param ctx the current request execution context.
     *
     * @return the {@link SecurityToken} found in the request execution context
     */
    Maybe<SecurityToken> extractSecurityToken(final HttpExecutionContext ctx);

    /**
     * Security policy can be used together with a plan that requires a subscription.
     * By enabling the subscription requirement, the gateway will automatically validate the subscription associated with the current request and reject the request if the subscription is invalid.
     *
     * @return <code>true</code> to indicates to check the subscription after the security policy has been successfully executed, <code>false</code> else.
     */
    default boolean requireSubscription() {
        return false;
    }

    /**
     * Defines the priority order of the security policy compared to others.
     * By default, it is set to {@link #DEFAULT_ORDER}. The lower is the order, the highest is the priority of the policy compared to other.
     * The order is not guaranteed when 2 {@link SecurityPolicy} have the same order.
     *
     * @return the order of the security policy when multiple security policies can be involved.
     */
    default int order() {
        return DEFAULT_ORDER;
    }

    /**
     * The <code>onResponse(HttpExecutionContext)</code> method shouldn't call on a <code>SecurityPolicy</code>
     *
     * @return a <code>UnsupportedOperationException</code
     */
    default Completable onResponse(final HttpExecutionContext ctx) {
        return Completable.error(new UnsupportedOperationException("onResponse method is not supported by a security policy"));
    }

    /**
     * The <code>onMessageResponse(MessageExecutionContext)</code>  method shouldn't call on a <code>SecurityPolicy</code>
     *
     * @return the order of the security policy when multiple security policies can be involved.
     */
    default Completable onMessageResponse(final MessageExecutionContext ctx) {
        return Completable.error(new UnsupportedOperationException("onMessageResponse method is not supported by a security policy"));
    }
}
