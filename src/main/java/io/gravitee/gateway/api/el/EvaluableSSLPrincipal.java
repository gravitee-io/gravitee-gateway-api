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

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;

import javax.security.auth.x500.X500Principal;
import java.security.Principal;
import java.util.Arrays;

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
 * To get all values, you may use the <i>getAll</i> method
 *     {#request.ssl.client.getAll('ou')[0]} => Test
 *     {#request.ssl.client.getAll('ou')[1]} => Test2
 *     {#request.ssl.client.getAll('ou')[2]} => Test3
 */
public class EvaluableSSLPrincipal {
    private final X500Principal principal;
    private X500Name x500Name;

    public EvaluableSSLPrincipal(Principal principal) {
        this.principal = (X500Principal) principal;
    }

    /*
     * Default attributes available in X509 SSLSession
     */
    public String getBusinessCategory() {
        return readFirstRDNByType(BCStyle.BUSINESS_CATEGORY);
    }

    public String getC() {
        return readFirstRDNByType(BCStyle.C);
    }

    public String getCn() {
        return readFirstRDNByType(BCStyle.CN);
    }

    public String getCountryOfCitizenship() {
        return readFirstRDNByType(BCStyle.COUNTRY_OF_CITIZENSHIP);
    }

    public String getCountryOfResidence() {
        return readFirstRDNByType(BCStyle.COUNTRY_OF_RESIDENCE);
    }

    public String getDateOfBirth() {
        return readFirstRDNByType(BCStyle.DATE_OF_BIRTH);
    }

    public String getDc() {
        return readFirstRDNByType(BCStyle.DC);
    }

    public String getDescription() {
        return readFirstRDNByType(BCStyle.DESCRIPTION);
    }

    public String getDmdName() {
        return readFirstRDNByType(BCStyle.DMD_NAME);
    }

    public String getDnQualifier() {
        return readFirstRDNByType(BCStyle.DN_QUALIFIER);
    }

    public String getE() {
        return readFirstRDNByType(BCStyle.E);
    }

    public String getEmailAddress() {
        return readFirstRDNByType(BCStyle.EmailAddress);
    }

    public String getGender() {
        return readFirstRDNByType(BCStyle.GENDER);
    }

    public String getGeneration() {
        return readFirstRDNByType(BCStyle.GENERATION);
    }

    public String getGivenname() {
        return readFirstRDNByType(BCStyle.GIVENNAME);
    }

    public String getInitials() {
        return readFirstRDNByType(BCStyle.INITIALS);
    }

    public String getL() {
        return readFirstRDNByType(BCStyle.L);
    }

    public String getName() {
        return readFirstRDNByType(BCStyle.NAME);
    }

    public String getNameAtBirth() {
        return readFirstRDNByType(BCStyle.NAME_AT_BIRTH);
    }

    public String getO() {
        return readFirstRDNByType(BCStyle.O);
    }

    public String getOrganizationIdentifier() {
        return readFirstRDNByType(BCStyle.ORGANIZATION_IDENTIFIER);
    }

    public String getOu() {
        return readFirstRDNByType(BCStyle.OU);
    }

    public String getPlaceOfBirth() {
        return readFirstRDNByType(BCStyle.PLACE_OF_BIRTH);
    }

    public String getPostalAddress() {
        return readFirstRDNByType(BCStyle.POSTAL_ADDRESS);
    }

    public String getPostalCode() {
        return readFirstRDNByType(BCStyle.POSTAL_CODE);
    }

    public String getPseudonym() {
        return readFirstRDNByType(BCStyle.PSEUDONYM);
    }

    public String getRole() {
        return readFirstRDNByType(BCStyle.ROLE);
    }

    public String getSerialnumber() {
        return readFirstRDNByType(BCStyle.SERIALNUMBER);
    }

    public String getSt() {
        return readFirstRDNByType(BCStyle.ST);
    }

    public String getStreet() {
        return readFirstRDNByType(BCStyle.STREET);
    }

    public String getSurname() {
        return readFirstRDNByType(BCStyle.SURNAME);
    }

    public String getT() {
        return readFirstRDNByType(BCStyle.T);
    }

    public String getTelephoneNumber() {
        return readFirstRDNByType(BCStyle.TELEPHONE_NUMBER);
    }

    public String getUid() {
        return readFirstRDNByType(BCStyle.UID);
    }

    public String getUniqueIdentifier() {
        return readFirstRDNByType(BCStyle.UNIQUE_IDENTIFIER);
    }

    public String getUnstructuredAddress() {
        return readFirstRDNByType(BCStyle.UnstructuredAddress);
    }

    /*
     * Other getters
     */
    public String getDn() {
        return principal.getName();
    }

    /**
     * Allow to fetch an attribute from its name (e.g.: CN) or attribute (e.g.: 2.5.4.6).
     * In case the value is an array, only the first item of this array is returned
     * @param attributeName
     * @return the first value (or the first item if value is an array) in principal DN for the given attribute.
     */
    public String get(String attributeName) {
        final ASN1ObjectIdentifier asn1ObjectIdentifier = BCStyle.INSTANCE.attrNameToOID(attributeName);
        return readFirstRDNByType(asn1ObjectIdentifier);
    }

    /**
     * Allow to fetch an attribute from its name (e.g.: CN) or attribute (e.g.: 2.5.4.6).
     * The value returned is always an array of String
     * @param attributeName
     * @return all values in principal DN for the given attribute.
     */
    public String[] getAll(String attributeName) {
        final ASN1ObjectIdentifier asn1ObjectIdentifier = BCStyle.INSTANCE.attrNameToOID(attributeName);
        return readAllRDNsByType(asn1ObjectIdentifier);
    }

    private String readFirstRDNByType(ASN1ObjectIdentifier objectIdentifier) {
        RDN[] rdns = readRdnsFromPrincipalName(objectIdentifier);
        if (rdns.length > 0) {
            return IETFUtils.valueToString(rdns[0].getFirst().getValue());
        }
        return null;
    }

    private String[] readAllRDNsByType(ASN1ObjectIdentifier objectIdentifier) {
        RDN[] rdns = readRdnsFromPrincipalName(objectIdentifier);
        return Arrays.stream(rdns).map(rdn -> IETFUtils.valueToString(rdn.getFirst().getValue())).toArray(String[]::new);
    }

    private RDN[] readRdnsFromPrincipalName(ASN1ObjectIdentifier objectIdentifier) {
        if (x500Name == null) {
            x500Name = new X500Name(principal.getName());
        }
        return x500Name.getRDNs(objectIdentifier);
    }
}
