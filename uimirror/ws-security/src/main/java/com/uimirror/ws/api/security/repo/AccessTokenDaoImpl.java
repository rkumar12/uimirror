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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.uimirror.ws.api.security.bean.base.AccessToken;

/**
 * <p>This is the main repo for the access token store</p>
 * @author Jay
 */
public class AccessTokenDaoImpl implements AccessTokenDao {

	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenDaoImpl.class);
	private final DBCollection accesstokenStore;
	
	/**
	 * <p>Initialize the Token store</p>
	 * @param uimAccessTokenStore
	 */
	public AccessTokenDaoImpl(DBCollection uimAccessTokenStore){
		Assert.notNull(uimAccessTokenStore, "Access Token Collection Can't be empty");
		this.accesstokenStore = uimAccessTokenStore;
		this.accesstokenStore.setObjectClass(AccessToken.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#save(com.uimirror.ws.api.security.bean.base.AccessToken)
	 */
	@Override
	public void insert(AccessToken accessToken) {
		LOG.debug("[START] -Trying to save a new Access Token");
		Assert.notEmpty(accessToken, "Empty Acces token can't be saved.");
		accesstokenStore.insert(accessToken);
		LOG.debug("[END] -Trying to save a new Access Token");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#findByToken(java.lang.String)
	 */
	@Override
	public AccessToken findByToken(String token) {
		LOG.debug("[START] -Trying to find a Access token by token id");
		Assert.hasText(token, "Serach can't be performed as token id is not valid");
		//accesstokenStore.findOne(o);
		LOG.debug("[END] -Trying to find a Access token by token id");
		return null;
	}

	@Override
	public List<AccessToken> findAll() {
		LOG.debug("[START] -Trying to find a Access all the available access token");
		DBCursor cursor = accesstokenStore.find();
		//Retrieve max of 20 at a time
		cursor.batchSize(20);
		List<AccessToken> lst = new ArrayList<AccessToken>(20);
		cursor.forEach((token) -> lst.add((AccessToken)token));
		LOG.debug("[END] -Trying to find a Access all the available access token");
		return lst;
	}

	@Override
	public List<AccessToken> findByClientId(String clientId) {
		LOG.debug("[START] -Trying to find list of access token issued for the client by clinet_id");
		
		LOG.debug("[END] -Trying to find list of access token issued for the client by clinet_id");
		return null;
	}

	@Override
	public List<AccessToken> findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markAsExpired(String token) {
		
		
	}

	@Override
	public void deleteByToken(String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByClientId(String clientId) {
		// TODO Auto-generated method stub
		
	}
}
