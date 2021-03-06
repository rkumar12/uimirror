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
package com.uimirror.core.ws.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.exceptions.db.RecordNotFoundException;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.exception.InvalidTokenException;
import com.uimirror.sso.token.store.AccessTokenStore;

/**
 * Common Mongo DB bridge to persist and retrieve the {@link AccessToken}
 * 
 * @author Jay
 */
public class PersistedAccessTokenProvider implements AccessTokenProvider{

	protected static final Logger LOG = LoggerFactory.getLogger(PersistedAccessTokenProvider.class);
	
	private AccessTokenStore persistedAccessTokenMongoStore;
	

	/**
	 * This will generate a new {@linkplain AccessToken} using
	 * 
	 * @param token as parameter
	 */
	@Override
	public void store(AccessToken token) {
		LOG.debug("[SINGLE]- Generating the AccessToken based on the Authentications");
		persistedAccessTokenMongoStore.store(token);
	}
	
	/**
	 * Will return the valid {@link AccessToken} based on the {@link Authentication}
	 * if the provided details are valid will return the {@linkplain AccessToken} else <code>null</code>
	 * @param token as parameter
	 * @return access token
	 */
	@Override
	public AccessToken getValid(String token){
		LOG.debug("[SINGLE]- Validating the details provided for the accesstoken");
		try{
			return persistedAccessTokenMongoStore.getValid(token);
		}catch(RecordNotFoundException e){
			throw new InvalidTokenException();
		}
	}
	
	/**
	 * Will return the valid {@link AccessToken} based on the {@link Authentication}
	 * if the provided details are valid will return the {@linkplain AccessToken} else <code>null</code>
	 * @param token as parameter
	 * @return access token
	 */
	@Override
	public AccessToken get(String token){
		LOG.debug("[SINGLE]- Validating the details provided for the accesstoken");
		try{
			return persistedAccessTokenMongoStore.get(token);
		}catch(RecordNotFoundException e){
			throw new InvalidTokenException();
		}
	}

	public void setPersistedAccessTokenMongoStore(
			AccessTokenStore persistedAccessTokenMongoStore) {
		this.persistedAccessTokenMongoStore = persistedAccessTokenMongoStore;
	}

}
