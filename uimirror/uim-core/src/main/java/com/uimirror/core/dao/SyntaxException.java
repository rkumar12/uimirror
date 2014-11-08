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
 * When Data base failed to execute a specified command
 * with code as {@link ErrorCodes#_510}
 * @author Jay
 */
public class SyntaxException extends DBException{

	private static final long serialVersionUID = 5952640506213878692L;
	
	public SyntaxException() {
		super(ErrorCodes._510, "Unable to execute command");
	}

	/**
     * Constructs an {@code SyntaxException} with the specified message and root cause.
     *
     * @param msg the detail message
     */
    public SyntaxException(String msg) {
        super(ErrorCodes._510, msg);
    }

}
