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
package io.gravitee.gateway.reactive.api.policy;

import io.gravitee.gateway.reactive.api.context.HttpExecutionContext;
import io.gravitee.gateway.reactive.api.context.MessageExecutionContext;
import io.gravitee.gateway.reactive.api.context.http.HttpMessageExecutionContext;
import io.gravitee.gateway.reactive.api.context.http.HttpPlainExecutionContext;
import io.gravitee.gateway.reactive.api.policy.http.HttpSecurityPolicy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * @deprecated see {@link HttpSecurityPolicy}
 */
@Deprecated(forRemoval = true)
public interface SecurityPolicy extends HttpSecurityPolicy, Policy {
    default Maybe<SecurityToken> extractSecurityToken(HttpPlainExecutionContext ctx) {
        return extractSecurityToken((HttpExecutionContext) ctx);
    }

    Maybe<SecurityToken> extractSecurityToken(final HttpExecutionContext ctx);

    @Override
    default Completable onResponse(final HttpExecutionContext ctx) {
        return Policy.super.onResponse(ctx);
    }

    @Override
    default Completable onResponse(final HttpPlainExecutionContext ctx) {
        return HttpSecurityPolicy.super.onResponse(ctx);
    }

    @Override
    default Completable onMessageResponse(final MessageExecutionContext ctx) {
        return Policy.super.onMessageResponse(ctx);
    }

    @Override
    default Completable onMessageResponse(final HttpMessageExecutionContext ctx) {
        return HttpSecurityPolicy.super.onMessageResponse(ctx);
    }
}
