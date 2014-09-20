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
package com.uimirror.core.dao;

import com.uimirror.core.ErrorCodes;


/**
 * When Data base connection is lost, it will be get notified
 * with code as {@link ErrorCodes#_503}
 * @author Jay
 */
public class NoConnectionException extends DBException{

	private static final long serialVersionUID = 5952640506213878692L;
	
	public NoConnectionException() {
		super(ErrorCodes._503, "Unable to get the connection");
	}

	/**
     * Constructs an {@code NoConnectionException} with the specified message and root cause.
     *
     *@param errorCode
     * @param msg the detail message
     */
    public NoConnectionException(String msg) {
        super(ErrorCodes._503, msg);
    }

}
