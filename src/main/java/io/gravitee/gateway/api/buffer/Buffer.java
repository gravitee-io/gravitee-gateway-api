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
package io.gravitee.gateway.api.buffer;

import io.gravitee.common.util.ServiceLoaderHelper;

import java.nio.charset.Charset;

/**
 * Mainly inspired by Vertx.io
 *
 * @see io.vertx.core.buffer.Buffer
 *
 * @author David BRASSELY (david at gravitee.io)
 * @author GraviteeSource Team
 */
public interface Buffer {

    static Buffer buffer() {
        return factory.buffer();
    }

    static Buffer buffer(int initialSizeHint) {
        return factory.buffer(initialSizeHint);
    }

    static Buffer buffer(String string) {
        return factory.buffer(string);
    }

    static Buffer buffer(String string, String enc) {
        return factory.buffer(string, enc);
    }

    static Buffer buffer(byte[] bytes) {
        return factory.buffer(bytes);
    }

    Buffer appendBuffer(Buffer buff);

    @Override
    String toString();

    String toString(String enc);

    String toString(Charset enc);

    byte[] getBytes();

    int length();

    Object getNativeBuffer();

    BufferFactory factory = ServiceLoaderHelper.loadFactory(BufferFactory.class);

    Buffer appendBuffer(Buffer buffer, int length);
}
