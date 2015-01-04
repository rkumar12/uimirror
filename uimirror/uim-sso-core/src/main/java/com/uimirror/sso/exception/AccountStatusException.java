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
package com.uimirror.sso.exception;

/**
 * Base class for authentication exceptions which are caused by a particular
 * user account status (locked, disabled etc).
 * 
 * @author Jay
 */
public abstract class AccountStatusException extends AuthenticationException{

	private static final long serialVersionUID = 5754524137547753112L;

	/**
	 * @param errorCode is integer parameter
	 * @param msg is String parameter
	 */
	public AccountStatusException(int errorCode, String msg) {
		super(errorCode, msg);
	}
	
	/**
	 * 
	 * @param errorCode is integer parameter
	 * @param msg is String parameter
	 * @param t is error message
	 */
	public AccountStatusException(int errorCode, String msg, Throwable t){
		super(errorCode, msg, t);
	}


}
