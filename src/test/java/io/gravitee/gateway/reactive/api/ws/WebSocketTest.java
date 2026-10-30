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
package io.gravitee.gateway.reactive.api.ws;

import static org.assertj.core.api.Assertions.assertThat;

import io.gravitee.gateway.api.buffer.Buffer;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Mateusz Jóźwiak (mateusz.jozwiak at incubly.com)
 * @author GraviteeSource Team
 */
class WebSocketTest {

    private final RecordingWebSocket cut = new RecordingWebSocket();

    @Test
    void should_not_support_text_frames_by_default() {
        assertThat(cut.supportsTextFrames()).isFalse();
    }

    @Test
    void should_fail_text_write_with_unsupported_operation_by_default() {
        cut.writeText(Buffer.buffer("hello")).test().assertError(UnsupportedOperationException.class);
    }

    @Test
    void should_not_write_anything_when_text_write_is_unsupported() {
        cut.writeText(Buffer.buffer("hello")).test();

        assertThat(cut.writes).isEmpty();
    }

    @Test
    void should_fail_on_each_subscription_and_not_at_call_time() {
        Completable write = cut.writeText(Buffer.buffer("hello"));
        List<Throwable> errors = new ArrayList<>();

        write.subscribe(() -> {}, errors::add);
        write.subscribe(() -> {}, errors::add);

        assertThat(errors).hasSize(2).hasOnlyElementsOfType(UnsupportedOperationException.class);
        assertThat(errors.get(0)).isNotSameAs(errors.get(1));
    }

    private static class RecordingWebSocket implements WebSocket {

        private final List<Buffer> writes = new ArrayList<>();

        @Override
        public Single<WebSocket> upgrade() {
            return Single.just(this);
        }

        @Override
        public Completable write(Buffer buffer) {
            writes.add(buffer);
            return Completable.complete();
        }

        @Override
        public Completable writePing() {
            return Completable.complete();
        }

        @Override
        public Flowable<Buffer> read() {
            return Flowable.empty();
        }

        @Override
        public Completable close() {
            return Completable.complete();
        }

        @Override
        public Completable close(int status) {
            return Completable.complete();
        }

        @Override
        public Completable close(int status, String reason) {
            return Completable.complete();
        }

        @Override
        public boolean upgraded() {
            return true;
        }

        @Override
        public boolean closed() {
            return false;
        }
    }
}
