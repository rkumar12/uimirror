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
package com.uimirror.core.user.store;

import com.uimirror.core.exceptions.db.DBException;
import com.uimirror.core.user.Credentials;

/**
 * Store the credentials in the DB 
 * 
 * @author Jay
 */
public interface UserCredentialsStore {

	Credentials getCredentialsByUserName(String identifier) throws DBException;
	
	Credentials getCredentialsByProfileId(String identifier) throws DBException;
	
	/**
	 * This will mark the account state as enables
	 * @param profileId user's id
	 * @throws DBException if any error or syntax error
	 */
	void enableAccount(String profileId) throws DBException;
	
	Credentials store(Credentials credentials);
}
