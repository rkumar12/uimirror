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
package com.uimirror.auth.user.bean;

import java.util.Map;

import com.uimirror.auth.bean.AuthenticatedDetails;
import com.uimirror.core.ActorType;

/**
 * This will hold the authenticated details for the user only.
 * 
 * @author Jay
 */
public class UserAuthenticatedDetails extends CommonAuthenticatedDetails{

	private static final long serialVersionUID = -1849963281712113667L;


	public UserAuthenticatedDetails(String id, long refreshTokenInterval, Map<String, Object> instructions) {
		super(id, refreshTokenInterval, instructions);
	}
	
	public UserAuthenticatedDetails(String id, Map<String, Object> instructions) {
		super(id, instructions);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AuthenticatedDetails#getType()
	 */
	@Override
	public ActorType getType() {
		return ActorType.USER;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.user.bean.CommonAuthenticatedDetails#setRefreshTokenInterval(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AuthenticatedDetails updateRefreshTokenInterval(long refreshTokenInterval) {
		return new UserAuthenticatedDetails((String)this.getId(), refreshTokenInterval, (Map<String, Object>)this.getInstructions());
	}

}
