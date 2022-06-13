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
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class used by the Currency Exchange Workflow.
 */
@ApplicationScoped
public class ExchangeWorkflowHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeWorkflowHelper.class);

    private static final Set<String> SUPPORTED_CURRENCIES = new LinkedHashSet<>(Arrays.asList("EUR", "USD", "JPY", "GBP", "CAD", "BRL", "AUD"));

    @Inject
    ExchangeRateCache exchangeRateCache;

    /**
     * Performs the validation of the parameters received by the serverless workflow and tries to get the exchange rate
     * from the cache to optimize and minimize the invocations to the Acme Financial Service.
     */
    public ValidateAndInitializeResult validateAndInitialize(String currencyFrom, String currencyTo, double amount, String exchangeDate) {
        LOGGER.debug("validateAndInitialize, currencyFrom: {}, currencyTo: {}, amount: {}, exchangeDate: {}",
                     currencyFrom, currencyTo, amount, exchangeDate);
        try {
            validateExchangeDate(exchangeDate);
            validateCurrency("currencyFrom", currencyFrom);
            validateCurrency("currencyTo", currencyTo);
        } catch (ValidationException e) {
            return new ValidateAndInitializeResult("ERROR", e.getMessage());
        }
        Double exchangeRate = exchangeRateCache.getRate(currencyFrom, currencyTo, LocalDate.parse(exchangeDate));
        if (exchangeRate != null) {
            LOGGER.debug("Optimization!, the exchangeRate: {} was read from the cache", exchangeRate);
        }
        return new ValidateAndInitializeResult(exchangeRate);
    }

    public ExchangeResult calculateExchange(String currencyFrom, String currencyTo, String exchangeDate, Double amount, Double exchangeRate) {
        LOGGER.debug("calculateExchange, currencyFrom: {}, currencyTo: {}, exchangeDate: {}, amount: {}, exchangeRate: {}",
                     currencyFrom, currencyTo, exchangeDate, amount, exchangeRateCache);
        exchangeRateCache.pushRate(currencyFrom, currencyTo, LocalDate.parse(exchangeDate), exchangeRate);
        return new ExchangeResult(amount * exchangeRate);
    }

    private static void validateExchangeDate(String exchangeDate) throws ValidationException {
        LocalDate date;
        try {
            date = LocalDate.parse(exchangeDate);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid exchangeDate: " + exchangeDate + ", a value in the YYYY-MM-DD must be used");
        }
        LocalDate today = LocalDate.now();
        if (date.isAfter(LocalDate.now())) {
            throw new ValidationException("Invalid exchangeDate: " + exchangeDate + ", a value lower or equal than today: " + today + " must be used");
        }
    }

    private static void validateCurrency(String paramName, String currency) throws ValidationException {
        if (!SUPPORTED_CURRENCIES.contains(currency)) {
            throw new ValidationException("Invalid " + paramName + ": " + currency + ", only the following currencies are supported " + SUPPORTED_CURRENCIES);
        }
    }

    private static class ValidationException extends Exception {

        public ValidationException(String message) {
            super(message);
        }
    }
}
