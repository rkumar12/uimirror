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

import com.uimirror.core.auth.bean.AccountState;
import com.uimirror.core.auth.bean.AccountStatus;
import com.uimirror.core.auth.bean.BasicCredentials;
import com.uimirror.core.service.ValidatorService;

/**
 * Comon validation logic that applies for a account i.e user /client
 * @author Jay
 */
public interface AuthenticationValidationService extends ValidatorService{

	
	/**
	 * <p>Checks for the valid account state</p>
	 * if account is in {@link AccountState#ENABLED} then its valid
	 * all other condition its invalid
	 * @return
	 */
	public boolean isValidState(BasicCredentials credentials);
	
	/**
	 * CHeks if account is enabled, it checks if state is null or 
	 * {@link AccountState#ENABLED} then <code>true</code> else <code>false</code> 
	 * @return
	 */
	public boolean isAccountEnabled(BasicCredentials credentials);
	
	/**
	 * Checks if the account is new, i.e not yet verified
	 * i.e {@link AccountState#NEW} and state is not null 
	 * then <code>true</code> else <code>false</code>
	 * @return
	 */
	public boolean isNewAccount(BasicCredentials credentials);
	
	/**
	 * <p>Checks for the valid account status</p>
	 * if account is blocked {@link AuthenticationValidationService#isAccountBlocked()}
	 * then status is invalid else valid 
	 * @return
	 */
	public boolean isValidStatus(BasicCredentials credentials);
	
	/**
	 * Checks if account is in blocked status
	 * if account is in {@link AccountStatus#BLOCKED} then 
	 * its <code>true</code> else <code>false</code>
	 * @return
	 */
	public boolean isAccountBlocked(BasicCredentials credentials);


}
