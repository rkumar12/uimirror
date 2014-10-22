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

import com.uimirror.core.user.BasicDetails;


/**
 * Store the User account Basic details in the DB 
 * 
 * @author Jay
 */
public interface UserBasicDetailsStore {

	/**
	 * Retrieves the document based on the profile id
	 * @param profileId
	 * @return
	 */
	BasicDetails getUserInfoByProfileId(String profileId);

	/**
	 * Delete details by profile id
	 * @param profileId
	 */
	void deleteByprofileId(String profileId);
	
	/**
	 * @param details
	 */
	BasicDetails store(BasicDetails details);
	
}
