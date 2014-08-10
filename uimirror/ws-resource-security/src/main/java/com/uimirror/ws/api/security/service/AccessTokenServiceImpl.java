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
package com.uimirror.ws.api.security.service;

import java.time.Clock;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mongodb.MongoException;
import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.bean.base.Client;
import com.uimirror.ws.api.security.bean.base.User;
import com.uimirror.ws.api.security.exception.AccessTokenException;
import com.uimirror.ws.api.security.exception.ErrorConstant;
import com.uimirror.ws.api.security.repo.AccessTokenDao;


/**
 * <p>Common Service Point for the AccessToken details</p>
 * @author Jayaram
 */
//TODO write test cases and create user dao, client Dao and their respective services
public class AccessTokenServiceImpl implements AccessTokenService {

	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenServiceImpl.class);
	private final AccessTokenDao accessTokenDao;
	private ClientService clientService;
	
	public AccessTokenServiceImpl(AccessTokenDao accessTokenDao) throws IllegalStateException{
		if(accessTokenDao == null)
			throw new IllegalStateException("Dao instance can't be empty to avail the service.");
		this.accessTokenDao = accessTokenDao;
	}
	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.AccessTokenService#getAccessTokenDetailsByTokenId(java.lang.String)
	 */
	@Override
	public AccessToken getAccessTokenDetailsByTokenId(String token) throws AccessTokenException, IllegalArgumentException {
		LOG.info("[START]- Getting the access Token details for token");
		Assert.hasText(token, "Access Token Is not valid to retrive token information");
		AccessToken accessToken = null;
		try{
			accessToken = accessTokenDao.findByToken(token);
			if(accessToken == null){
				LOG.debug("[INTERIM]- No Token Found for the token ID: {}", token);
				throw new AccessTokenException(ErrorConstant.TOKEN_NOT_FOUND, String.format("No Token Details for the token Id : %s", token));
			}
			Client client = clientService.getClientById(accessToken.getClientId());
			User usr = null;
			accessToken = accessToken.updateClient(client).updateUser(usr);
			//TODO user is hardcoded remove that once functional done
		}catch(MongoException me){
			LOG.error("[EXCEPTION]- Getting the access Token details for token experiencing some internal issue {}",me);
			throw new AccessTokenException(ErrorConstant.MONGO_ERROR, "Mongo Connection issue", me);
		}
		LOG.info("[END]- Getting the access Token details for token");
		return accessToken;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.AccessTokenService#getActiveAccessTokenDetailsByTokenId(java.lang.String)
	 */
	@Override
	public AccessToken getActiveAccessTokenDetailsByTokenId(String token) throws AccessTokenException, IllegalArgumentException {
		LOG.info("[START]- Getting the valid access Token details for token");
		AccessToken accessToken = getAccessTokenDetailsByTokenId(token);
		//Validating accessToken expires on
		validateToken(accessToken);
		LOG.info("[END]- Getting the valid access Token details for token");
		return accessToken;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.AccessTokenService#getAccessTokenByTokenId(java.lang.String)
	 */
	@Override
	public AccessToken getAccessTokenByTokenId(String token) throws AccessTokenException, IllegalArgumentException {
		LOG.info("[START]- Getting the access Token for token");
		Assert.hasText(token, "Access Token Is not valid to retrive token information");
		AccessToken accessToken = null;
		try{
			accessToken = accessTokenDao.findByToken(token);
		}catch(MongoException me){
			LOG.error("[EXCEPTION]- Getting the access Token details for token experiencing some internal issue {}",me);
			throw new AccessTokenException(ErrorConstant.MONGO_ERROR, "Mongo Connection issue", me);
		}
		if(accessToken == null){
			LOG.debug("[INTERIM]- No Token Found for the token ID: {}", token);
			throw new AccessTokenException(ErrorConstant.TOKEN_NOT_FOUND, String.format("No Token Details for the token Id : %s", token));
		}
		LOG.info("[END]- Getting the access Token for token");
		return accessToken;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.AccessTokenService#getActiveAccessTokenByTokenId(java.lang.String)
	 */
	@Override
	public AccessToken getActiveAccessTokenByTokenId(String token) throws AccessTokenException, IllegalArgumentException {
		LOG.info("[START]- Getting the valid access Token for token");
		AccessToken accessToken = getAccessTokenByTokenId(token);
		//Validating accessToken expires on
		validateToken(accessToken);
		LOG.info("[END]- Getting the valid access Token for token");
		return accessToken;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.AccessTokenService#markTokenAsExpired(java.lang.String)
	 */
	@Override
	public void markTokenAsExpired(String token) throws AccessTokenException, IllegalArgumentException {
		LOG.info("[START]- Expiring a token details by token id");
		Assert.hasText(token, "Token can't be marked as expired as its not a valid token");
		try{
			accessTokenDao.markAsExpired(token);
		}catch(MongoException me){
			LOG.error("[EXCEPTION]- Marking the access Token details as expired for token experiencing some internal issue {}",me);
			throw new AccessTokenException(ErrorConstant.MONGO_ERROR, "Mongo Connection issue", me);
		}
		LOG.info("[END]- Expiring a token details by token id");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.AccessTokenService#expireAllClinetToken(java.lang.String)
	 */
	@Override
	public void expireAllClinetToken(String clientId) throws AccessTokenException, IllegalArgumentException {
		LOG.info("[START]- Expiring a list oftoken details by client id");
		Assert.hasText(clientId, "Token can't be marked as expired as its not a valid client id");
		try{
			accessTokenDao.expireAllTokenForClient(clientId);
		}catch(MongoException me){
			LOG.error("[EXCEPTION]- Marking the access Token details as expired for client experiencing some internal issue {}",me);
			throw new AccessTokenException(ErrorConstant.MONGO_ERROR, "Mongo Connection issue", me);
		}
		LOG.info("[END]- Expiring a token details by client id");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.AccessTokenService#expireAllUserToken(java.lang.String)
	 */
	@Override
	public void expireAllUserToken(String userId)throws AccessTokenException, IllegalArgumentException {
		LOG.info("[START]- Expiring a list oftoken details by user id");
		Assert.hasText(userId, "Token can't be marked as expired as its not a valid user id");
		try{
			accessTokenDao.expireAllTokenForUser(userId);
		}catch(MongoException me){
			LOG.error("[EXCEPTION]- Marking the access Token details as expired for user experiencing some internal issue {}",me);
			throw new AccessTokenException(ErrorConstant.MONGO_ERROR, "Mongo Connection issue", me);
		}
		LOG.info("[END]- Expiring a token details by user id");
	}
	
	/**
	 * <p>This will validate the token by comparing expires on with now</p>
	 * <p>Adds the expires on seconds with token creation time and check if it after now then invalid token</p>
	 * <p>Please note the time zone {@link Clock#systemUTC()} will be used to get current time stamp</p>
	 * @param token
	 * @throws AccessTokenException
	 */
	private void validateToken(AccessToken token) throws AccessTokenException{
		if(token.getTokenCreationDate().plusSeconds(token.getExpiresOn()).isBefore(ZonedDateTime.now(Clock.systemUTC()))){
			LOG.info("[INTERIM]- Access Token for the token: {} has been expired", token);
			throw new AccessTokenException(ErrorConstant.TOKEN_EXPIRED, String.format("Token Id: %s has been expired", token.getName()));
		}
	}
	public ClientService getClientService() {
		return clientService;
	}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
}
