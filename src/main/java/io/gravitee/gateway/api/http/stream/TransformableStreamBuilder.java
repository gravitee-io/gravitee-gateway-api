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
package io.gravitee.gateway.api.http.stream;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.stream.TransformableStream;
import io.gravitee.policy.api.PolicyChain;
import java.util.function.Function;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class TransformableStreamBuilder<T> {

    protected final T container;
    protected String contentType;
    protected PolicyChain policyChain;
    protected Function<Buffer, Buffer> transform;
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
        this.transform = function;
        return this;
    }

    public TransformableStreamBuilder chain(PolicyChain policyChain) {
        this.policyChain = policyChain;
        return this;
    }

    public abstract TransformableStream build();
}
