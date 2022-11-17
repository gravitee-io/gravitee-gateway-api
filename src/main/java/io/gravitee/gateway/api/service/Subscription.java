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
package io.gravitee.gateway.api.service;

import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subscription {

    private String id;

    private String api;

    private String plan;

    private String application;

    private String clientId;

    private String status;

    private Date startingAt;

    private Date endingAt;

    private String configuration;

    private Map<String, String> metadata;

    private Type type = Type.STANDARD;

    public boolean isTimeValid(long requestTimestamp) {
        Date requestDate = new Date(requestTimestamp);
        return (endingAt == null || endingAt.after(requestDate)) && (startingAt == null || startingAt.before(requestDate));
    }

    public enum Type {
        STANDARD,
        SUBSCRIPTION,
    }
}
