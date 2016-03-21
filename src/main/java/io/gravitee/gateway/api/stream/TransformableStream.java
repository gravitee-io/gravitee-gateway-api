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
import io.gravitee.gateway.api.stream.exception.TransformationException;

/**
 * @author David BRASSELY (david at gravitee.io)
 * @author GraviteeSource Team
 */
public abstract class TransformableStream extends BufferedReadWriteStream {

    protected final Buffer buffer;

    public TransformableStream(int length) {
        buffer = Buffer.buffer(length);
    }

    public TransformableStream() {
        buffer = Buffer.buffer();
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

    protected abstract Buffer transform() throws TransformationException;
}
