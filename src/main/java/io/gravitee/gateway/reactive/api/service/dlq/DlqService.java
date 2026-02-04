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
package io.gravitee.gateway.reactive.api.service.dlq;

import io.gravitee.gateway.reactive.api.context.http.HttpExecutionContext;
import io.gravitee.gateway.reactive.api.message.Message;
import io.reactivex.rxjava3.core.Flowable;

/**
 * Allows applying behavior on a flow of messages in order to filter messages in error and send them ot a Dead Letter Queue.
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface DlqService {
    /**
     * Set up the dead letter queue mechanism on the incoming flow of messages.
     * It is the responsibility of the implementation to filter incoming messages and send only the appropriate subset of messages to the DLQ
     * (ex: message in error, message matching a particular condition, ..).
     *
     * @param messages the incoming flow of messages.
     * @param ctx the execution context for EL evaluation.
     * @return the original flow of messages, so it can be chained easily.
     */
    Flowable<Message> apply(Flowable<Message> messages, HttpExecutionContext ctx);
}
