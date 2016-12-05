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
package io.gravitee.gateway.api.stream;

import io.gravitee.gateway.api.buffer.Buffer;

import java.util.function.Function;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class TransformableStream extends BufferedReadWriteStream {

    protected final Buffer buffer;
    private String contentType;
    private Function<Buffer, Buffer> transform;

    public TransformableStream(int length) {
        buffer = (length != -1) ? Buffer.buffer(length) : Buffer.buffer();
    }

    public TransformableStream() {
        this(-1);
    }

    @Override
    public TransformableStream write(Buffer chunk) {
        buffer.appendBuffer(chunk);
        return this;
    }

    protected TransformableStream flush(Buffer chunk) {
        super.write(chunk);
        return this;
    }

    void contentType(String contentType) {
        this.contentType = contentType;
    }

    public String contentType() {
        return this.contentType;
    }

    void transform(Function<Buffer, Buffer> transform) {
        this.transform = transform;
    }

    public Function<Buffer, Buffer> transform() {
        return transform;
    }
}
