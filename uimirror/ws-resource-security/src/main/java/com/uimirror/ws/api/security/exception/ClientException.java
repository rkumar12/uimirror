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
 * <p>RunTime Exception for client Related Information</p>
 * @author Jay
 */
public class ClientException extends BaseRunTimeException {

	private static final long serialVersionUID = 7212317895977140327L;

	/**
	 * @param errorCode
	 */
	public ClientException(int errorCode) {
		super(errorCode);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public ClientException(int errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 * @param cause
	 */
	public ClientException(int errorCode, String msg, Throwable cause) {
		super(errorCode, msg, cause);
	}

	/**
	 * @param msg
	 */
	public ClientException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public ClientException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
