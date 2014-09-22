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

import com.uimirror.core.auth.Authentication;
import com.uimirror.core.auth.AuthenticationValidationService;
import com.uimirror.core.auth.BadCredentialsException;
import com.uimirror.core.auth.BasicCredentials;
import com.uimirror.core.auth.DisabledException;
import com.uimirror.core.auth.LockedException;
import com.uimirror.core.auth.PasswordMatcher;

/**
 * Implementing the logic to validate the credentials
 * 
 * @author Jay
 */
public class UserAuthenticationValidationService extends AuthenticationValidationService {

	private final PasswordMatcher passwordMatcher;
	private final Authentication auth;
	/**
	 * @param credentials
	 * @param passwordMatcher
	 */
	public UserAuthenticationValidationService(BasicCredentials credentials, Authentication auth, PasswordMatcher passwordMatcher) {
		super(credentials);
		this.auth = auth;
		this.passwordMatcher = passwordMatcher;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.ValidatorService#validate(java.lang.Object)
	 */
	@Override
	public boolean validate() {
		if(!isValidState())
			throw new DisabledException();
		if(!isValidStatus())
			throw new LockedException();
		if(!isValidpassword())
			throw new BadCredentialsException();
		//As all other passed means its a valid credentials
		return Boolean.TRUE;
	}

	/**
	 * Checks for the password is valid for the given password by the encryption startegy
	 * @return
	 */
	private boolean isValidpassword() {
		BasicCredentials cred = getCredentials();
		String raw = (String)auth.getCredentials();
		String stratagy = (String)cred.getEncryptionStratgy();
		String password = (String)cred.getPassword();
		return passwordMatcher.match(raw, password, stratagy);
	}

}
