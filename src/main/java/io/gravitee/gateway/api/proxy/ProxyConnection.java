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
package io.gravitee.gateway.api.proxy;

import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.api.http2.HttpFrame;
import io.gravitee.gateway.api.stream.WriteStream;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface ProxyConnection extends WriteStream<Buffer> {

    /**
     * Write custom HTTP Frame to upstream.
     *
     * //TODO: How-to make this specific to HTTP/2 proxy connection only?
     * // This should be handle when the gateway will be able to manage other connectors
     *
     * @param frame
     * @return
     */
    default ProxyConnection writeCustomFrame(HttpFrame frame) {
        return this;
    }

    default ProxyConnection cancel() {
        return this;
    }

    default ProxyConnection exceptionHandler(Handler<Throwable> exceptionHandler) {
        return this;
    }

    default ProxyConnection responseHandler(Handler<ProxyResponse> responseHandler) {
        return this;
    }
}
