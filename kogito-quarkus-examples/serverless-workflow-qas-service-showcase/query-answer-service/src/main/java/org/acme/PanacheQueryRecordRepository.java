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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PanacheQueryRecordRepository
        implements QueryRecordRepository, PanacheRepository<QueryRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanacheQueryRecordRepository.class);

    @Inject
    UserTransaction transaction;

    @Override
    public void saveOrUpdate(QueryRecord queryRecord) {
        LOGGER.debug("Save or update queryRecord: {}", queryRecord);
        try {
            transaction.begin();
            if (queryRecord.id == null) {
                persist(queryRecord);
            } else {
                getEntityManager().merge(queryRecord);
            }
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An error was produced during queryRecord saving", e);
            rollbackTransaction();
        }
    }

    @Override
    public QueryRecord get(String processInstanceId) {
        return find("processInstanceId", processInstanceId).stream().findFirst().orElse(null);
    }

    @Override
    public List<QueryRecord> getAll() {
        return listAll();
    }

    private void rollbackTransaction() {
        try {
            transaction.rollback();
        } catch (Exception e) {
            LOGGER.error("Transaction rollback failed.", e);
        }
    }
}
