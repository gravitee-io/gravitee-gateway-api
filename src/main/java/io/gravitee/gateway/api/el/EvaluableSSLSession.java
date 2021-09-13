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
package io.gravitee.gateway.api.el;

import org.bouncycastle.asn1.x500.style.BCStyle;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/**
 * @author Florent CHAMFROY (florent.chamfroy at graviteesource.com)
 * @author GraviteeSource Team
 */
public class EvaluableSSLSession {

    private final SSLSession sslSession;

    public EvaluableSSLSession(final SSLSession sslSession) {
        this.sslSession = sslSession;
    }

    public String getClientHost() {
        if (sslSession != null) {
            return sslSession.getPeerHost();
        }
        return null;
    }

    public Integer getClientPort() {
        if (sslSession != null) {
            return sslSession.getPeerPort();
        }
        return null;
    }

    public EvaluableSSLPrincipal getClient() {
        if (sslSession != null) {
            try {
                return new EvaluableSSLPrincipal(sslSession.getPeerPrincipal());
            } catch (SSLPeerUnverifiedException ignored) {

            }
        }
        return null;
    }

    public EvaluableSSLPrincipal getServer() {
        if (sslSession != null) {
            return new EvaluableSSLPrincipal(sslSession.getLocalPrincipal());
        }
        return null;
    }

}
