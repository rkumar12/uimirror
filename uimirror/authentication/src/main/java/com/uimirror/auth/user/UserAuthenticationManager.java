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
import org.springframework.stereotype.Service;

import com.uimirror.auth.AuthExceptionMapper;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.AuthenticationException;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.BadCredentialsException;
import com.uimirror.core.auth.CredentialType;
import com.uimirror.core.auth.PasswordMatcher;
import com.uimirror.core.auth.dao.CredentialsStore;
import com.uimirror.core.extra.MapException;

/**
 * Implementation of {@link AuthenticationManager#authenticate(Authentication)}
 * to validate the user provided details are valid or not.
 * if valid details, it will issue or re issue the {@link AccessToken}
 * 
 * @author Jay
 */
@Service
public class UserAuthenticationManager implements AuthenticationManager{
	
	protected static final Logger LOG = LoggerFactory.getLogger(UserAuthenticationManager.class);
	
	private @Autowired CredentialsStore userCredentialStore;
	private @Autowired PasswordMatcher passwordMatcher;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(by=AuthExceptionMapper.class)
	public AccessToken authenticate(Authentication authentication) throws AuthenticationException {
		LOG.info("[START]- validating user credentials");
		BasicUserCredentials usr = getUserCredentialDetails(authentication);
		doValidate(authentication, usr);
		LOG.info("[END]- validating user credentials");
		//TODO access token generation logic for latter
		return null;
	}
	
	/**
	 * Gets the {@link UserCredentials} object from the {@link Authentication}
	 * @param authentication
	 * @return
	 */
	private BasicUserCredentials getUserCredentialDetails(Authentication authentication){
		LOG.debug("[START]- Reteriving User Credentials on basics of the user id");
		BasicUserCredentials userCredentials = handleLoginForm(authentication.getName());
		LOG.debug("[END]- Reteriving User Credentials on basics of the user id");
		return userCredentials;
	}
	
	/**
	 * Handles the {@link CredentialType#LOGINFORM} request
	 * @param userId
	 * @return
	 */
	private BasicUserCredentials handleLoginForm(String userId){
		return new BasicUserCredentials(getAuthenticationDetails(userId));
	}
	
	/**
	 * <p>This will validate the credentials in the order, authentication should happen</p>
	 * @param auth
	 * @param userCredentials
	 */
	private void doValidate(Authentication auth, BasicUserCredentials userCredentials){
		new UserAuthenticationValidationService(userCredentials, auth, passwordMatcher);
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

}
