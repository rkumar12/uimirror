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
package com.uimirror.account.user.dao;

import com.uimirror.core.user.DefaultUserAccountLogs;

/**
 * Store the User account logs in the DB 
 * 
 * @author Jay
 */
public interface UserAccountLogStore {

	/**
	 * Retrieves the document based on the profile id
	 * @param profileId
	 * @return
	 */
	DefaultUserAccountLogs getLogsByProfileId(String profileId);

	/**
	 * Delete all the logs present for the user in his log
	 * @param profileId
	 */
	void deleteByprofileId(String profileId);
}
