/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.metadata.discovery;

import com.google.common.base.Preconditions;

import org.apache.hadoop.metadata.MetadataException;
import org.apache.hadoop.metadata.repository.MetadataRepository;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBackedDiscoveryService implements DiscoveryService {

    private final MetadataRepository repository;

    @Inject
    GraphBackedDiscoveryService(MetadataRepository repository) throws MetadataException {
        this.repository = repository;
    }

    /**
     * Assumes the User is familiar with the persistence structure of the Repository.
     * The given query is run uninterpreted against the underlying Graph Store.
     * The results are returned as a List of Rows. each row is a Map of Key,Value pairs.
     *
     * @param gremlinQuery query in gremlin dsl format
     * @return List of Maps
     * @throws org.apache.hadoop.metadata.MetadataException
     */
    @Override
    public List<Map<String, String>> searchByGremlin(String gremlinQuery) throws MetadataException {
        Preconditions.checkNotNull(gremlinQuery, "gremlin query name cannot be null");
        // simple pass-through
        return repository.searchByGremlin(gremlinQuery);
    }
    
    /**
     * Simple direct graph search and depth traversal.
     * @param searchText is plain text
     * @param prop is the Vertex property to search.
     */
    @Override
    public Map<String, HashMap<String,Map<String,String>>> textSearch(String searchText, int depth, String prop) {
        Preconditions.checkNotNull(searchText, "Invalid argument: \"text\" cannot be null.");
        Preconditions.checkNotNull(prop, "Invalid argument: \"prop\" cannot be null.");
    	
        return repository.textSearch(searchText, depth, prop);
    }
    
    /**
     * Simple graph walker for search interface, which allows following of specific edges only.
     * @param edgesToFollow is a comma-separated-list of edges to follow.
     */
    @Override
    public Map<String, HashMap<String,Map<String,String>>> relationshipWalk(String guid, int depth, String edgesToFollow) {
        Preconditions.checkNotNull(guid, "Invalid argument: \"guid\" cannot be null.");
        Preconditions.checkNotNull(edgesToFollow, "Invalid argument: \"edgesToFollow\" cannot be null.");
        
        return repository.relationshipWalk(guid, depth, edgesToFollow);
    	
    }
    
    
}
