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
package io.gravitee.gateway.reactive.api.context.tcp;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.reactive.api.context.base.BaseResponse;
import io.reactivex.rxjava3.core.Flowable;

/**
 * Response specialized for TCP allowing piping traffic from client to backend.
 *
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface TcpResponse extends BaseResponse {
    /**
     * Setup internally a pipe downstream traffic bytes from backend to the client
     */
    void pipeDownstream();

    /**
     * Set the current response body chunks from a {@link Flowable} of {@link Buffer}.
     * This is useful to directly pump the upstream chunks to the downstream without having to load all the chunks in memory.
     * <p>
     * <b>WARN:</b>
     * <ul>
     *  <li>replacing the response chunks will <b>NOT</b> "drain" the previous response that was in place.</li>
     *  <li>You <b>MUST</b> ensure to consume the previous chunks by yourself when using it.</li>
     * </ul>
     * </p>
     *
     * @param chunks the {@link Flowable} of chunks representing the response to push back to the downstream.
     */
    void chunks(final Flowable<Buffer> chunks);

    /**
     * Get the current response body chunks as {@link Flowable} of {@link Buffer}.
     * This is useful when you want to manipulate the entire body without having to load it in memory.
     * <p>
     * <b>WARN:</b> you should not keep a direct reference on the body chunks as they could be overridden by others at anytime.
     * </p>
     *
     * @return a {@link Flowable} containing the current body response chunks.
     */
    Flowable<Buffer> chunks();
}
