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
package com.uimirror.ws.api.security.exception;

import com.uimirror.core.exceptions.BaseRunTimeException;

/**
 * <p>RunTime Exception for access Related Information</p>
 * 
 * @author Jay
 */
public class AccessTokenException extends BaseRunTimeException{

	private static final long serialVersionUID = 186619279117724255L;

	/**
	 * @param errorCode
	 * @param msg
	 * @param cause
	 */
	public AccessTokenException(int errorCode, String msg, Throwable cause) {
		super(errorCode, msg, cause);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public AccessTokenException(int errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param errorCode
	 */
	public AccessTokenException(int errorCode) {
		super(errorCode);
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public AccessTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param msg
	 */
	public AccessTokenException(String msg) {
		super(msg);
	}
}
