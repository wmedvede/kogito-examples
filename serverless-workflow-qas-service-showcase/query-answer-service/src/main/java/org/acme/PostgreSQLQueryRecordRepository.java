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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.arc.properties.IfBuildProperty;

import static org.acme.PostgreSQLQueryRecordRepository.PERSISTENCE_TYPE_PROPERTY;

/**
 * TODO: provide the real postgresql persistence, by now works the same as the InMemoryRecordRepository
 */
@IfBuildProperty(name = PERSISTENCE_TYPE_PROPERTY, stringValue = "postgresql")
@ApplicationScoped
public class PostgreSQLQueryRecordRepository implements QueryRecordRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSQLQueryRecordRepository.class);

    public static final String PERSISTENCE_TYPE_PROPERTY = "kogito.persistence.type";

    private final Map<String, QueryRecord> queryRecordMap = new ConcurrentHashMap<>();

    @Override
    public void save(QueryRecord queryRecord) {
        LOGGER.debug("Saving query record: {}", queryRecord);
        queryRecordMap.put(queryRecord.getId(), queryRecord);
    }

    @Override
    public QueryRecord get(String id) {
        return queryRecordMap.get(id);
    }

    @Override
    public List<QueryRecord> find() {
        return new ArrayList<>(queryRecordMap.values());
    }
}
