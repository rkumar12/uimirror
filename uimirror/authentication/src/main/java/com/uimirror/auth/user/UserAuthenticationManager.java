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
import org.springframework.util.Assert;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.AuthenticationException;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.AuthenticationValidationService;
import com.uimirror.core.auth.BadCredentialsException;
import com.uimirror.core.auth.CredentialType;
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
	private @Autowired AuthenticationValidationService userAuthenticationValidationService;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationManager#authenticate(com.uimirror.core.auth.Authentication)
	 */
	@Override
	@MapException(use="AUTHEM")
	public AccessToken authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "Authention Request Object can't be empty");
		LOG.info("[START]- Authenticating User");
		BasicUserCredentials usr = getUserCredentialDetails(authentication);
		doAuthenticate(authentication, usr);
		//Step 2- Generate Access Token 
		AccessToken token = doGenerateToken(authentication, usr);
		LOG.info("[END]- Authenticating User");
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
	 * @return <code>true</code> if successfully authenticated else <code>false</code>
	 * or appropriate {@link AuthenticationException}
	 */
	private boolean doAuthenticate(Authentication auth, BasicUserCredentials userCredentials){
		return userAuthenticationValidationService.doMatch(userCredentials, auth);
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
	 * Checks for any remaining step, if user has opted for the 2FA, returns the interim
	 * {@link AccessToken} else generate a fully phased token.
	 * 
	 * @param auth
	 * @param usr
	 * @return
	 */
	private AccessToken doGenerateToken(Authentication auth, BasicUserCredentials usr) {
		LOG.debug("Checking If User has 2FA enabled");
		if(isOptedFor2FA(usr, auth)){
			LOG.debug("User has 2 Factor Authentication enabled");
		}
		return null;
	}
	
	/**
	 * Checks if user has opted for the 2FA authentication or not,
	 * if 2FA, return <code>true</code> else <code>false</code>
	 * if login type is form and user opted for 2FA then return true else false
	 * @param userCredentials
	 * @return
	 */
	//TODO do the implementation latter
	private boolean isOptedFor2FA(BasicUserCredentials userCredentials, Authentication auth){
		//TODO if login type is form and user opted for 2FA then return true else false
		return Boolean.FALSE;
	}
	
	/**
	 * will generate a new token and send back to the client.
	 * 
	 * @param auth
	 * @param usr
	 * @param partial
	 * @return
	 */
	private AccessToken generateToken(Authentication auth, BasicUserCredentials usr, boolean partial){
		return null;
	}
	

}
