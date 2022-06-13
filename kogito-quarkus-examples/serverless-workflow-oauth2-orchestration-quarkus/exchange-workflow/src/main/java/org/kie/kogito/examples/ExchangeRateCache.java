/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.examples;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * Naive implementation of a cache for holding the exchange rates, meant only for the example purpose.
 */
@ApplicationScoped
public class ExchangeRateCache {

    private final Map<String, Double> exchangeRates = new HashMap<>();

    public Double getRate(String currencyFrom, String currencyTo, LocalDate exchangeDate) {
        return exchangeRates.get(buildKey(currencyFrom, currencyTo, exchangeDate));
    }

    public void pushRate(String currencyFrom, String currencyTo, LocalDate exchangeDate, Double exchangeRate) {
        exchangeRates.put(buildKey(currencyFrom, currencyTo, exchangeDate), exchangeRate);
    }

    private static String buildKey(String currencyFrom, String currencyTo, LocalDate exchangeDate) {
        return currencyFrom + "_" + currencyTo + "_" + exchangeDate;
    }
}
