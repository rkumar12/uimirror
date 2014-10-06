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
package com.uimirror.auth.user;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.uimirror.auth.AuthExceptionMapper;
import com.uimirror.auth.user.bean.UserAuthenticatedDetails;
import com.uimirror.core.auth.AuthenticationException;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.AuthenticationValidationService;
import com.uimirror.core.auth.BadCredentialsException;
import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.AuthenticatedDetails;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.BasicCredentials;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.dao.CredentialsStore;
import com.uimirror.core.extra.MapException;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will return the Authenticated Details {@linkplain AuthenticatedDetails}
 * 
 * @author Jay
 */
public class ScreenLockAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(ScreenLockAuthenticationManager.class);
	
	//TODO check and process the proper screen lock password
	private @Autowired CredentialsStore userCredentialStore;
	
	private @Autowired AuthenticationValidationService userAuthenticationValidationService;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public AuthenticatedDetails authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating User for unlocking the screen");
		BasicCredentials usr = getUserCredentialDetails(authentication);
		doAuthenticate(authentication, usr);
		AuthenticatedDetails authDetails = getAuthenticatedDetails(authentication, usr);
		LOG.info("[END]- Authenticating User for unlocking the screen");
		return authDetails;
	}

	/**
	 * Gets the {@link UserCredentials} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	private BasicCredentials getUserCredentialDetails(Authentication authentication){
		LOG.debug("[START]- Reteriving User Credentials on basics of the user id");
		BasicCredentials userCredentials = handleLoginForm(authentication.getName());
		LOG.debug("[END]- Reteriving User Credentials on basics of the user id");
		return userCredentials;
	}
	
	/**
	 * Handles the {@link CredentialType#LOGINFORM} request
	 * @param userId
	 * @return
	 */
	private BasicCredentials handleLoginForm(String userId){
		return new BasicUserCredentials(getAuthenticationDetails(userId));
	}
	
	/**
	 * Will try to find the user based on the user ID, if no record then throw
	 * an {@link BadCredentialsException}
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getAuthenticationDetails(String userId){
		return (Map<String, Object>)userCredentialStore.getCredentials(userId);
	}
	
	/**
	 * <p>This will validate the credentials in the order, authentication should happen</p>
	 * @param auth
	 * @param userCredentials
	 * @return <code>true</code> if successfully authenticated else <code>false</code>
	 * or appropriate {@link AuthenticationException}
	 */
	private boolean doAuthenticate(Authentication auth, BasicCredentials userCredentials){
		return userAuthenticationValidationService.doMatch(userCredentials, auth);
	}
	
	/**
	 * convert the user details into {@linkplain AuthenticatedDetails} that will be used latter
	 * for the {@linkplain AccessToken} generation logic
	 * Accommodate the user basic information into the authenticated details.
	 * {@link AccessToken} else generate a fully phased token.
	 * 
	 * @param auth
	 * @param usr
	 * @return
	 */
	private AuthenticatedDetails getAuthenticatedDetails(Authentication auth, BasicCredentials usr) {
		return new UserAuthenticatedDetails((String)usr.getUserId(), usr.getInstructions());
	}

}
