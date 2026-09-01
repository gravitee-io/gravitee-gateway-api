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
package io.gravitee.gateway.reactive.api.context.llm;

import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author GraviteeSource Team
 */
class LlmResponseTest {

    private static final Turn HELLO = new Turn(Role.ASSISTANT, "hello", null, null, List.of(), Map.of());
    private static final Turn WORLD = new Turn(Role.ASSISTANT, " world", null, null, List.of(), Map.of());
    private static final ErrorFrame FAILED = new ErrorFrame(StopReason.CONTENT_FILTER, "pii detected");

    private final LlmResponse cut = mock(LlmResponse.class, CALLS_REAL_METHODS);

    @Test
    void should_keep_only_the_turns_of_the_delta_flow() {
        doReturn(Flowable.just(HELLO, FAILED, WORLD)).when(cut).deltas();

        cut.turns().test().assertResult(HELLO, WORLD);
    }

    @Test
    void should_apply_the_delta_transformation_on_every_frame_including_the_error_one() {
        doReturn(Completable.complete()).when(cut).onDeltas(any());

        cut.onDelta(frame -> frame instanceof Turn turn ? new Turn(turn.role(), "[redacted]", null, null, List.of(), Map.of()) : FAILED);

        ArgumentCaptor<FlowableTransformer<Frame, Frame>> transformer = ArgumentCaptor.captor();
        verify(cut).onDeltas(transformer.capture());

        Flowable.just(HELLO, new ErrorFrame(StopReason.ERROR, "boom"))
            .compose(transformer.getValue())
            .test()
            .assertResult(new Turn(Role.ASSISTANT, "[redacted]", null, null, List.of(), Map.of()), FAILED);
    }
}
