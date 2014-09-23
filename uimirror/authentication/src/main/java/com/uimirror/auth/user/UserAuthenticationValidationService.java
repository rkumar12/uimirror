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

import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.auth.AccountState;
import com.uimirror.core.auth.AccountStatus;
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
public class UserAuthenticationValidationService implements AuthenticationValidationService {

	private final PasswordMatcher passwordMatcher;

	/**
	 * @param credentials
	 */
	@Autowired
	public UserAuthenticationValidationService(PasswordMatcher passwordMatcher) {
		this.passwordMatcher = passwordMatcher;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.ValidatorService#validate(java.lang.Object)
	 */
	@Override
	public boolean validate(Object src) {
		BasicCredentials credentials = (BasicCredentials) src;
		if(!isValidState(credentials))
			throw new DisabledException();
		if(!isValidStatus(credentials))
			throw new LockedException();
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.ValidatorService#doMatch(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean doMatch(Object src, Object des) {
		BasicCredentials credentials = (BasicCredentials) src;
		//First Validate the account state and status
		validate(credentials);
		Authentication auth = (Authentication) des;
		if(!isPasswordMatching(credentials, auth))
			throw new BadCredentialsException();
		
		//As all other passed means its a valid credentials
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationValidationService#isValidState(com.uimirror.core.auth.BasicCredentials)
	 */
	@Override
	public boolean isValidState(BasicCredentials credentials) {
		boolean valid = Boolean.FALSE;
		if(isAccountEnabled(credentials))
			valid =  Boolean.TRUE;
		return valid;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationValidationService#isAccountEnabled(com.uimirror.core.auth.BasicCredentials)
	 */
	@Override
	public boolean isAccountEnabled(BasicCredentials credentials) {
		AccountState state = credentials.getAccountState();
		return (state == null || AccountState.ENABLED.equals(state)) ? Boolean.TRUE : Boolean.FALSE;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationValidationService#isNewAccount(com.uimirror.core.auth.BasicCredentials)
	 */
	@Override
	public boolean isNewAccount(BasicCredentials credentials) {
		AccountState state = credentials.getAccountState();
		return (state != null && AccountState.NEW.equals(state)) ? Boolean.TRUE : Boolean.FALSE;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationValidationService#isValidStatus(com.uimirror.core.auth.BasicCredentials)
	 */
	@Override
	public boolean isValidStatus(BasicCredentials credentials) {
		boolean valid = Boolean.TRUE;
		if(isAccountBlocked(credentials))
			valid =  Boolean.FALSE;
		return valid;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.AuthenticationValidationService#isAccountBlocked(com.uimirror.core.auth.BasicCredentials)
	 */
	@Override
	public boolean isAccountBlocked(BasicCredentials credentials) {
		AccountStatus status = credentials.getAccountStatus();
		return (status != null && AccountStatus.BLOCKED.equals(status)) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	/**
	 * Checks for the password is valid for the given password by the encryption startegy
	 * @return
	 */
	private boolean isPasswordMatching(BasicCredentials credentials, Authentication auth) {
		String raw = (String)auth.getCredentials();
		String stratagy = (String)credentials.getEncryptionStratgy();
		String password = (String)credentials.getPassword();
		return passwordMatcher.match(raw, password, stratagy);
	}

}
