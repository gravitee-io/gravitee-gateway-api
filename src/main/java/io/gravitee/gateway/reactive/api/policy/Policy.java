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
package io.gravitee.gateway.reactive.api.policy;

import io.gravitee.gateway.reactive.api.context.ExecutionContext;
import io.gravitee.gateway.reactive.api.context.RequestExecutionContext;
import io.gravitee.gateway.reactive.api.message.Message;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface Policy {
    String getId();

    default Completable onRequest(final RequestExecutionContext ctx) {
        return Completable.complete();
    }

    default Completable onResponse(final RequestExecutionContext ctx) {
        return Completable.complete();
    }

    default Maybe<Message> onMessage(final ExecutionContext ctx, final Message message) {
        return Maybe.just(message);
    }

    default Flowable<Message> onMessageFlow(final ExecutionContext ctx, final Flowable<Message> messageFlow) {
        return messageFlow;
    }
}
