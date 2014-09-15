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

import org.springframework.util.Assert;

import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.CredentialType;

/**
 * <p>Contains the user authentication details which will be the principal details of the user</p>
 * <p>implements the {@link Authentication} which has basic user id in raw format and password in encrypted format</p>
 * This details will be erased once authentication process over by {@link AuthenticationManager#authenticate(Authentication)}
 * This expects a user id and password field needs to be populated
 * @author Jay
 */
public final class UserAuthentication implements Authentication{

	private static final long serialVersionUID = -8400229867497592762L;
	private final String id;
	private final String password;
	private final CredentialType credentialType;
	private final Map<String, String> details;

	/**
	 * <p>Populate the Authentication object with the provided information,
	 * which needs to be validated and check if information was correct</p>
	 * @param userId
	 * @param password
	 * @param credentialType
	 * @param details
	 */
	public UserAuthentication(String userId, String password, CredentialType credentialType, Map<String, String> details) {
		Assert.notNull(credentialType, "Credential Type Can't be empty");
		Assert.hasText(userId, "User Identifier can't be empty");
		if(CredentialType.LOGINFORM.equals(credentialType) || CredentialType.SCREENLOCK.equals(credentialType)){
			Assert.hasText(password, "Password Can't be empty");
		}
		this.id = userId;
		this.password = password;
		this.credentialType = credentialType;
		this.details = details;
				
	}

	/** 
	 * This also works as the login id such as user name, might be a cookie token
	 * (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.id;
	}

	/** 
	 * <p>Specifies the type of the authentication being provided such as {@link CredentialType#LOGINFORM}, {@link CredentialType#COOKIE} etc</p>
	 * (non-Javadoc)
	 * @see com.uimirror.core.auth.Authentication#getCredentialType()
	 */
	@Override
	public CredentialType getCredentialType() {
		return this.credentialType;
	}

	/** 
	 * <p>This always be a password might be a screen lock password or login password of the user</p>
	 * (non-Javadoc)
	 * @see com.uimirror.core.auth.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return this.password;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.uimirror.core.auth.Authentication#getDetails()
	 */
	@Override
	public Object getDetails() {
		return this.details;
	}

	/** 
	 * Specifies the authentication schema supported
	 * (non-Javadoc)
	 * @see com.uimirror.core.auth.Authentication#getAuthenticationScheme()
	 */
	@Override
	public Object getAuthenticationScheme() {
		return Authentication.BEARER;
	}

}
