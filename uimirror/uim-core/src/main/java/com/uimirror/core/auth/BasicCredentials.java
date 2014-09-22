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

import java.util.Map;


/**
 * A Basic Credentials object which needs to be implemented by various authentications 
 * instance for example, user has its own authentication schema, a client has its own
 * and so on
 * @author Jay
 */
public interface BasicCredentials {
	/**
	 * Specifies User id for the user/api key for the client
	 * @return
	 */
	Object getUserId();
	/**
	 * specifies password for the user/ null for client
	 * @return
	 */
	Object getPassword();
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
	 * if {@link BasicCredentials#getPassword()} has value then it will have , else empty
	 * @return
	 */
	Object getEncryptionStratgy();
	
	/**
	 * other instructions associated with the account
	 * @return
	 */
	Map<String, Object> getInstructions();
	
	/**
	 * In case of client it requires the redirect uri to be validated
	 * @return
	 */
	String getRedirectUri();

}
