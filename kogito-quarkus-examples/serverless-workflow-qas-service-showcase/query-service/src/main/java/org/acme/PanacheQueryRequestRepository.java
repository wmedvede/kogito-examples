/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

package org.acme;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PanacheQueryRequestRepository
        implements QueryRequestRepository,
        PanacheRepository<QueryRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanacheQueryRequestRepository.class);

    @Inject
    UserTransaction transaction;

    @Override
    public void save(QueryRequest queryRequest) {
        LOGGER.debug("Saving queryRequest: {}", queryRequest);
        try {
            transaction.begin();
            queryRequest.id = null;
            persist(queryRequest);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An error was produced during queryRequest saving", e);
            rollbackTransaction();
        }
    }

    @Override
    public void delete(String processInstanceId) {
        LOGGER.debug("Deleting queryRequest, for processInstanceId: {}", processInstanceId);
        try {
            transaction.begin();
            Optional<QueryRequest> request = find("processInstanceId", processInstanceId).stream().findFirst();
            if (request.isPresent()) {
                deleteById(request.get().id);
                flush();
                transaction.commit();
            } else {
                LOGGER.warn("Query request, for processInstancedId: {} was not found in DB", processInstanceId);
            }
        } catch (Exception e) {
            LOGGER.error("An error was produced during queryRequest deletion", e);
            rollbackTransaction();
        }
    }

    @Override
    public List<QueryRequest> getAll() {
        return listAll();
    }

    private void rollbackTransaction() {
        try {
            transaction.rollback();
        } catch (Exception e) {
            LOGGER.warn("Transaction rollback failed.", e);
        }
    }
}
