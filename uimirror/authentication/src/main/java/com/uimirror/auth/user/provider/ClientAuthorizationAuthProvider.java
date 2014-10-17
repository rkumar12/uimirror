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
package com.uimirror.auth.user.provider;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.controller.AccessTokenProvider;
import com.uimirror.auth.controller.AuthenticationProvider;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.user.bean.ClientAuthorizationAuthentication;
import com.uimirror.auth.user.processor.AllowAuthorizationClientProcessor;
import com.uimirror.auth.user.processor.DenyAuthorizationClientProcessor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;

/**
 * Validates the {@link Authentication} and populate the authenticated principal
 * with the appropriate token i.e temporal_token/secret_token.
 * 
 * it should adhere the methodology of {@linkplain AuthenticationManager#authenticate(Authentication)}
 * 
 * @author Jay
 */
public class ClientAuthorizationAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(ClientAuthorizationAuthProvider.class);
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;
	private @Autowired AuthenticationManager ClientAuthorizationAuthManager;
	private @Autowired BackgroundProcessorFactory<AccessToken, Object> backgroundProcessorFactory;
	private @Autowired AllowAuthorizationClientProcessor allowAuthorizationClientProcessor;
	private @Autowired DenyAuthorizationClientProcessor denyAuthorizationClientProcessor;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating or refreshing and storing token");
		//Step 1- get the authenticated principal
		Authentication authenticated = getAuthenticatedDetails(authentication);
		AccessToken token = (AccessToken)authenticated.getPrincipal();
		//Step 2- Check for the other process such as if approved, store the authorized reference for future the db etc
		doOtherProcess(token);
		//Step 3-Store the token iff required else cancel
		storeAuthenticatedPrincipal(token);
		//Step 3- Clean Authentication Principal
		LOG.debug("[END]- Authenticating, generating and storing token");
		return cleanAuthentication(authenticated);
	}

	/**
	 * @param token
	 */
	private void doOtherProcess(final AccessToken token) {
		//If any process needs to be taken care such as saving the client
		BackgroundProcessor<AccessToken, Object> processor = null;
		processor = TokenType.SECRET.equals(token.getType()) 
				? backgroundProcessorFactory.getProcessor(AllowAuthorizationClientProcessor.NAME) 
						: backgroundProcessorFactory.getProcessor(DenyAuthorizationClientProcessor.NAME);  
		processor.invoke(token);
	}

	/**
	 * Will interact with the {@link AuthenticationManager} to get the prinicpal of the caller
	 * @param auth
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication auth){
		return ClientAuthorizationAuthManager.authenticate(auth);
	}
	
	/**
	 * Stores the {@link AccessToken} iff its a secret token else forget
	 * @param token
	 */
	private void storeAuthenticatedPrincipal(AccessToken token) {
		if(TokenType.SECRET.equals(token.getType()))
			persistedAccessTokenProvider.store(token);
	}
	
	/**
	 * It should generate a access token and tries to encapsulate the accesstoken to the
	 * {@link Authentication}
	 * 
	 * @param auth an authenticated principal that indicate the principal clearly.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Authentication cleanAuthentication(Authentication auth){
		//Clean the Authentication principal
		AccessToken accessToken = (AccessToken)auth.getPrincipal();
		return new ClientAuthorizationAuthentication(accessToken.eraseEsential(), (Map<String, Object>)auth.getDetails());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return ClientAuthorizationAuthentication.class.isAssignableFrom(authentication);
	}

}
