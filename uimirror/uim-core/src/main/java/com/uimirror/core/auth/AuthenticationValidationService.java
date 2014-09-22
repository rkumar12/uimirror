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
package com.uimirror.core.auth;

import com.uimirror.core.ValidatorService;

/**
 * Comon validation logic that applies for a account i.e user /client
 * @author Jay
 */
public abstract class AuthenticationValidationService implements ValidatorService{

	private final BasicCredentials credentials;
	
	public AuthenticationValidationService(BasicCredentials credentials) {
		this.credentials = credentials;
	}
	
	/**
	 * <p>Checks for the valid account state</p>
	 * if account is in {@link AccountState#ENABLED} then its valid
	 * all other condition its invalid
	 * @return
	 */
	public boolean isValidState(){
		boolean valid = Boolean.FALSE;
		if(isAccountEnabled())
			valid =  Boolean.TRUE;
		return valid;
	}
	
	/**
	 * CHeks if account is enabled, it checks if state is null or 
	 * {@link AccountState#ENABLED} then <code>true</code> else <code>false</code> 
	 * @return
	 */
	public boolean isAccountEnabled(){
		AccountState state = credentials.getAccountState();
		return (state == null || AccountState.ENABLED.equals(state)) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	/**
	 * Checks if the account is new, i.e not yet verified
	 * i.e {@link AccountState#NEW} and state is not null 
	 * then <code>true</code> else <code>false</code>
	 * @return
	 */
	public boolean isNewAccount(){
		AccountState state = credentials.getAccountState();
		return (state != null && AccountState.NEW.equals(state)) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	/**
	 * <p>Checks for the valid account status</p>
	 * if account is blocked {@link AuthenticationValidationService#isAccountBlocked()}
	 * then status is invalid else valid 
	 * @return
	 */
	public boolean isValidStatus(){
		boolean valid = Boolean.TRUE;
		if(isAccountBlocked())
			valid =  Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Checks if account is in blocked status
	 * if account is in {@link AccountStatus#BLOCKED} then 
	 * its <code>true</code> else <code>false</code>
	 * @return
	 */
	public boolean isAccountBlocked(){
		AccountStatus status = credentials.getAccountStatus();
		return (status != null && AccountStatus.BLOCKED.equals(status)) ? Boolean.TRUE : Boolean.FALSE;
	}

	public BasicCredentials getCredentials() {
		return credentials;
	}


}
