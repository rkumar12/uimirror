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
package com.uimirror.auth.user.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mongodb.DBCollection;
import com.uimirror.auth.user.UserAuthorizedClient;
import com.uimirror.auth.user.UserAuthorizedClientDBFields;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.mongo.BasicMongoOperators;

/**
 * A Basic MONGO store for the User approved clients with the scope.
 * @author Jay
 */
@Repository
public class PersistedUserAuthorizedClientMongoStore extends AbstractMongoStore<UserAuthorizedClient> implements UserAuthorizedClientStore{

	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedUserAuthorizedClientMongoStore(@Qualifier("userAuthorizedClientCol") DBCollection collection){
		super(collection, UserAuthorizedClient.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.user.dao.UserAuthorizedClientStore#findAuthrorizedClient(java.lang.String, java.lang.String)
	 */
	@Override
	public UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId) {
		return queryFirstRecord(getClientIdSerachQuery(profileId, clientId), getSingleMatchedArrayProj());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.user.dao.UserAuthorizedClientStore#findAuthrorizedClient(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UserAuthorizedClient findAuthrorizedClient(String profileId, String clientId, String scope) {
		return queryFirstRecord(getClientIdScopeSerachQuery(profileId, clientId, scope), getSingleMatchedArrayProj());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.user.dao.UserAuthorizedClientStore#getAllAuthroziedClient(java.lang.String)
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
	
}
