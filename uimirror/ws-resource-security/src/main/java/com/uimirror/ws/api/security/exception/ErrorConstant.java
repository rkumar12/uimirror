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

/**
 * <p>Contains all the error message and associated error code if any</p>
 * @author Jay
 */
public interface ErrorConstant {

	//Token Details not found
	public static final int TOKEN_NOT_FOUND = 404;
	//Token has been expired
	public static final int TOKEN_EXPIRED = 419;
}
