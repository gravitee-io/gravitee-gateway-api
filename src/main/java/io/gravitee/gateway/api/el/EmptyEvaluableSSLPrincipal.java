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

import io.gravitee.common.util.LinkedMultiValueMap;
import io.gravitee.common.util.MultiValueMap;
import java.util.Collections;

public class EmptyEvaluableSSLPrincipal extends EvaluableSSLPrincipal {

    public EmptyEvaluableSSLPrincipal() {
        super(null);
    }

    @Override
    public String getBusinessCategory() {
        return "";
    }

    @Override
    public String getC() {
        return "";
    }

    @Override
    public String getCn() {
        return "";
    }

    @Override
    public String getCountryOfCitizenship() {
        return "";
    }

    @Override
    public String getCountryOfResidence() {
        return "";
    }

    @Override
    public String getDateOfBirth() {
        return "";
    }

    @Override
    public String getDc() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getDmdName() {
        return "";
    }

    @Override
    public String getDnQualifier() {
        return "";
    }

    @Override
    public String getE() {
        return "";
    }

    @Override
    public String getEmailAddress() {
        return "";
    }

    @Override
    public String getGender() {
        return "";
    }

    @Override
    public String getGeneration() {
        return "";
    }

    @Override
    public String getGivenname() {
        return "";
    }

    @Override
    public String getInitials() {
        return "";
    }

    @Override
    public String getL() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getNameAtBirth() {
        return "";
    }

    @Override
    public String getO() {
        return "";
    }

    @Override
    public String getOrganizationIdentifier() {
        return "";
    }

    @Override
    public String getOu() {
        return "";
    }

    @Override
    public String getPlaceOfBirth() {
        return "";
    }

    @Override
    public String getPostalAddress() {
        return "";
    }

    @Override
    public String getPostalCode() {
        return "";
    }

    @Override
    public String getPseudonym() {
        return "";
    }

    @Override
    public String getRole() {
        return "";
    }

    @Override
    public String getSerialnumber() {
        return "";
    }

    @Override
    public String getSt() {
        return "";
    }

    @Override
    public String getStreet() {
        return "";
    }

    @Override
    public String getSurname() {
        return "";
    }

    @Override
    public String getT() {
        return "";
    }

    @Override
    public String getTelephoneNumber() {
        return "";
    }

    @Override
    public String getUid() {
        return "";
    }

    @Override
    public String getUniqueIdentifier() {
        return "";
    }

    @Override
    public String getUnstructuredAddress() {
        return "";
    }

    @Override
    public String getDn() {
        return "";
    }

    @Override
    public MultiValueMap<String, String> getAttributes() {
        return new LinkedMultiValueMap<>(Collections.emptyMap());
    }

    @Override
    public boolean isDefined() {
        return false;
    }
}
