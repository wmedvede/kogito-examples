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

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "European Central Bank API",
                version = "1.0.1"),
        components = @Components(
                securitySchemes = {
                        @SecurityScheme(securitySchemeName = "eu-central-bank-oauth",
                                type = SecuritySchemeType.OAUTH2,
                                flows = @OAuthFlows(
                                        clientCredentials = @OAuthFlow(
                                                authorizationUrl = "https://example.com/oauth",
                                                tokenUrl = "https://example.com/oauth/token",
                                                scopes = {})))

                }))
public class EUCentralBankApplication extends javax.ws.rs.core.Application {

}
