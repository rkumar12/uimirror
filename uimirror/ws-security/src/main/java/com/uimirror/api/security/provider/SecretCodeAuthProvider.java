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
package com.uimirror.api.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.OAuth2Authentication;
import com.uimirror.sso.auth.OAuth2SecretKeyAuthentication;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.auth.provider.AuthenticationProvider;
import com.uimirror.sso.token.store.AccessTokenStore;

/**
 * Validates the {@link Authentication} and populate the authenticated principal
 * with the appropriate token i.e access_token. 
 * 
 * Steps are as below:
 * <ol>
 * <li>Validate the previous token and generate a token</li>
 * <li>store the token</li>
 * <li>clean the prinicpal</li>
 * </ol>
 * 
 * @author Jay
 */
public class SecretCodeAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(SecretCodeAuthProvider.class);
	private AuthenticationManager secretKeyAuthManager;
	private AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		//Step 1- get the authenticated principal
		Authentication authDetails = getAuthenticatedDetails(authentication);
		//Step 2- Store principal
		storeAuthenticatedPrincipal((AccessToken)authDetails.getPrincipal());
		LOG.debug("[END]- Authenticating, generating and storing token");
		//Step 3- generate a authentication which has a access token
		return cleanAuthentication(authDetails);
	}

	/**
	 * Will interact with the {@link AuthenticationManager} to get the principal of the caller
	 * @param auth
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication auth){
		return secretKeyAuthManager.authenticate(auth);
	}
	
	/**
	 * Stores the {@link AccessToken} using {@link AccessTokenStore}
	 * @param token
	 */
	private void storeAuthenticatedPrincipal(AccessToken token){
		persistedAccessTokenProvider.store(token);
	}
	
	/**
	 * It should generate a access token and tries to encapsulate the accesstoken to the
	 * {@link Authentication}
	 * 
	 * @param auth an authenticated principal that indicate the principal clearly.
	 * @return
	 */
	private Authentication cleanAuthentication(Authentication auth){
		//Clean the Authentication principal
		AccessToken accessToken = (AccessToken)auth.getPrincipal();
		return new OAuth2Authentication(accessToken.eraseEsential());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2SecretKeyAuthentication.class.isAssignableFrom(authentication);
	}

	public void setSecretKeyAuthManager(AuthenticationManager secretKeyAuthManager) {
		this.secretKeyAuthManager = secretKeyAuthManager;
	}

	public void setPersistedAccessTokenProvider(
			AccessTokenProvider persistedAccessTokenProvider) {
		this.persistedAccessTokenProvider = persistedAccessTokenProvider;
	}

}
