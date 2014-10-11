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
import com.uimirror.auth.dao.AccessTokenStore;
import com.uimirror.auth.user.bean.LoginFormAuthentication;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.TokenType;

/**
 * Validates the {@link Authentication} and populate the authenticated principal
 * with the appropriate token i.e temporal_token/secret_token. 
 * 
 * @author Jay
 */
public class LoginFormAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(LoginFormAuthProvider.class);
	private @Autowired AuthenticationManager loginFormAuthManager;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.controller.AuthenticationProvider#getAuthenticateToken(com.uimirror.core.auth.bean.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		//Step 1- get the authenticated principal
		Authentication authenticated = getAuthenticatedDetails(authentication);
		AccessToken token = (AccessToken)authenticated.getPrincipal();
		//Step 2- Check Any action required before storing the principal
		doOtherProcess(token);
		//Step 3- Store the token
		storeAuthenticatedPrincipal(token);
		//Step 4- Clean Authentication Principal
		LOG.debug("[END]- Authenticating, generating and storing token");
		return cleanAuthentication(authenticated);
	}

	/**
	 * Will interact with the {@link AuthenticationManager} to get the prinicpal of the caller
	 * @param auth
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication auth){
		return loginFormAuthManager.authenticate(auth);
	}
	
	/**
	 * This will process other process such as in case of otp, it will send mail,
	 * in case user has earlier deactivated the account, so user can reactivated the same.
	 * @param token
	 */
	private void doOtherProcess(AccessToken token){
		if(TokenType._2FA.equals(token.getType()))
			System.out.println("DO the Mail processing");
		Map<String, Object> instructions = token.getInstructions();
		if(instructions.get(AuthConstants.INST_RESTORE_REQUIRED) != null){
			//Process account restore as well
		}
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
	@SuppressWarnings("unchecked")
	private Authentication cleanAuthentication(Authentication auth){
		//Clean the Authentication principal
		AccessToken accessToken = (AccessToken)auth.getPrincipal();
		return new LoginFormAuthentication(accessToken.toResponseMap(), (Map<String, Object>)auth.getDetails());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return LoginFormAuthentication.class.isAssignableFrom(authentication);
	}

}
