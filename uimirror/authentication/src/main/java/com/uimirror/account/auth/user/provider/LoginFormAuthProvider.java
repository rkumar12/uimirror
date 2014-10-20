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
package com.uimirror.account.auth.user.provider;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.client.Client;
import com.uimirror.account.auth.client.ClientDBFields;
import com.uimirror.account.auth.controller.AccessTokenProvider;
import com.uimirror.account.auth.controller.AuthenticationProvider;
import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.account.auth.core.processor.OTPMailProcessor;
import com.uimirror.account.auth.dao.AccessTokenStore;
import com.uimirror.account.auth.dao.ClientStore;
import com.uimirror.account.auth.user.bean.LoginAuthentication;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;

/**
 * The step of operations for this processor is defined as below:
 * <ol>
 * <li>Authenticate the provided token and client user name and password using {@link AuthenticationManager}</li>
 * <li>Check for other operations {@link AccessToken} such as OTP authentication or client details needs to be fetched</li>
 * <li>store the {@link AccessToken}</li>
 * <li>Check if any other process such as OTP mail needs to send</li>
 * <li>clean the {@link AccessToken}</li>
 * <li>include {@link Client} in the {@link AccessToken} if required</li>
 * </ol> 
 * 
 * @author Jay
 */
public class LoginFormAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(LoginFormAuthProvider.class);
	private @Autowired AuthenticationManager loginFormAuthManager;
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;
	private @Autowired BackgroundProcessorFactory<AccessToken, Object> backgroundProcessorFactory;
	private @Autowired ClientStore persistedClientStore;
	
	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.debug("[START]- Authenticating, generating and storing token");
		//Step 1- get the authenticated principal
		Authentication authenticated = getAuthenticatedDetails(authentication);
		AccessToken token = (AccessToken)authenticated.getPrincipal();
		//Step 2- Store the token
		storeAuthenticatedPrincipal(token);
		//Step 3- Check Any action required before storing the principal
		doOtherProcess(token);
		//Step 4- Clean Authentication
		authenticated = cleanAuthentication(authenticated);
		//Step 5- Modify response to include client if required
		authenticated = includeClientIfRequired(authenticated);
		LOG.debug("[END]- Authenticating, generating and storing token");
		return authenticated;
	}

	/**
	 * Will interact with the {@link AuthenticationManager} to get the principal of the caller
	 * @param auth
	 * @return
	 */
	private Authentication getAuthenticatedDetails(Authentication auth){
		return loginFormAuthManager.authenticate(auth);
	}
	
	/**
	 * Stores the {@link AccessToken} using {@link AccessTokenStore}
	 * @param token
	 */
	private void storeAuthenticatedPrincipal(AccessToken token){
		persistedAccessTokenProvider.store(token);
	}
	
	/**
	 * This will process other process such as in case of otp, it will send mail,
	 * in case user has earlier deactivated the account, so user can reactivated the same.
	 * @param token
	 */
	private void doOtherProcess(AccessToken token){
		if(TokenType._2FA.equals(token.getType()))
			backgroundProcessorFactory.getProcessor(OTPMailProcessor.NAME).invoke(token);
	}
	
	/**
	 * Includes the CLient details into the token as it will be required to authorize
	 * @param token
	 * @return
	 */
	private Authentication includeClientIfRequired(Authentication auth) {
		AccessToken token = (AccessToken)auth.getPrincipal();
		if(TokenType.USER_PERMISSION.equals(token.getType())){
			Map<String, Object> inst = new LinkedHashMap<String, Object>(5);
			Client client = getClient(token.getClient(), ClientDBFields.NAME);
			inst.put(ClientDBFields.NAME, client.getName());
			inst.put(AuthConstants.SCOPE, token.getScope().getScope());
			token = token.updateInstructions(null, inst);
			auth = new LoginAuthentication(token);
		}else if(TokenType.SECRET.equals(token.getType())){
			Map<String, Object> inst = new LinkedHashMap<String, Object>(5);
			Client client = getClient(token.getClient(), ClientDBFields.REDIRECT_URI);
			inst.put(ClientDBFields.REDIRECT_URI, client.getRedirectURI());
			token = token.updateInstructions(null, inst);
			auth = new LoginAuthentication(token);
		}
		return auth;
	}
	
	/**
	 * Retrieves the client details based on the client ID
	 * @param clientId
	 * @return
	 */
	private Client getClient(String clientId, String ... fields){
		Client client = persistedClientStore.findClientById(clientId, ClientDBFields.NAME);
		return client;
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
		return new LoginAuthentication(accessToken.eraseEsential());
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return LoginAuthentication.class.isAssignableFrom(authentication);
	}

}
