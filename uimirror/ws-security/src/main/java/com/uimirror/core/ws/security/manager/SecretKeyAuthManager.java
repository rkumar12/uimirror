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
import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.client.Client;
import com.uimirror.core.client.store.ClientStore;
import com.uimirror.core.extra.MapException;
import com.uimirror.core.service.MatcherService;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.sso.AuthenticationManager;
import com.uimirror.sso.auth.APIKeyAuthentication;
import com.uimirror.sso.auth.OAuth2Authentication;
import com.uimirror.sso.auth.provider.AccessTokenProvider;
import com.uimirror.sso.exception.AuthExceptionMapper;
import com.uimirror.sso.exception.AuthenticationException;
import com.uimirror.sso.exception.BadCredentialsException;
import com.uimirror.sso.token.TokenGenerator;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details in principal {@linkplain Authentication}
 * 
 * @author Jay
 */
public class SecretKeyAuthManager implements AuthenticationManager, MatcherService<AccessToken, Client>{
	
	protected static final Logger LOG = LoggerFactory.getLogger(SecretKeyAuthManager.class);
	
	private ClientStore persistedClientStore;
	private AccessTokenProvider persistedAccessTokenProvider;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating Client and generating access token");
		//Step 1- Authenticate and get the previous access Token, which has the user id and client info
		AccessToken token = authenticateAndGetToken(authentication);
		//Step 2- Mark the principal as the previous AccessToken
		Authentication authenticated = getAuthenticatedDetails(authentication, token);
		LOG.info("[END]- Authenticating Client and generating access token");
		return authenticated;
	}

	/**
	 * Gets the previous {@link AccessToken} object from the {@link Authentication}
	 * @param authentication as parameter
	 * @return access Token
	 */
	@SuppressWarnings("unchecked")
	private AccessToken authenticateAndGetToken(Authentication authentication){
		LOG.debug("[START]- Reteriving Secret Code details issued to client eariller");
		Map<String, String> credentials = (Map<String, String>)authentication.getCredentials();
		AccessToken accessToken = getActiveToken(credentials.get(AuthConstants.CLIENT_SECRET_CODE));
		Client client = reteriveActieveClient(authentication.getName());
		if(!StringUtils.hasText(accessToken.getOwner()) 
				|| !TokenType.SECRET.equals(accessToken.getType()) 
				|| !isMatching(accessToken, client) 
				|| !isMatching(client.getSecret(), credentials.get(AuthConstants.CLIENT_SECRET))){
			throw new BadCredentialsException();
		}
		LOG.debug("[END]- Reteriving Secret Code details issued to client eariller");
		return accessToken;
	}
	
	/**
	 * Retrieves the client based on the client apiKey
	 * @param apiKey as parameter
	 * @return client object
	 */
	private Client reteriveActieveClient(String apiKey){
		return persistedClientStore.findActieveClientByApiKey(apiKey);
	}
	
	/**
	 * Retrieves the {@link AccessToken} associated with the given token
	 * @param issuedToken as parameter
	 * @return access token
	 */
	private AccessToken getActiveToken(String issuedToken){
		return persistedAccessTokenProvider.getValid(issuedToken);
	}
	
	/**
	 * This will create a new {@link Authentication} of type {@link APIKeyAuthentication}
	 * where principal is the {@link AccessToken} and details as passed from the authentication.
	 * @param auth as parameter
	 * @param token as parameter
	 * @return authentication object
	 */
	@SuppressWarnings("unchecked")
	private Authentication getAuthenticatedDetails(Authentication auth, AccessToken token) {
		Map<String, Object> details = (Map<String, Object>)auth.getDetails();
		//Issue a new Token with type as AccessToken
		AccessToken newToken = issueANewToken(token, details);
		return new OAuth2Authentication(newToken);
	}
	
	/**
	 * from the previous {@linkplain AccessToken} and provided previous details
	 * it will create a new {@link AccessToken} of the type {@link TokenType#ACCESS}
	 * 
	 * @param prevToken as parameter
	 * @param details as parameter
	 * @return access Token
	 */
	private AccessToken issueANewToken(AccessToken prevToken, Map<String, Object> details){
		Map<String, Object> prevInstructions = prevToken.getInstructions(); 
		long expire = getTokenExpire(prevInstructions);
		return new DefaultAccessToken.TokenBuilder(TokenGenerator.getNewOneWithOutPharse()).
				addClient(prevToken.getClient()).
				addOwner(prevToken.getOwner()).
				addExpire(expire).
				addType(TokenType.ACCESS).
				addScope(prevToken.getScope()).
				addNotes(getNotes(getNotes(details))).
				addInstructions(getInstructions(prevInstructions)).build();
	}
	
	/**
	 * Decide the {@link AccessToken} expire period
	 * @param instructions as parameter
	 * @return long object
	 */
	private long getTokenExpire(Map<String, Object> instructions){
		int standtingInstructions = 0;
		standtingInstructions = (int) instructions.getOrDefault(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, 0);
		standtingInstructions = standtingInstructions == 0 ? AuthConstants.DEFAULT_EXPIRY_INTERVAL : standtingInstructions;
		return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(standtingInstructions);
	}
	
	/**
	 * Get details such as IP and user agent
	 * @param details as parameter
	 * @return map of object
	 */
	private Map<String, Object> getNotes(Map<String, Object> details){
		Map<String, Object> notes = new WeakHashMap<String, Object>(5);
		notes.put(IP, details.get(IP));
		notes.put(USER_AGENT, details.get(USER_AGENT));
		return notes;
	}
	
	/**
	 * Get instructions for this token such as expire interval
	 * @param details as parameter
	 * @return map of object
	 */
	private Map<String, Object> getInstructions(Map<String, Object> prevInstructions){
		Map<String, Object> instructions = new WeakHashMap<String, Object>(5);
		instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, prevInstructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL));
		return instructions;
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
	 * @param src as parameter
	 * @param des as parameter
	 * @return boolean object 
	 */
	public boolean isMatching(String src, String des){
		return src.equalsIgnoreCase(des);
	}

	public void setPersistedClientStore(ClientStore persistedClientStore) {
		this.persistedClientStore = persistedClientStore;
	}

	public void setPersistedAccessTokenProvider(
			AccessTokenProvider persistedAccessTokenProvider) {
		this.persistedAccessTokenProvider = persistedAccessTokenProvider;
	}
	
	

}
