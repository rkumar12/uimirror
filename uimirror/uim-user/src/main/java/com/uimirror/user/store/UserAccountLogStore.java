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

import com.uimirror.core.user.AccountLogs;

/**
 * Store the User account logs in the DB 
 * 
 * @author Jay
 */
public interface UserAccountLogStore {

	/**
	 * Retrieves the document based on the profile id
	 * @param profileId as parameter
	 * @return account logs object
	 */
	AccountLogs getLogsByProfileId(String profileId);

	/**
	 * Delete all the logs present for the user in his log
	 * @param profileId as parameter
	 */
	void deleteByprofileId(String profileId);
	
	/**
	 * @param logs as parameter
	 * @return account Logs
	 */
	AccountLogs store(AccountLogs logs);
}
