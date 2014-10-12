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
package com.uimirror.auth.user.bean;

import java.util.List;
import java.util.Map;

import com.uimirror.auth.bean.AccountState;
import com.uimirror.auth.bean.AccountStatus;


/**
 * A Basic Credentials object which needs to be implemented by various authentications 
 * instance for example, user has its own authentication schema, a client has its own
 * and so on
 * @author Jay
 */
public interface UserCredentials {

	/**
	 * Specifies the profile id of the user
	 * @return
	 */
	String getProfileId();
	
	/**
	 * Specifies User id for the user
	 * @return
	 */
	List<String> getUserId();
	/**
	 * specifies password for the user/ null for client
	 * @return
	 */
	String getPassword();
	
	/**
	 * specifies password for the user screen lock
	 * @return
	 */
	String getScreenPassword();
	/**
	 * <p>Specifies account state for both user and client</p>
	 * @return
	 */
	AccountState getAccountState();
	/**
	 * Specifies account status for both user and client
	 * @return
	 */
	AccountStatus getAccountStatus();
	/**
	 * if {@link UserCredentials#getPassword()} has value then it will have , else empty
	 * @return
	 */
	String getEncryptionStratgy();
	
	/**
	 * other instructions associated with the account
	 * @return
	 */
	Map<String, Object> getInstructions();

}
