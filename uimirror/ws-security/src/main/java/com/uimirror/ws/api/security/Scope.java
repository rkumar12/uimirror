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
package com.uimirror.ws.api.security;

import com.uimirror.ws.api.security.ReadWriteScope;

/**
 * <p>This defines the scope for the accesstoken.</p>
 * It specifies the client has access to which application and what type of access he has.
 * 
 * @author Jay
 *
 */
public final class Scope {
	
	private final int applicationCode;
	private final ReadWriteScope readWriteScope;
	
	public Scope(int application, ReadWriteScope readWriteScope) {
		if(application <= 0)
			throw new IllegalArgumentException("Not a Valid Appplication Identifier.");
		if(readWriteScope == null)
			throw new IllegalArgumentException("Not a Valid Scope.");
		this.applicationCode = application;
		this.readWriteScope = readWriteScope;
	}
	
	public Scope(int application, String readWriteScope) {
		if(application <= 0)
			throw new IllegalArgumentException("Not a Valid Appplication Identifier.");
		this.applicationCode = application;
		this.readWriteScope = ReadWriteScope.getEnum(readWriteScope);
	}

	public int getApplicationName() {
		return applicationCode;
	}

	public ReadWriteScope getReadWriteScope() {
		return readWriteScope;
	}

	@Override
	public String toString() {
		return "Scope [applicationCode=" + applicationCode
				+ ", readWriteScope=" + readWriteScope + "]";
	}

}
