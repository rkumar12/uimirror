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

import org.springframework.util.Assert;

import com.uimirror.auth.bean.AuthenticatedDetails;

/**
 * Common Authenticated details such as id, refresh token interval and instructions
 * @author Jay
 */
public abstract class CommonAuthenticatedDetails implements AuthenticatedDetails{

	private static final long serialVersionUID = -8218934746138316392L;

	private String id;
	private long refreshTokenInterval;
	private Map<String, Object> instructions;
	
	public CommonAuthenticatedDetails(String id, long refreshTokenInterval, Map<String, Object> instructions) {
		initialize(id, refreshTokenInterval, instructions);
	}
	
	public CommonAuthenticatedDetails(String id, Map<String, Object> instructions) {
		this(id, 0l, instructions);
	}
	
	private void initialize(String id, long refreshTokenInterval, Map<String, Object> instructions){
		Assert.hasText(id, "Principal Id can't be invalid");
		this.id = id;
		this.refreshTokenInterval = refreshTokenInterval;
		this.instructions = instructions;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AuthenticatedDetails#getId()
	 */
	@Override
	public Object getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AuthenticatedDetails#getRefreshTokenInterval()
	 */
	@Override
	public long getRefreshTokenInterval() {
		return this.refreshTokenInterval;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.AuthenticatedDetails#getInstructions()
	 */
	@Override
	public Object getInstructions() {
		return this.instructions;
	}

}
