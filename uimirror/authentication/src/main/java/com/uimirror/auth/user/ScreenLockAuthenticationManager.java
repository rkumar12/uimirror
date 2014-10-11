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

import com.uimirror.auth.bean.CredentialType;
import com.uimirror.auth.core.AuthenticationException;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.auth.core.BadCredentialsException;
import com.uimirror.auth.dao.UserCredentialsStore;
import com.uimirror.auth.exception.AuthExceptionMapper;
import com.uimirror.auth.user.bean.UserAuthenticatedDetails;
import com.uimirror.auth.user.bean.UserCredentials;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
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
	private @Autowired UserCredentialsStore userCredentialStore;
	

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use=AuthExceptionMapper.NAME)
	public AuthenticatedDetails authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating User for unlocking the screen");
		UserCredentials usr = getUserCredentialDetails(authentication);
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
	private UserCredentials getUserCredentialDetails(Authentication authentication){
		LOG.debug("[START]- Reteriving User Credentials on basics of the user id");
		UserCredentials userCredentials = handleLoginForm(authentication.getName());
		LOG.debug("[END]- Reteriving User Credentials on basics of the user id");
		return userCredentials;
	}
	
	/**
	 * Handles the {@link CredentialType#LOGINFORM} request
	 * @param userId
	 * @return
	 */
	private UserCredentials handleLoginForm(String userId){
		return new DefaultUserCredentials(getAuthenticationDetails(userId));
	}
	
	/**
	 * Will try to find the user based on the user ID, if no record then throw
	 * an {@link BadCredentialsException}
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getAuthenticationDetails(String userId){
		return (Map<String, Object>)userCredentialStore.getCredentialsByUserName(userId);
	}
	
	/**
	 * <p>This will validate the credentials in the order, authentication should happen</p>
	 * @param auth
	 * @param userCredentials
	 * @return <code>true</code> if successfully authenticated else <code>false</code>
	 * or appropriate {@link AuthenticationException}
	 */
	private boolean doAuthenticate(Authentication auth, UserCredentials userCredentials){
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
	private AuthenticatedDetails getAuthenticatedDetails(Authentication auth, UserCredentials usr) {
		return new UserAuthenticatedDetails((String)usr.getUserId(), usr.getInstructions());
	}

}
