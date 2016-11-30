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
package io.gravitee.gateway.api.http.stream;

import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.stream.TransformableStream;
import io.gravitee.gateway.api.stream.exception.TransformationException;

import java.util.function.Function;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public final class TransformableRequestStreamBuilder {
    private final Request request;
    private String contentType;
    private Function<Buffer, Buffer> function;
    private int contentLength = -1;

    private TransformableRequestStreamBuilder(Request request) {
        this.request = request;
    }

    public static TransformableRequestStreamBuilder on(Request request) {
        return new TransformableRequestStreamBuilder(request);
    }

    public TransformableRequestStreamBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public TransformableRequestStreamBuilder contentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public TransformableRequestStreamBuilder transform(Function<Buffer, Buffer> function) {
        this.function = function;
        return this;
    }

    public TransformableStream build() {
        return new TransformableRequestStream(request, contentLength) {
            @Override
            protected String to() {
                return contentType;
            }

            @Override
            protected Function<Buffer, Buffer> transform() throws TransformationException {
                return function;
            }
        };
    }

}
