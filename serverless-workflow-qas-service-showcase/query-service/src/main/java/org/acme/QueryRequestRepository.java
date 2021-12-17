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

@ApplicationScoped
public class QueryRequestRepository {

    private final Map<String, QueryServiceResource.QueryRequest> queryRequests = new ConcurrentHashMap<>();

    public List<QueryServiceResource.QueryRequest> find() {
        return new ArrayList<>(queryRequests.values());
    }

    public void save(QueryServiceResource.QueryRequest queryRequest) {
        queryRequests.put(queryRequest.getProcessInstanceId(), queryRequest);
    }

    public void delete(String id) {
        queryRequests.remove(id);
    }
}
