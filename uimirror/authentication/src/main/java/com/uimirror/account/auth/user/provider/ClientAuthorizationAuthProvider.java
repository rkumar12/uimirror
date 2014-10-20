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

import com.uimirror.account.auth.controller.AccessTokenProvider;
import com.uimirror.account.auth.controller.AuthenticationProvider;
import com.uimirror.account.auth.core.AuthenticationManager;
import com.uimirror.account.auth.dao.ClientStore;
import com.uimirror.account.auth.user.bean.ClientAuthorizationAuthentication;
import com.uimirror.account.auth.user.bean.LoginAuthentication;
import com.uimirror.account.auth.user.processor.AllowAuthorizationClientProcessor;
import com.uimirror.account.auth.user.processor.DenyAuthorizationClientProcessor;
import com.uimirror.account.client.ClientDBFields;
import com.uimirror.account.client.bean.Client;
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
 * <ol>
 * <li>Authenticate the provided token using {@link AuthenticationManager}</li>
 * <li>store the {@link AccessToken} if needed</li>
 * <li>store the user created operation for future reference</li>
 * <li>clean the {@link AccessToken}</li>
 * </ol>
 * 
 * @author Jay
 */
public class ClientAuthorizationAuthProvider implements AuthenticationProvider{

	private static final Logger LOG = LoggerFactory.getLogger(ClientAuthorizationAuthProvider.class);
	private @Autowired AccessTokenProvider persistedAccessTokenProvider;
	private @Autowired AuthenticationManager clientAuthorizationAuthManager;
	private @Autowired BackgroundProcessorFactory<AccessToken, Object> backgroundProcessorFactory;
	private @Autowired ClientStore persistedClientStore;

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
		//Step 4- Clean Authentication
		authenticated = cleanAuthentication(authenticated);
		//Step 5- Modify response to include client if required
		authenticated = includeClientIfRequired(authenticated);
		LOG.debug("[END]- Authenticating, generating and storing token");
		return authenticated;
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
		return clientAuthorizationAuthManager.authenticate(auth);
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
	private Authentication cleanAuthentication(Authentication auth){
		//Clean the Authentication principal
		AccessToken accessToken = (AccessToken)auth.getPrincipal();
		if(TokenType.CANCELLED.equals(accessToken.getType()))
			return null;
		return new ClientAuthorizationAuthentication(accessToken.eraseEsential());
	}
	
	/**
	 * @param authenticated
	 * @return
	 */
	private Authentication includeClientIfRequired(Authentication authenticated) {
		if(authenticated == null)
			return null;
		AccessToken token = (AccessToken)authenticated.getPrincipal();
		if(TokenType.SECRET.equals(token.getType())){
			Map<String, Object> inst = new LinkedHashMap<String, Object>(5);
			Client client = getClient(token.getClient(), ClientDBFields.REDIRECT_URI);
			inst.put(ClientDBFields.REDIRECT_URI, client.getRedirectURI());
			token = token.updateInstructions(null, inst);
			authenticated = new LoginAuthentication(token);
		}
		return authenticated;
	}
	
	/**
	 * Retrieves the client details based on the client ID
	 * @param clientId
	 * @param fields
	 * @return
	 */
	private Client getClient(String clientId, String ... fields){
		Client client = persistedClientStore.findClientById(clientId, fields);
		return client;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.controller.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return ClientAuthorizationAuthentication.class.isAssignableFrom(authentication);
	}

}
