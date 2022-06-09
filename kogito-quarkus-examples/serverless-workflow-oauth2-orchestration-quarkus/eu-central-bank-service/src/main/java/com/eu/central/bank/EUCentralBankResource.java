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

package com.eu.central.bank;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import io.quarkus.security.identity.SecurityIdentity;

@Path("central-bank")
public class EUCentralBankResource {

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("reference-rates")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "referenceRates")
    @SecurityRequirement(name = "eu-central-bank-oauth")
    public ReferenceRates getReferenceRates(@QueryParam("date") String date) {

        System.out.println("QUIEN ES:  " + identity.getPrincipal().getName());
        System.out.println("ATTRIBUTES:  " + identity.getAttributes());
        System.out.println("ROLES:  " + identity.getRoles());
        System.out.println("CREDENTIALS:  " + identity.getCredentials());

        List<ReferenceRate> result = new ArrayList<>();
        result.add(new ReferenceRate("USD", 1.0726));
        result.add(new ReferenceRate("GBP", 0.85415));
        result.add(new ReferenceRate("AUD", 1.4842));
        result.add(new ReferenceRate("CAD", 1.3463));
        return new ReferenceRates(result);

        /*
         * 
         * <Cube currency="USD" rate="1.0726"/>
         * <Cube currency="GBP" rate="0.85415"/>
         * <Cube currency="AUD" rate="1.4842"/>
         * <Cube currency="CAD" rate="1.3463"/>
         * 
         * 
         * <Cube currency="JPY" rate="140.16"/>
         * <Cube currency="BGN" rate="1.9558"/>
         * <Cube currency="CZK" rate="24.715"/>
         * <Cube currency="DKK" rate="7.4390"/>
         * <Cube currency="HUF" rate="388.05"/>
         * <Cube currency="PLN" rate="4.5808"/>
         * <Cube currency="RON" rate="4.9424"/>
         * <Cube currency="SEK" rate="10.4520"/>
         * <Cube currency="CHF" rate="1.0320"/>
         * <Cube currency="ISK" rate="138.30"/>
         * <Cube currency="NOK" rate="10.0853"/>
         * <Cube currency="HRK" rate="7.5222"/>
         * <Cube currency="TRY" rate="17.7960"/>
         * <Cube currency="BRL" rate="5.0986"/>
         */
    }
}
