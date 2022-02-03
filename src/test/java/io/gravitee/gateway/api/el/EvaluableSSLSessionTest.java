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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EvaluableSSLSessionTest {

    private static final String CLIENT_DN =
        "C=AU,ST=Victoria,L=South Melbourne,O=The Legion of the Bouncy Castle,OU=Webserver Team,OU=And another team,CN=www2.connect4.com.au,E=webmaster@connect4.com.au";
    private static final String CLIENT_HOST = "my_domain";
    private static final int CLIENT_PORT = 1234;

    private static final String SERVER_DN =
        "emailAddress=contact@graviteesource.com,CN=localhost,OU=GraviteeSource,O=GraviteeSource,L=Lille,ST=France,C=FR";

    private SSLSession sslSession;
    private X500Principal clientPrincipal;
    private X500Principal serverPrincipal;

    @BeforeEach
    public void init() throws SSLPeerUnverifiedException {
        sslSession = mock(SSLSession.class);
        clientPrincipal = mock(X500Principal.class);
        serverPrincipal = mock(X500Principal.class);

        when(sslSession.getPeerHost()).thenReturn(CLIENT_HOST);
        when(sslSession.getPeerPort()).thenReturn(CLIENT_PORT);
        when(sslSession.getPeerPrincipal()).thenReturn(clientPrincipal);
        when(sslSession.getLocalPrincipal()).thenReturn(serverPrincipal);
        when(clientPrincipal.getName()).thenReturn(CLIENT_DN);
        when(serverPrincipal.getName()).thenReturn(SERVER_DN);
    }

    @Test
    public void shouldGetNoAttributeIfNoSSLSession() {
        EvaluableSSLSession evaluableSSLSession = new EvaluableSSLSession(null);
        assertEquals(EvaluableSSLSession.EMPTY_EVALUABLE_SSL_PRINCIPAL, evaluableSSLSession.getClient());
        assertNull(evaluableSSLSession.getClientHost());
        assertNull(evaluableSSLSession.getClientPort());
        assertEquals(EvaluableSSLSession.EMPTY_EVALUABLE_SSL_PRINCIPAL, evaluableSSLSession.getServer());
    }

    @Test
    public void shouldGetClientAttributes() {
        EvaluableSSLSession evaluableSSLSession = new EvaluableSSLSession(sslSession);

        assertEquals(CLIENT_HOST, evaluableSSLSession.getClientHost());
        assertEquals(CLIENT_PORT, evaluableSSLSession.getClientPort().intValue());

        final EvaluableSSLPrincipal client = evaluableSSLSession.getClient();
        assertNotNull(client);

        final String expectedC = "AU";
        assertEquals(expectedC, client.getC());

        final String expectedST = "Victoria";
        assertEquals(expectedST, client.getSt());

        final String expectedL = "South Melbourne";
        assertEquals(expectedL, client.getL());

        final String expectedO = "The Legion of the Bouncy Castle";
        assertEquals(expectedO, client.getO());

        final String expectedOU = "Webserver Team";
        assertEquals(expectedOU, client.getOu());

        final String expectedCN = "www2.connect4.com.au";
        assertEquals(expectedCN, client.getCn());

        final String expectedE = "webmaster@connect4.com.au";
        assertEquals(expectedE, client.getE());

        assertNull(client.getBusinessCategory());

        assertEquals(CLIENT_DN, client.getDn());
    }

    @Test
    public void shouldGetServerAttributes() {
        EvaluableSSLSession evaluableSSLSession = new EvaluableSSLSession(sslSession);

        final EvaluableSSLPrincipal server = evaluableSSLSession.getServer();
        assertNotNull(server);

        final String expectedC = "FR";
        assertEquals(expectedC, server.getC());

        final String expectedST = "France";
        assertEquals(expectedST, server.getSt());

        final String expectedL = "Lille";
        assertEquals(expectedL, server.getL());

        final String expectedO = "GraviteeSource";
        assertEquals(expectedO, server.getO());

        final String expectedOU = "GraviteeSource";
        assertEquals(expectedOU, server.getOu());

        final String expectedCN = "localhost";
        assertEquals(expectedCN, server.getCn());

        final String expectedE = "contact@graviteesource.com";
        assertEquals(expectedE, server.getEmailAddress());

        assertNull(server.getBusinessCategory());

        assertEquals(SERVER_DN, server.getDn());
    }

    @Test
    public void shouldGetSpecificClientAttribute() {
        EvaluableSSLSession evaluableSSLSession = new EvaluableSSLSession(sslSession);

        final EvaluableSSLPrincipal client = evaluableSSLSession.getClient();
        assertNotNull(client);

        final List<String> expectedOu = Arrays.asList("Webserver Team", "And another team");
        assertEquals(expectedOu, client.getAttributes().get("2.5.4.11"));
        assertEquals(expectedOu, client.getAttributes().get("OU"));
        assertEquals(expectedOu, client.getAttributes().get("ou"));
        assertEquals(expectedOu, client.getAttributes().get("Ou"));
        assertEquals(expectedOu, client.getAttributes().get("oU"));
    }
}
