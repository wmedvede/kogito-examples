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

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ApplicationScoped
public class ExchangeWorkflowHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeWorkflowHelper.class);

    public ExchangeResult calculateExchange(String currencyFrom, String currencyTo, double amount, List<JsonNode> rates, JsonNode workflowData) {
        LOGGER.debug("calculateExchange exchange, currencyFrom: {}, currencyTo: {}, amount: {}, rates: {}, workflowData: {}",
                currencyFrom, currencyTo, amount, rates, workflowData);

        //TODO, ver en los Rates
        ObjectNode node = (ObjectNode) workflowData;
        node.remove("rates");
        if (amount <= 10) {
            return new ExchangeResult(1234d);
        } else {
            return new ExchangeResult(null);
        }
    }

    public ExchangeResult calculateExchange2(String currencyFrom, String currencyTo, double amount, double rate) {
        LOGGER.debug("calculateExchange2 exchange, currencyFrom: {}, currencyTo: {}, amount: {}, rate: {}",
                currencyFrom, currencyTo, amount, rate);

        return new ExchangeResult(rate * amount);
    }

    public void cleanUpHelperFields(JsonNode workflowData) {
        LOGGER.debug("cleanUpHelperFields");
        ObjectNode node = (ObjectNode) workflowData;
        node.remove("rates");
    }

    public ExchangeResult exchangeRateFromCache(String currencyFrom, String currencyTo, String exchangeDate) {
        if ("UYP".equals(currencyFrom)) {
            return new ExchangeResult(2d);
        } else {
            return new ExchangeResult(null);
        }
    }
}
