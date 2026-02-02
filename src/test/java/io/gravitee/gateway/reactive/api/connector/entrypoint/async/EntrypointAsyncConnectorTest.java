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
package io.gravitee.gateway.reactive.api.connector.entrypoint.async;

import static io.gravitee.gateway.reactive.api.connector.entrypoint.async.EntrypointAsyncConnector.STOP_MESSAGE_ID;

import io.gravitee.gateway.reactive.api.message.DefaultMessage;
import io.gravitee.gateway.reactive.api.message.Message;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
class EntrypointAsyncConnectorTest {

    private final EntrypointAsyncConnectorImpl cut = new EntrypointAsyncConnectorImpl();

    @Test
    void should_emit_stop_message() throws Exception {
        final TestScheduler testScheduler = new TestScheduler();
        final Flowable<DefaultMessage> messages = Flowable.interval(10, TimeUnit.MILLISECONDS, testScheduler).map(i ->
            DefaultMessage.builder().build()
        );
        final TestSubscriber<Message> obs = messages.compose(cut.applyStopHook()).test();

        obs.assertNotComplete();
        testScheduler.advanceTimeBy(50, TimeUnit.MILLISECONDS);
        testScheduler.triggerActions();
        obs.assertNotComplete();

        cut.emitStopMessage();
        obs.assertComplete();

        obs.assertValueCount(6);
        obs.assertValueAt(5, message -> message.id().equals(STOP_MESSAGE_ID));
    }

    @Test
    void should_not_emit_stop_message_when_already_cancelled() throws Exception {
        final TestScheduler testScheduler = new TestScheduler();
        final Flowable<DefaultMessage> messages = Flowable.interval(10, TimeUnit.MILLISECONDS, testScheduler).map(i ->
            DefaultMessage.builder().build()
        );
        final TestSubscriber<Message> obs = messages.compose(cut.applyStopHook()).test();

        obs.assertNotComplete();
        testScheduler.advanceTimeBy(50, TimeUnit.MILLISECONDS);
        testScheduler.triggerActions();
        obs.assertNotComplete();
        obs.cancel();

        cut.emitStopMessage();
        obs.assertNotComplete();

        obs.assertValueCount(5);
    }
}
