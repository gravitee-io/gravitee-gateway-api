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

import io.gravitee.gateway.jupiter.api.context.RequestExecutionContext;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * {@link SecurityPolicy} is a {@link Policy} that can be used for securing a plan that can require a subscription.
 * Implementing a {@link SecurityPolicy} requires to implement some additional behavior in order to be used by the gateway during the security chain execution that will identify which plan the consumer is using:
 * <ul>
 *     <li>{@link #order()}: to define the priority compared to other security policies</li>
 *     <li>{@link #support(RequestExecutionContext)}: to check if the current request can be handled or not</li>
 *     <li>{@link #requireSubscription()} : to indicate if it require a valid subscription or not</li>
 *     <li>{@link #onInvalidSubscription(RequestExecutionContext)} : to defined what to do in case of invalid subscription</li>
 * </ul>
 */
public interface SecurityPolicy extends Policy {
    int DEFAULT_ORDER = 1000;

    /**
     * Indicates whether the security policy can handle the request execution context or not.
     * The policy can extract any information from the execution context to determine if the current request can be handled or not (ex: specific request header, body extraction, ...).
     *
     * @param ctx the current execution context that can be used to determine if this security policy can handle the current request execution or not.
     *
     * @return <code>true</code> if the policy can handle the current request execution, <code>false</code> otherwise.
     */
    Single<Boolean> support(final RequestExecutionContext ctx);

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
     * Defines if the subscription associated must be checked and the action to execute when the subscription is not valid.
     * Usually, it allows to the security policy to interrupt the execution with an error status code and some response headers.
     *
     * Returning <code>null</code> indicates that the subscription does have to be verified after the policy completes successfully.
     *
     * @return a {@link Completable} corresponding to the actions to execute when the gateway has detected an invalid subscription
     * or <code>null</code> to disable subscription check.
     */
    default Completable onInvalidSubscription(final RequestExecutionContext ctx) {
        return Completable.complete();
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
}
