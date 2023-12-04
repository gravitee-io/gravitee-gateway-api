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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

public class SubscriptionTest {

    @Test
    public void isTimeValid_should_return_false_cause_not_yet_started() {
        Subscription subscription = new Subscription();
        subscription.setStartingAt(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
        subscription.setEndingAt(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)));

        assertFalse(subscription.isTimeValid(Date.from(Instant.now()).getTime()));
    }

    @Test
    public void isTimeValid_should_return_false_cause_already_expired() {
        Subscription subscription = new Subscription();
        subscription.setStartingAt(Date.from(Instant.now().minus(2, ChronoUnit.DAYS)));
        subscription.setEndingAt(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)));

        assertFalse(subscription.isTimeValid(Date.from(Instant.now()).getTime()));
    }

    @Test
    public void isTimeValid_should_return_true_cause_between_start_and_end() {
        Subscription subscription = new Subscription();
        subscription.setStartingAt(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)));
        subscription.setEndingAt(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));

        assertTrue(subscription.isTimeValid(Date.from(Instant.now()).getTime()));
    }

    @Test
    public void isTimeValid_should_return_true_with_null_start_date() {
        Subscription subscription = new Subscription();
        subscription.setStartingAt(null);
        subscription.setEndingAt(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));

        assertTrue(subscription.isTimeValid(Date.from(Instant.now()).getTime()));
    }

    @Test
    public void isTimeValid_should_return_true_with_null_end_date() {
        Subscription subscription = new Subscription();
        subscription.setStartingAt(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)));
        subscription.setEndingAt(null);

        assertTrue(subscription.isTimeValid(Date.from(Instant.now()).getTime()));
    }
}