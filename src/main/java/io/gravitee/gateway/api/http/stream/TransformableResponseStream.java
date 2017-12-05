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

import io.gravitee.common.http.HttpHeaders;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.gateway.api.Response;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.stream.exception.TransformationException;
import io.gravitee.policy.api.PolicyResult;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class TransformableResponseStream extends TransformableSourceStream<Response> {

    TransformableResponseStream(TransformableResponseStreamBuilder builder) {
        super(builder);
    }

    @Override
    public void end() {
        try {
            Buffer content = transform().apply(buffer);

            // Set content length (remove useless transfer encoding header)
            source.headers().remove(HttpHeaders.TRANSFER_ENCODING);
            if (content != null) {
                source.headers().set(HttpHeaders.CONTENT_LENGTH, Integer.toString(content.length()));
            }

            // Set the content-type if settled
            String contentType = contentType();
            if (contentType != null && !contentType.isEmpty()) {
                source.headers().set(HttpHeaders.CONTENT_TYPE, contentType);
            }

            if (content != null) {
                super.flush(content);
            }

            super.end();
        } catch (TransformationException tex) {
            if (policyChain != null) {
                policyChain.streamFailWith(PolicyResult.failure(HttpStatusCode.INTERNAL_SERVER_ERROR_500, tex.getMessage()));
            } else {
                super.end();
            }
        }
    }
}
