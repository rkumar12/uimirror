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

import com.uimirror.core.user.BasicInfo;

/**
 * Store the User account Basic Info in the DB 
 * 
 * @author Jay
 */
public interface UserBasicInfoStore {

	/**
	 * Retrieves the document based on the profile id
	 * @param profileId
	 * @return
	 */
	BasicInfo getUserInfoByProfileId(String profileId);
	
	/**
	 * Retrieves the document based on the email
	 * @param profileId
	 * @return
	 */
	BasicInfo getUserInfoByEmail(String email);

	/**
	 * Delete all the logs present for the user in his log
	 * @param profileId
	 */
	void deleteByprofileId(String profileId);
	
	/**
	 * Activates the Basic Info by the Profile ID
	 * @param profileId
	 */
	void activateByprofileId(String profileId);
	
	/**
	 * Deactivate the Basic Info by the Profile ID
	 * @param profileId
	 */
	void blockByprofileId(String profileId);
	
	/**
	 * Enables the Basic Info by the Profile ID
	 * @param profileId
	 */
	void enableByprofileId(String profileId);
	
	/**
	 * Enables the Basic Info by the Profile ID
	 * @param profileId
	 */
	void disableByprofileId(String profileId);
	
	/**
	 * @param user
	 * @return
	 */
	BasicInfo store(BasicInfo user);
}
