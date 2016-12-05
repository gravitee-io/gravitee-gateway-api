package io.gravitee.gateway.api.http.stream;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.stream.TransformableStream;

import java.util.function.Function;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class TransformableStreamBuilder<T> {

    protected final T container;
    protected String contentType;
    protected Function<Buffer, Buffer> function;
    protected int contentLength = -1;

    protected TransformableStreamBuilder(T container) {
        this.container = container;
    }

    public TransformableStreamBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public TransformableStreamBuilder contentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public TransformableStreamBuilder transform(Function<Buffer, Buffer> function) {
        this.function = function;
        return this;
    }

    public abstract TransformableStream build();
}
