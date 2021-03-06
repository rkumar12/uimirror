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
package com.uimirror.core.ws.security.manager;

import static com.uimirror.core.Constants.IP;
import static com.uimirror.core.Constants.USER_AGENT;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.client.Client;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.APIKeyAuthentication;
import com.uimirror.sso.exception.AuthenticationException;
import com.uimirror.sso.token.TokenGenerator;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details in principal {@linkplain Authentication}
 * 
 * This always generate a token of type {@link TokenType#TEMPORAL}, which will intern will be used by the 
 * internal UIMirror Application to redirect to the login page
 * 
 * Steps are as below
 * <ol>
 * <li>Get Client Details</li>
 * <li>Validate the Client Details</li>
 * <li>Generate a Temporal {@link AccessToken} of type {@link TokenType#TEMPORAL}</li>
 * </ol>
 * 
 * @author Jay
 */
public class APIKeyAuthManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(APIKeyAuthManager.class);
	
	private Processor<Authentication, Client> apiKeyAuthenticateProcessor;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LOG.info("[START]- Authenticating Client");
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		//Step 1-Get the Client Details
		Client client = getAuthenticatedClientDetails(authentication);
		//Step 3- Generate Token
		AccessToken token = generateToken(authentication, client);
		Authentication authenticated = getAuthenticatedDetails(authentication, token);
		LOG.info("[END]- Authenticating User");
		return authenticated;
	}

	/**
	 * Gets the {@link Client} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	private Client getAuthenticatedClientDetails(Authentication authentication){
		LOG.debug("[START]- Authenticate the provided details and reterive client.");
		Client client = apiKeyAuthenticateProcessor.invoke(authentication);
		LOG.debug("[END]- Authenticate the provided details and reterive client.");
		return client;
	}
	
	/**
	 * This will create a new {@link Authentication} of type {@link APIKeyAuthentication}
	 * where principal is the {@link Client} and details as passed from the authentication.
	 * @param auth
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Authentication getAuthenticatedDetails(Authentication auth, AccessToken token) {
		return new APIKeyAuthentication(token, (Map<String, Object>)auth.getDetails());
	}
	
	/**
	 * Generates a {@link AccessToken} based on the provided authentication
	 * @param auth
	 * @param client
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AccessToken generateToken(Authentication auth, Client client){
		Map<String, Object> details = (Map<String, Object>)auth.getDetails();
		return new DefaultAccessToken.TokenBuilder(TokenGenerator.getNewOne()).
				addClient(client.getClientId()).
				addType(TokenType.TEMPORAL).
				addScope(getScope(details)).
				addNotes(getNotes(details)).
				addInstructions(getInstructions()).build();
	}
	
	/**
	 * Get details such as IP and user agent
	 * @param details
	 * @return
	 */
	private Map<String, Object> getNotes(Map<String, Object> details){
		Map<String, Object> notes = new WeakHashMap<String, Object>(5);
		notes.put(IP, details.get(IP));
		notes.put(USER_AGENT, details.get(USER_AGENT));
		return notes;
	}
	
	/**
	 * Creates the instructions for this token, as this token only can be used for the next user authentication only.
	 * 
	 * @return
	 */
	private Map<String, Object> getInstructions(){
		Map<String, Object> instructions = new WeakHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_NEXT_STEP, AuthConstants.INST_NEXT_USER_AUTH);
		return instructions;
	}

	/**
	 * Gets the scope of this token from the caller
	 * @param details
	 * @return
	 */
	private Scope getScope(Map<String, Object> details){
		String scp = (String)details.get(AuthConstants.SCOPE);
		Scope scope = Scope.getEnum(scp) != null ? Scope.getEnum(scp) : Scope.READ;
		return scope;
	}

	public void setApiKeyAuthenticateProcessor(
			Processor<Authentication, Client> apiKeyAuthenticateProcessor) {
		this.apiKeyAuthenticateProcessor = apiKeyAuthenticateProcessor;
	}

}
