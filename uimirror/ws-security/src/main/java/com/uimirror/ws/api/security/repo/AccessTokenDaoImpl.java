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

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.common.SecurityFieldConstants;

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
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._ID, token);
		AccessToken tokenDetails = (AccessToken)accesstokenStore.findOne(query);
		LOG.debug("[END] -Trying to find a Access token by token id");
		return tokenDetails;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#findAll()
	 */
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

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#findByClientId(java.lang.String)
	 */
	@Override
	public List<AccessToken> findByClientId(String clientId) {
		LOG.debug("[START] -Trying to find list of access token issued for the client by clinet_id");
		Assert.hasText(clientId, "Serach can't be performed as client id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_CLIENT_ID, clientId);
		DBCursor cursor = accesstokenStore.find(query);
		List<AccessToken> lst = new ArrayList<AccessToken>(20);
		cursor.forEach((token) -> lst.add((AccessToken)token));
		LOG.debug("[END] -Trying to find list of access token issued for the client by clinet_id");
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#findByUserId(java.lang.String)
	 */
	@Override
	public List<AccessToken> findByUserId(String userId) {
		LOG.debug("[START] -Trying to find list of access token issued for the User by user_id");
		Assert.hasText(userId, "Serach can't be performed as user id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_USER_ID, userId);
		DBCursor cursor = accesstokenStore.find(query);
		List<AccessToken> lst = new ArrayList<AccessToken>(20);
		cursor.forEach((token) -> lst.add((AccessToken)token));
		LOG.debug("[END] -Trying to find list of access token issued for the User by user_id");
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#markAsExpired(java.lang.String)
	 */
	@Override
	public void markAsExpired(String token) {
		LOG.debug("[START] -Trying to expire a token by ID");
		Assert.hasText(token, "Expiry can't be performed as token id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._ID, token);
		BasicDBObject carrier = new BasicDBObject();
		BasicDBObject set = new BasicDBObject(SecurityFieldConstants._SET, carrier);
		carrier.put(SecurityFieldConstants._CLIENT_ACCESS_EXPIRIES_ON, ZonedDateTime.now(Clock.systemUTC()));
		accesstokenStore.update(query, set);
		LOG.debug("[END] -Trying to expire a token by ID");
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#expireAllTokenForClient(java.lang.String)
	 */
	@Override
	public void expireAllTokenForClient(String clientid) {
		LOG.debug("[START] -Trying to expire a token by Client ID");
		Assert.hasText(clientid, "Expiry can't be performed as client id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_CLIENT_ID, clientid);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_EXPIRIES_ON, BasicDBObjectBuilder.start(SecurityFieldConstants._GRETER, ZonedDateTime.now(Clock.systemUTC())));
		
		BasicDBObject carrier = new BasicDBObject();
		BasicDBObject set = new BasicDBObject(SecurityFieldConstants._SET, carrier);
		carrier.put(SecurityFieldConstants._CLIENT_ACCESS_EXPIRIES_ON, ZonedDateTime.now(Clock.systemUTC()));
		accesstokenStore.update(query, set, Boolean.FALSE, Boolean.TRUE);
		LOG.debug("[END] -Trying to expire a token by Client ID");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#expireAllTokenForUser(java.lang.String)
	 */
	@Override
	public void expireAllTokenForUser(String userId) {
		LOG.debug("[START] -Trying to expire a token by User ID");
		Assert.hasText(userId, "Expiry can't be performed as user id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_USER_ID, userId);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_EXPIRIES_ON, BasicDBObjectBuilder.start(SecurityFieldConstants._GRETER, ZonedDateTime.now(Clock.systemUTC())));
		
		BasicDBObject carrier = new BasicDBObject();
		BasicDBObject set = new BasicDBObject(SecurityFieldConstants._SET, carrier);
		carrier.put(SecurityFieldConstants._CLIENT_ACCESS_EXPIRIES_ON, ZonedDateTime.now(Clock.systemUTC()));
		accesstokenStore.update(query, set, Boolean.FALSE, Boolean.TRUE);
		LOG.debug("[END] -Trying to expire a token by User ID");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#deleteByToken(java.lang.String)
	 */
	@Override
	public void deleteByToken(String token) {
		LOG.debug("[START] -Trying to delete a token by ID");
		Assert.hasText(token, "Delete can't be performed as token id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._ID, token);
		accesstokenStore.remove(query);
		LOG.debug("[END] -Trying to delete a token by ID");
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#deleteByClientId(java.lang.String)
	 */
	@Override
	public void deleteByClientId(String clientId) {
		LOG.debug("[START] -Trying to delete a token by client ID");
		Assert.hasText(clientId, "Delete can't be performed as client id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_CLIENT_ID, clientId);
		accesstokenStore.remove(query);
		LOG.debug("[END] -Trying to delete a token by client ID");
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.repo.AccessTokenDao#deleteByUserId(java.lang.String)
	 */
	@Override
	public void deleteByUserId(String userId) {
		LOG.debug("[START] -Trying to delete a token by User ID");
		Assert.hasText(userId, "Delete can't be performed as user id is not valid");
		DBObject query = new BasicDBObject(4);
		query.put(SecurityFieldConstants._CLIENT_ACCESS_USER_ID, userId);
		accesstokenStore.remove(query);
		LOG.debug("[END] -Trying to delete a token by User ID");
		
	}

}
