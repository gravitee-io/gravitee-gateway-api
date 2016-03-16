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

import io.gravitee.gateway.api.handler.Handler;

/**
 * @author David BRASSELY (david at gravitee.io)
 * @author GraviteeSource Team
 */
public class SimpleReadWriteStream<T> implements ReadWriteStream<T> {

    protected Handler<T> bodyHandler;
    protected Handler<Void> endHandler;

    @Override
    public SimpleReadWriteStream<T> bodyHandler(Handler<T> bodyHandler) {
        this.bodyHandler = bodyHandler;
        return this;
    }

    @Override
    public SimpleReadWriteStream<T> endHandler(Handler<Void> endHandler) {
        this.endHandler = endHandler;
        return this;
    }

    @Override
    public SimpleReadWriteStream<T> write(T content) {
        if(this.bodyHandler != null) {
            this.bodyHandler.handle(content);
        }

        return this;
    }

    @Override
    public void end() {
        if(this.endHandler != null) {
            this.endHandler.handle(null);
        }
    }
}
