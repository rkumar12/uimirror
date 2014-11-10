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
 * A flat representation of user data 
 * into temp user collection 
 * 
 * @author Jay
 */
public interface DefaultUserStore {

	String USER_BASIC_INFO_SEQ = "ubis";
	
	DefaultUser getByProfileId(String profileId);
	
	DefaultUser getByEmail(String email);

	void deleteByprofileId(String profileId);
	
	DefaultUser store(DefaultUser user);
}
