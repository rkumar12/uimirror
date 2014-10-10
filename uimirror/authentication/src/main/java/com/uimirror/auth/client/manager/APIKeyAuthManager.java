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
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.dao.ClientStore;
import com.uimirror.auth.exception.AuthExceptionMapper;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.extra.MapException;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details in principal {@linkplain Authentication}
 * 
 * @author Jay
 */
public class APIKeyAuthManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(APIKeyAuthManager.class);
	
	private @Autowired ClientStore clientStore;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating Client");
		Client client = getActieveClientDetails(authentication);
		Authentication authenticated = getAuthenticatedDetails(authentication, client);
		LOG.info("[END]- Authenticating User");
		return authenticated;
	}

	/**
	 * Gets the {@link Client} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	private Client getActieveClientDetails(Authentication authentication){
		LOG.debug("[START]- Reteriving acteive Client By api Key");
		Client client = reteriveActiveClient(authentication.getName());
		LOG.debug("[END]- Reteriving acteive Client By api Key");
		return client;
	}
	
	/**
	 * Retrieves the client based on the client apiKey
	 * @param apiKey
	 * @return
	 */
	private Client reteriveActiveClient(String apiKey){
		return clientStore.findActieveClientByApiKey(apiKey);
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

}
