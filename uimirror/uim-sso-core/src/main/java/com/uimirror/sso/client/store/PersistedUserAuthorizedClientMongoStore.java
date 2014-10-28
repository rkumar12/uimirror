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
package com.uimirror.sso.client.store;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mongodb.DBCollection;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.mongo.BasicMongoOperators;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.sso.client.ClientAuthorizedScope;
import com.uimirror.sso.client.UserAuthorizedClient;
import com.uimirror.sso.client.UserAuthorizedClientDBFields;

/**
 * A Basic MONGO store for the User approved clients with the scope.
 * @author Jay
 */
public class PersistedUserAuthorizedClientMongoStore extends AbstractMongoStore<UserAuthorizedClient> implements UserAuthorizedClientStore{

	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	public PersistedUserAuthorizedClientMongoStore(DBCollection collection){
		super(collection, UserAuthorizedClient.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.user.dao.UserAuthorizedClientStore#findAuthrorizedClient(java.lang.String, java.lang.String)
	 */
	@Override
	public UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId) {
		return queryFirstRecord(getClientIdSerachQuery(profileId, clientId), getSingleMatchedArrayProj());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.user.dao.UserAuthorizedClientStore#findAuthrorizedClient(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId, String scope) {
		return queryFirstRecord(getClientIdScopeSerachQuery(profileId, clientId, scope), getSingleMatchedArrayProj());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.user.dao.UserAuthorizedClientStore#getAllAuthroziedClient(java.lang.String)
	 */
	@Override
	public List<UserAuthorizedClient> getAllAuthroziedClient(String profileId) {
		return getByQuery(getIdMap(profileId));
	}
	
	/**
	 * Builds the client Id based search query with the element match key word for the 
	 * array
	 * @param profileId
	 * @param clientId
	 * @return
	 */
	private Map<String, Object> getClientIdSerachQuery(String profileId, String clientId){
		return getClientIdScopeSerachQuery(profileId, clientId, null);                                                                                                                                                                                                                   
	}
	
	/**
	 * This will build the search query for the profile id and client id and scope
	 * @param profileId
	 * @param clientId
	 * @param scope
	 * @return
	 */
	private Map<String, Object> getClientIdScopeSerachQuery(String profileId, String clientId, String scope){
		Map<String, Object> query = getIdMap(profileId);
		Map<String, Object> elementMatchQuery = new LinkedHashMap<String, Object>(5);
		elementMatchQuery.put(BasicMongoOperators.ELEMENT_MATCH, getArrayFindCreteria(clientId, scope));
		query.put(UserAuthorizedClientDBFields.CLIENTS, elementMatchQuery);
		return query;
	}
	
	/**
	 * Builds the array search criteria
	 * @param clientId
	 * @param scope
	 * @return
	 */
	private Map<String, Object> getArrayFindCreteria(String clientId, String scope){
		Map<String, Object> query = new LinkedHashMap<String, Object>(5);
		if(StringUtils.hasText(clientId))
			query.put(UserAuthorizedClientDBFields.CLIENT_ID, clientId);
		if(StringUtils.hasText(scope))
			query.put(UserAuthorizedClientDBFields.SCOPE, scope);
		return query;
	}
	
	/**
	 * Builds the projection query to retrieve the matched array document.
	 * @return
	 */
	private Map<String, Object> getSingleMatchedArrayProj(){
		Map<String, Object> fields = new LinkedHashMap<String, Object>(5);
		fields.put(UserAuthorizedClientDBFields.CLIENT_ARRAY_MATCH_DOC, 1);
		return fields;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.user.dao.UserAuthorizedClientStore#unAuthorizeAClient(java.lang.String, java.lang.String)
	 */
	@Override
	public int unAuthorizeAClient(String profileId, String clientId) {
		Assert.hasText(profileId, "Profile ID requires");
		Assert.hasText(clientId, "Client ID requires");
		Map<String, Object> clientIdMap = getClientIdMap(clientId);
		return updateById(profileId, prepareClientPullMap(clientIdMap));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.user.dao.UserAuthorizedClientStore#authorizeClient(com.uimirror.account.auth.user.UserAuthorizedClient)
	 */
	@Override
	public int authorizeClient(UserAuthorizedClient userAuthorizedClient) {
		Assert.notNull(userAuthorizedClient, "Client To Update can't be invalid");
		return updateByIdInsertWhenNoMatchWithOutMulti(userAuthorizedClient.getProfileId(), prepareClientEachAddToSetMap(convertToListOfClient(userAuthorizedClient.getClients())));
	}
	
	/**
	 * Converts the given list of bean to List of map
	 * @param clients
	 * @return
	 */
	private List<Map<String, Object>> convertToListOfClient(List<ClientAuthorizedScope> clients){
		if(CollectionUtils.isEmpty(clients))
			throw new IllegalArgumentException("Clients sould present");
		List<Map<String, Object>> clientDocs = new ArrayList<Map<String,Object>>(5);
		clients.forEach((client) -> {
			if(client.isValid())
				clientDocs.add(client.toMap());
		});
		return clientDocs;
	}
	
	/**
	 * Creates the query for add to set.
	 * @param clientDocs
	 * @return
	 */
	private Map<String, Object> prepareClientEachAddToSetMap(List<Map<String, Object>> clientDocs){
		Map<String, Object> eachMap = new LinkedHashMap<String, Object>(3);
		//eachMap.put(BasicMongoOperators.EACH, clientDocs);
		eachMap.put(UserAuthorizedClientDBFields.CLIENTS, clientDocs.get(0));
		//Map<String, Object> clientsMap = getClientsMap(clientDocs);
		Map<String, Object> addToSetMap = new LinkedHashMap<String, Object>(3);
		addToSetMap.put(BasicMongoOperators.ADD_TO_SET, eachMap);
		return addToSetMap;
	}
	
	/**
	 * Build a map for Client Id
	 * @param clientId
	 * @return
	 */
	private Map<String, Object> getClientIdMap(String clientId){
		Map<String, Object> fields = new LinkedHashMap<String, Object>(5);
		fields.put(UserAuthorizedClientDBFields.CLIENT_ID, clientId);
		return fields;
	}
	
	/**
	 * Build a map for Client Array Pull
	 * @param map
	 * @return
	 */
	private Map<String, Object> prepareClientPullMap(Map<String, Object> map){
		Map<String, Object> pullClientMap = new LinkedHashMap<String, Object>(5);
		pullClientMap.put(BasicMongoOperators.PULL, getClientsMap(map));
		return pullClientMap;
	}
	
	/**
	 * Gives the clients key and value as the map specified
	 * @param map
	 * @return
	 */
	private Map<String, Object> getClientsMap(Map<String, Object> map){
		Map<String, Object> clientMap = new LinkedHashMap<String, Object>(5);
		clientMap.put(UserAuthorizedClientDBFields.CLIENTS, map);
		return clientMap;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		// TODO Auto-generated method stub
		
	}
	
}
