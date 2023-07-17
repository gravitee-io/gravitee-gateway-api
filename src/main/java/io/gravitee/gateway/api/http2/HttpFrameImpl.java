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
package io.gravitee.gateway.api.http2;

import io.gravitee.gateway.api.buffer.Buffer;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
class HttpFrameImpl implements HttpFrame {

    private final int type;

    private final int flags;

    private final Buffer payload;

    HttpFrameImpl(int type, int flags, Buffer payload) {
        this.type = type;
        this.flags = flags;
        this.payload = payload;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public int flags() {
        return flags;
    }

    @Override
    public Buffer payload() {
        return payload;
    }
}
