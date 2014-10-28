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
package com.uimirror.user.store;

import com.uimirror.core.dao.DBException;
import com.uimirror.core.user.Credentials;

/**
 * Store the credentials in the DB 
 * 
 * @author Jay
 */
public interface UserCredentialsStore {

	/**
	 * <p>Retrieves the credential object in the given document by the provided search criteria</p>
	 * @param type
	 * @return
	 * @throws DBException
	 */
	Credentials getCredentialsByUserName(String identifier) throws DBException;
	
	/**
	 * Retrieves the document by id
	 * @param identifier
	 * @return
	 * @throws DBException
	 */
	Credentials getCredentialsByProfileId(String identifier) throws DBException;
	
	/**
	 * This will change the account state as enabled
	 * @param profileId
	 * @return
	 * @throws DBException
	 */
	int enableAccount(String profileId) throws DBException;
	
	/**
	 * @param credentials
	 * @return
	 */
	Credentials store(Credentials credentials);
}