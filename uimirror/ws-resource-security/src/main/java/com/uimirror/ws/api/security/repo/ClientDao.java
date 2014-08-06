/*******************************************************************************
 * Copyright (c) 2014 Uimirror.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Uimirror license
 * which accompanies this distribution, and is available at
 * http://www.uimirror.com/legal
 *
 * Contributors:
 * Uimirror Team
 *******************************************************************************/
package com.uimirror.ws.api.security.repo;

import java.util.List;
import java.util.Map;

import com.mongodb.MongoException;
import com.uimirror.ws.api.security.bean.base.Client;

/**
 * <p>This will be interacting with the ouath_db, client collection
 * to get everything about the client</p>
 * 
 * @author Jay
 */
public interface ClientDao {

	public static final String OUATH_DB = "uim_ouath";
	public static final String CLIENT_COLLECTION = "client";
	
	/**
	 * <p>This will save the client into the collection</p>
	 * @param client
	 * @throws IllegalArgumentException in case the input is not valid
	 * @throws MongoException in case of any data base error
	 */
	public void insert(Client client) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>This will find the client by client ID</p>
	 * @param id Client Id
	 * @return {@link Client} if found else null
	 * @throws IllegalArgumentException in case the input is not valid
	 * @throws MongoException in case of any data base error
	 */
	public Client findById(String id) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>This will find the available clients based on some search query</p>
	 * <p>It might be regex query as well</p>
	 * @param query {@link Map} of query objects
	 * @return {@link List} of {@link Client} else null
	 * @throws IllegalArgumentException in case the input is not valid
	 * @throws MongoException in case of any data base error
	 */
	public List<Client> findBy(Map<String, Object> query) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>This will find all the available client</p>
	 * <p>It also includes active as well inactive client</p>
	 * @return {@link List} of {@link Client}
	 * @throws MongoException in case of any data base error
	 */
	public List<Client> findAll() throws MongoException;
	
	/**
	 * <p>This will find all the active Clients available</p>
	 * @return {@link List} Of Active {@link Client}
	 * @throws MongoException in case of any data base error
	 */
	public List<Client> findAllActive() throws MongoException;;
	
	/**
	 * <p>This will find all the inactive client</p>
	 * @return {@link List} of inactive {@link Client}
	 * @throws MongoException in case of any data base error
	 */
	public List<Client> findAllInactive() throws MongoException;;
	
	/**
	 * <p>This will update the client details for the given client id</p>
	 * @param id client id
	 * @param updates a {@link Map} objects of fields and values to be updated
	 * @throws IllegalArgumentException in case the input is not valid
	 * @throws MongoException in case of any data base error
	 */
	public void updateById(String id, Map<String, Object> updates) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>This will update the list of all client which matches the query criteria</p>
	 * @param query a map of fields against which search will be performed.
	 * @param updates a map of fields and value which will be updated
	 * @throws IllegalArgumentException in case the input is not valid
	 * @throws MongoException in case of any data base error
	 */
	public void updateBy(Map<String, Object> query, Map<String, Object> updates) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>This will delete a client by id</p>
	 * @param id cient_id
	 * @throws IllegalArgumentException in case the input is not valid
	 * @throws MongoException in case of any data base error
	 */
	public void deleteById(String id) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>This will delete all the client.</p>
	 * @throws MongoException in case of any data base error
	 */
	public void deleteAll();
	
	/**
	 * <p>This will delete zero or more document(s) which matches the query.</p>
	 * @param query
	 * @throws IllegalArgumentException in case the input is not valid
	 * @throws MongoException in case of any data base error
	 */
	public void deleteBy(Map<String, Object> query) throws IllegalArgumentException, MongoException;
}
