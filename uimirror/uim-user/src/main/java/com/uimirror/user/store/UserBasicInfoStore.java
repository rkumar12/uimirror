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

import com.uimirror.core.user.BasicInfo;

/**
 * Store the User account Basic Info in the DB 
 * 
 * @author Jay
 */
public interface UserBasicInfoStore {

	/**
	 * Retrieves the document based on the profile id
	 * @param profileId as parameter
	 * @return basicInfo object
	 */
	BasicInfo getUserInfoByProfileId(String profileId);
	
	/**
	 * Retrieves the document based on the email
	 * @param email as parameter
	 * @return basicInfo object
	 */
	BasicInfo getUserInfoByEmail(String email);

	/**
	 * Delete all the logs present for the user in his log
	 * @param profileId as parameter
	 */
	void deleteByprofileId(String profileId);
	
	/**
	 * Activates the Basic Info by the Profile ID
	 * @param profileId as parameter
	 */
	void activateByprofileId(String profileId);
	
	/**
	 * Deactivate the Basic Info by the Profile ID
	 * @param profileId as parameter
	 */
	void blockByprofileId(String profileId);
	
	/**
	 * Enables the Basic Info by the Profile ID
	 * @param profileId as parameter
	 */
	void enableByprofileId(String profileId);
	
	/**
	 * Enables the Basic Info by the Profile ID
	 * @param profileId as parameter
	 */
	void disableByprofileId(String profileId);
	
	/**
	 * @param user as parameter
	 * @return basicInfo object
	 */
	BasicInfo store(BasicInfo user);
}
