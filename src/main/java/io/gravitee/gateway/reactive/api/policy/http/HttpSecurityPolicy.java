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
package io.gravitee.gateway.reactive.api.policy.http;

import io.gravitee.gateway.reactive.api.context.http.HttpMessageExecutionContext;
import io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext;
import io.gravitee.gateway.reactive.api.policy.Policy;
import io.gravitee.gateway.reactive.api.policy.SecurityToken;
import io.gravitee.gateway.reactive.api.policy.base.BaseSecurityPolicy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * {@link HttpSecurityPolicy} is a {@link Policy} that can be used for securing a plan that can require a subscription.
 * Implementing a {@link HttpSecurityPolicy} requires to implement some additional behavior in order to be used by the gateway during the security chain execution that will identify which plan the consumer is using:
 * <ul>
 *     <li>{@link #order()}: to define the priority compared to other security policies</li>
 *     <li>{@link #extractSecurityToken(HttpPlainExecutionContext)}: to extract the {@link SecurityToken} from the request execution context</li>
 *     <li>{@link #requireSubscription()} : to indicate if it require a valid subscription or not</li>
 * </ul>
 */
public interface HttpSecurityPolicy extends HttpPolicy, BaseSecurityPolicy {
    /**
     * Extracts the {@link SecurityToken} from the request execution context.
     * If no relevant {@link SecurityToken} is found, it returns an empty Maybe, so that policy won't be executed.
     *
     * @param ctx the current request execution context.
     *
     * @return the {@link SecurityToken} found in the request execution context
     */
    Maybe<SecurityToken> extractSecurityToken(final HttpPlainExecutionContext ctx);

    /**
     * The <code>onResponse(HttpSimpleExecutionContext)</code> method shouldn't call on a <code>SecurityPolicy</code>
     *
     * @return a <code>UnsupportedOperationException</code
     */
    default Completable onResponse(final HttpPlainExecutionContext ctx) {
        return Completable.error(new UnsupportedOperationException("onResponse method is not supported by a security policy"));
    }

    /**
     * The <code>onMessageResponse(MessageExecutionContext)</code>  method shouldn't call on a <code>SecurityPolicy</code>
     *
     * @return the order of the security policy when multiple security policies can be involved.
     */
    default Completable onMessageResponse(final HttpMessageExecutionContext ctx) {
        return Completable.error(new UnsupportedOperationException("onMessageResponse method is not supported by a security policy"));
    }
}
