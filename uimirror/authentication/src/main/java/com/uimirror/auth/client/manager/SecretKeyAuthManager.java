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
package com.uimirror.auth.client.manager;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.uimirror.auth.client.Client;
import com.uimirror.auth.client.bean.OAuth2APIKeyAuthentication;
import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.core.BadCredentialsException;
import com.uimirror.auth.dao.ClientStore;
import com.uimirror.auth.exception.AuthExceptionMapper;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.service.MatcherService;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details in principal {@linkplain Authentication}
 * 
 * @author Jay
 */
public class SecretKeyAuthManager implements AuthenticationManager, MatcherService<AccessToken, Client>{
	
	protected static final Logger LOG = LoggerFactory.getLogger(SecretKeyAuthManager.class);
	
	private @Autowired ClientStore clientStore;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating Client");
		Client client = authenticateAndGetClient(authentication);
		Authentication authenticated = getAuthenticatedDetails(authentication, client);
		LOG.info("[END]- Authenticating Client");
		return authenticated;
	}

	/**
	 * Gets the {@link Client} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Client authenticateAndGetClient(Authentication authentication){
		LOG.debug("[START]- Reteriving Secret Code details issued to client eariller");
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		AccessToken accessToken = getActiveToken(credentials.get(AuthConstants.CLIENT_SECRET_CODE));
		Client client = reteriveActieveClient(authentication.getName());
		if(!isMatching(accessToken, client) || !isMatching(client.getSecret(), credentials.get(AuthConstants.CLIENT_SECRET))){
			throw new BadCredentialsException();
		}
		LOG.debug("[END]- Reteriving Secret Code details issued to client eariller");
		return client;
	}
	
	/**
	 * Retrieves the client based on the client apiKey
	 * @param apiKey
	 * @return
	 */
	private Client reteriveActieveClient(String apiKey){
		return clientStore.findActieveClientByApiKey(apiKey);
	}
	
	/**
	 * Retrieves the {@link AccessToken} associated with the given token
	 * @param issuedToken
	 * @return
	 */
	private AccessToken getActiveToken(String issuedToken){
		return persistedAccessTokenProvider.getValidToken(issuedToken);
	}
	
	/**
	 * This will create a new {@link Authentication} of type {@link OAuth2APIKeyAuthentication}
	 * where principal is the {@link Client} and details as passed from the authentication.
	 * @param auth
	 * @param client
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Authentication getAuthenticatedDetails(Authentication auth, Client client) {
		return new OAuth2APIKeyAuthentication(client, (Map<String, Object>)auth.getDetails());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.MatcherService#isMatching(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isMatching(AccessToken src, Client des) {
		boolean matching = Boolean.FALSE;
		if(src != null && des != null){
			if(src.getClient().equals(des.getClientId())){
				matching = Boolean.TRUE;
			}
		}
		return matching;
	}
	
	/**
	 * This will validate the provided credentials and stored credentials are matching or not
	 * @param src
	 * @param des
	 * @return
	 */
	public boolean isMatching(String src, String des){
		return src.equalsIgnoreCase(des);
	}

}
