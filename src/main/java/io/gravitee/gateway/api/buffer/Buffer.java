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
package io.gravitee.gateway.api.buffer;

import io.gravitee.common.util.ServiceLoaderHelper;
import io.netty.buffer.ByteBuf;
import io.reactivex.rxjava3.annotations.NonNull;
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
    /**
     * Creates an empty buffer.
     *
     * @return the newly created buffer.
     */
    static Buffer buffer() {
        return factory.buffer();
    }

    /**
     * Creates a buffer from a vertx {@link io.vertx.core.buffer.Buffer}.
     *
     * @return the newly created buffer.
     * @see #buffer(ByteBuf)
     */
    static Buffer buffer(io.vertx.core.buffer.Buffer vertxBuffer) {
        return factory.buffer(vertxBuffer);
    }

    /**
     * Creates a buffer from a vertx {@link io.vertx.rxjava3.core.buffer.Buffer}.
     *
     * @return the newly created buffer.
     * @see #buffer(ByteBuf)
     */
    static Buffer buffer(io.vertx.rxjava3.core.buffer.Buffer vertxBuffer) {
        return factory.buffer(vertxBuffer.getDelegate());
    }

    /**
     * Creates a buffer from a netty {@link ByteBuf}.
     *
     * @return the newly created buffer.
     */
    static Buffer buffer(ByteBuf nativeBuffer) {
        return factory.buffer(nativeBuffer);
    }

    /**
     * Creates an empty buffer with reserved space.
     *
     * @return the newly created buffer.
     */
    static Buffer buffer(int initialSizeHint) {
        return factory.buffer(initialSizeHint);
    }

    /**
     * Creates a buffer from the specified {@link String}.
     * <p/>
     * <b>WARN</b>: creating a buffer from a string allocates dedicated space in the memory.
     * When creating a {@link Buffer} with the content of another one, it is recommended to use one of the alternative methods:
     * <ul>
     *     <li>{@link #buffer(io.vertx.core.buffer.Buffer)}</li>
     *     <li>{@link #buffer(io.vertx.reactivex.core.buffer.Buffer)}</li>
     *     <li>{@link #buffer(ByteBuf)}</li>
     * </ul>
     *
     * @return the newly created buffer.
     * @see #buffer(ByteBuf)
     * @see #buffer(io.vertx.core.buffer.Buffer)
     * @see #buffer(io.vertx.reactivex.core.buffer.Buffer)
     */
    static Buffer buffer(String string) {
        return factory.buffer(string);
    }

    /**
     * Same as {@link #buffer(String)} but with the given charset.
     *
     * @return the newly created buffer.
     * @see #buffer(String)
     * @see #buffer(ByteBuf)
     * @see #buffer(io.vertx.core.buffer.Buffer)
     * @see #buffer(io.vertx.reactivex.core.buffer.Buffer)
     */
    static Buffer buffer(String string, String charset) {
        return factory.buffer(string, charset);
    }

    /**
     * Creates a buffer from the specified byte array.
     * <p/>
     * <b>WARN</b>: creating a buffer from a byte array allocates dedicated space in the memory.
     * When creating a {@link Buffer} with the content of another one, it is recommended to use one of the alternative methods:
     * <ul>
     *     <li>{@link #buffer(io.vertx.core.buffer.Buffer)}</li>
     *     <li>{@link #buffer(io.vertx.reactivex.core.buffer.Buffer)}</li>
     *     <li>{@link #buffer(ByteBuf)}</li>
     * </ul>
     *
     * @return the newly created buffer.
     * @see #buffer(ByteBuf)
     * @see #buffer(io.vertx.core.buffer.Buffer)
     * @see #buffer(io.vertx.reactivex.core.buffer.Buffer)
     */
    static Buffer buffer(byte[] bytes) {
        return factory.buffer(bytes);
    }

    /**
     * Appends the specified {@link Buffer} to the current one.
     *
     * @return the current buffer.
     */
    @NonNull
    Buffer appendBuffer(Buffer buff);

    /**
     * Appends the first <code>length</code> bytes of the specified {@link Buffer} to the current one.
     *
     * @return the current buffer.
     */
    Buffer appendBuffer(Buffer buff, int length);

    /**
     * Appends the specified {@link String} with the given charset to the current one.
     *
     * @return the current buffer.
     */
    Buffer appendString(String str, String charset);

    /**
     * Appends the specified {@link String} to the current one.
     *
     * @return the current buffer.
     */
    Buffer appendString(String str);

    /**
     * Returns the {@link String} representation of this current buffer.
     *
     * @return the string representation of this buffer.
     */
    @Override
    String toString();

    /**
     * Returns the {@link String} representation of this current buffer with the given charset.
     *
     * @return the string representation of this buffer with the given charset.
     * @see #toString(Charset)
     */
    String toString(String enc);

    /**
     * Returns the {@link String} representation of this current buffer with the given charset.
     *
     * @return the string representation of this buffer with the given charset.
     * @see #toString(String)
     */
    String toString(Charset enc);

    /**
     * Returns the byte array corresponding to the content of this buffer.
     *
     * @return the byte array of this buffer.
     */
    byte[] getBytes();

    /**
     * Returns the length of this buffer. It corresponds to the number of bytes of this buffer.
     *
     * @return the number of bytes of this buffer.
     */
    int length();

    /**
     * Returns the netty native buffer ({@link ByteBuf}) behind this buffer.
     * This can be particularly useful to access it when wanted to optimise memory consumption and avoid byte array copy to transmit the buffer.
     *
     * @return the netty native buffer.
     */
    ByteBuf getNativeBuffer();

    BufferFactory factory = ServiceLoaderHelper.loadFactory(BufferFactory.class);
}
