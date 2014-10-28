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

import com.uimirror.core.user.DefaultUser;

/**
 * Store the User account Basic Info in the DB 
 * 
 * @author Jay
 */
public interface DefaultUserStore {

	/**
	 * Retrieves the document based on the profile id
	 * @param profileId
	 * @return
	 */
	DefaultUser getUserByProfileId(String profileId);
	
	/**
	 * Retrieves the document based on the email
	 * @param email
	 * @return
	 */
	DefaultUser getUserByEmail(String email);

	/**
	 * Delete all the logs present for the user in his log
	 * @param profileId
	 */
	void deleteByprofileId(String profileId);
	
	/**
	 * @param user
	 * @return
	 */
	DefaultUser store(DefaultUser user);
}
