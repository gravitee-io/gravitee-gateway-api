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
package io.gravitee.gateway.api.el;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.gravitee.common.util.LinkedCaseInsensitiveMultiValueMap;
import io.gravitee.common.util.MultiValueMap;
import java.security.Principal;
import java.util.Arrays;
import java.util.Hashtable;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;

/**
 * @author Florent CHAMFROY (florent.chamfroy at graviteesource.com)
 * @author GraviteeSource Team
 *
 * This class allows the TemplateEngine to access SSL attributes using the following syntax: "{#request.ssl.xxx.yyy}", where
 *  - xxx can be client or server
 *  - yyy can be one of {@link BCStyle} ASN1ObjectIdentifier value
 *
 * Examples:
 *   {#request.ssl.client.cn} or {#request.ssl.client.role} or {#request.ssl.server.serialnumber} or {#request.ssl.server.o}
 *
 * Even if the DN of a certificate can contain multiple OU and DC attributes, by default, only the first item will be returned.
 *
 * Examples:
 *   If DN = "CN=John Doe, OU=Test, OU=Test2, OU=Test3, O=ACME, C=US"
 *   Then {#request.ssl.client.ou} => Test
 *
 * To get all values, you may use the <i>attributes</i> field, which is an array
 *     {#request.ssl.client.attributes['ou'][0]} => Test
 *     {#request.ssl.client.attributes['ou'][1]} => Test2
 *     {#request.ssl.client.attributes['ou'][2]} => Test3
 */
public class EvaluableSSLPrincipal {

    private final X500Principal principal;
    private X500Name x500Name;
    private MultiValueMap<String, String> attributes;

    public EvaluableSSLPrincipal(Principal principal) {
        this.principal = (X500Principal) principal;
    }

    /*
     * Default attributes available in X509 SSLSession
     */
    @JsonProperty
    public String getBusinessCategory() {
        return readFirstRDNByType(BCStyle.BUSINESS_CATEGORY);
    }

    @JsonProperty
    public String getC() {
        return readFirstRDNByType(BCStyle.C);
    }

    @JsonProperty
    public String getCn() {
        return readFirstRDNByType(BCStyle.CN);
    }

    @JsonProperty
    public String getCountryOfCitizenship() {
        return readFirstRDNByType(BCStyle.COUNTRY_OF_CITIZENSHIP);
    }

    @JsonProperty
    public String getCountryOfResidence() {
        return readFirstRDNByType(BCStyle.COUNTRY_OF_RESIDENCE);
    }

    @JsonProperty
    public String getDateOfBirth() {
        return readFirstRDNByType(BCStyle.DATE_OF_BIRTH);
    }

    @JsonProperty
    public String getDc() {
        return readFirstRDNByType(BCStyle.DC);
    }

    @JsonProperty
    public String getDescription() {
        return readFirstRDNByType(BCStyle.DESCRIPTION);
    }

    @JsonProperty
    public String getDmdName() {
        return readFirstRDNByType(BCStyle.DMD_NAME);
    }

    @JsonProperty
    public String getDnQualifier() {
        return readFirstRDNByType(BCStyle.DN_QUALIFIER);
    }

    @JsonProperty
    public String getE() {
        return readFirstRDNByType(BCStyle.E);
    }

    @JsonProperty
    public String getEmailAddress() {
        return readFirstRDNByType(BCStyle.EmailAddress);
    }

    @JsonProperty
    public String getGender() {
        return readFirstRDNByType(BCStyle.GENDER);
    }

    @JsonProperty
    public String getGeneration() {
        return readFirstRDNByType(BCStyle.GENERATION);
    }

    @JsonProperty
    public String getGivenname() {
        return readFirstRDNByType(BCStyle.GIVENNAME);
    }

    @JsonProperty
    public String getInitials() {
        return readFirstRDNByType(BCStyle.INITIALS);
    }

    @JsonProperty
    public String getL() {
        return readFirstRDNByType(BCStyle.L);
    }

    @JsonProperty
    public String getName() {
        return readFirstRDNByType(BCStyle.NAME);
    }

    @JsonProperty
    public String getNameAtBirth() {
        return readFirstRDNByType(BCStyle.NAME_AT_BIRTH);
    }

    @JsonProperty
    public String getO() {
        return readFirstRDNByType(BCStyle.O);
    }

    @JsonProperty
    public String getOrganizationIdentifier() {
        return readFirstRDNByType(BCStyle.ORGANIZATION_IDENTIFIER);
    }

