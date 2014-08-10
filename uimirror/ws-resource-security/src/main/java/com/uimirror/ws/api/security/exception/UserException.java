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

import com.uimirror.core.BaseRunTimeException;

/**
 * <p>RunTime Exception for User Related Information</p>
 * @author Jay
 */
public class UserException extends BaseRunTimeException {

	private static final long serialVersionUID = 7212317895977140327L;

	/**
	 * @param errorCode
	 */
	public UserException(int errorCode) {
		super(errorCode);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public UserException(int errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 * @param cause
	 */
	public UserException(int errorCode, String msg, Throwable cause) {
		super(errorCode, msg, cause);
	}

	/**
	 * @param msg
	 */
	public UserException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public UserException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
