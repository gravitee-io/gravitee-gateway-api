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
package io.gravitee.gateway.reactive.api.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContextAttributes {

    public static final String ATTR_PREFIX = "gravitee.attribute.";
    public static final String ATTR_ENVIRONMENT = ATTR_PREFIX + "environment";
    public static final String ATTR_ORGANIZATION = ATTR_PREFIX + "organization";
    public static final String ATTR_USER_ROLES = ATTR_PREFIX + "user.roles";
    public static final String ATTR_USER = ATTR_PREFIX + "user";
    public static final String ATTR_QUOTA_RESET_TIME = ATTR_PREFIX + "quota.reset.time";
    public static final String ATTR_QUOTA_LIMIT = ATTR_PREFIX + "quota.limit";
    public static final String ATTR_QUOTA_REMAINING = ATTR_PREFIX + "quota.remaining";
    public static final String ATTR_QUOTA_COUNT = ATTR_PREFIX + "quota.count";
    public static final String ATTR_REQUEST_ENDPOINT = ATTR_PREFIX + "request.endpoint";
    public static final String ATTR_REQUEST_ENDPOINT_OVERRIDE = ATTR_PREFIX + "request.endpoint.override";
    public static final String ATTR_REQUEST_METHOD = ATTR_PREFIX + "request.method";
    public static final String ATTR_REQUEST_ORIGINAL_URL = ATTR_PREFIX + "request.original-url";
    public static final String ATTR_PLAN = ATTR_PREFIX + "plan";
    public static final String ATTR_SUBSCRIPTION_ID = ATTR_PREFIX + "user-id";
    public static final String ATTR_API_DEPLOYED_AT = ATTR_PREFIX + "api.deployed-at";
    public static final String ATTR_API = ATTR_PREFIX + "api";
    public static final String ATTR_API_NAME = ATTR_PREFIX + "api.name";
    public static final String ATTR_APPLICATION = ATTR_PREFIX + "application";
    public static final String ATTR_RESOLVED_PATH = ATTR_PREFIX + "resolved-path";
    public static final String ATTR_CONTEXT_PATH = ATTR_PREFIX + "context-path";

    public static final String ATTR_MAPPED_PATH = ATTR_PREFIX + "mapped-path";
}