    @JsonProperty
    public String getOu() {
        return readFirstRDNByType(BCStyle.OU);
    }

    @JsonProperty
    public String getPlaceOfBirth() {
        return readFirstRDNByType(BCStyle.PLACE_OF_BIRTH);
    }

    @JsonProperty
    public String getPostalAddress() {
        return readFirstRDNByType(BCStyle.POSTAL_ADDRESS);
    }

    @JsonProperty
    public String getPostalCode() {
        return readFirstRDNByType(BCStyle.POSTAL_CODE);
    }

    @JsonProperty
    public String getPseudonym() {
        return readFirstRDNByType(BCStyle.PSEUDONYM);
    }

    @JsonProperty
    public String getRole() {
        return readFirstRDNByType(BCStyle.ROLE);
    }

    @JsonProperty
    public String getSerialnumber() {
        return readFirstRDNByType(BCStyle.SERIALNUMBER);
    }

    @JsonProperty
    public String getSt() {
        return readFirstRDNByType(BCStyle.ST);
    }

    @JsonProperty
    public String getStreet() {
        return readFirstRDNByType(BCStyle.STREET);
    }

    @JsonProperty
    public String getSurname() {
        return readFirstRDNByType(BCStyle.SURNAME);
    }

    @JsonProperty
    public String getT() {
        return readFirstRDNByType(BCStyle.T);
    }

    @JsonProperty
    public String getTelephoneNumber() {
        return readFirstRDNByType(BCStyle.TELEPHONE_NUMBER);
    }

    @JsonProperty
    public String getUid() {
        return readFirstRDNByType(BCStyle.UID);
    }

    @JsonProperty
    public String getUniqueIdentifier() {
        return readFirstRDNByType(BCStyle.UNIQUE_IDENTIFIER);
    }

    @JsonProperty
    public String getUnstructuredAddress() {
        return readFirstRDNByType(BCStyle.UnstructuredAddress);
    }

    /*
     * Other getters
     */
    @JsonProperty
    public String getDn() {
        return principal.getName();
    }

    /**
     * Allow to fetch an attribute from its name (e.g.: CN) or attribute (e.g.: 2.5.4.6).
     * The value returned is always an array of String
     * @return a case insensitive MultiValueMap of all available attributes. Meaning that, if "key" is an existing key, then getAttributes().get("KEY") returns the same value
     */
    @JsonProperty
    public MultiValueMap<String, String> getAttributes() {
        if (attributes == null) {
            attributes = computeSSLAttributes();
        }
        return attributes;
    }

    /**
     *
     * @return true if the principal exists and is defined
     */
    public boolean isDefined() {
        return this.principal != null;
    }

    private String readFirstRDNByType(ASN1ObjectIdentifier objectIdentifier) {
        RDN[] rdns = readRdnsFromPrincipalName(objectIdentifier);
        if (rdns.length > 0) {
            return IETFUtils.valueToString(rdns[0].getFirst().getValue());
        }
        return null;
    }

    private RDN[] readRdnsFromPrincipalName(ASN1ObjectIdentifier objectIdentifier) {
        if (x500Name == null) {
            x500Name = new X500Name(principal.getName());
        }
        return x500Name.getRDNs(objectIdentifier);
    }

    private MultiValueMap<String, String> computeSSLAttributes() {
        class AccessibleBCStyle extends BCStyle {

            Hashtable<ASN1ObjectIdentifier, String> getDefaultSymbols() {
                return this.defaultSymbols;
            }
        }
        AccessibleBCStyle bcStyle = new AccessibleBCStyle();

        LinkedCaseInsensitiveMultiValueMap computedAttributes = new LinkedCaseInsensitiveMultiValueMap<>();

        RDN[] rdns = readAllRdnsFromPrincipalName();
        Arrays
            .stream(rdns)
            .forEach(rdn -> {
                final ASN1ObjectIdentifier type = rdn.getFirst().getType();
                final String value = IETFUtils.valueToString(rdn.getFirst().getValue());
                computedAttributes.add(type.getId(), value);

                final String symbol = bcStyle.getDefaultSymbols().get(type);
                if (symbol != null) {
                    computedAttributes.add(symbol, value);
                }
            });

        return computedAttributes;
    }

    private RDN[] readAllRdnsFromPrincipalName() {
        if (x500Name == null) {
            x500Name = new X500Name(principal.getName());
        }
        return x500Name.getRDNs();
    }
}
