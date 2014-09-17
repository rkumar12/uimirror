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
package com.uimirror.core.auth.dao;

import com.uimirror.core.dao.DBException;

/**
 * Store the credentials in the DB 
 * 
 * @author Jay
 */
public interface CredentialsStore {

	/**
	 * <p>Retrieves the credential object in the given document by the provided search criteria</p>
	 * @param type
	 * @return
	 * @throws DBException
	 */
	Object getCredentials(Object identifier) throws DBException;
}
