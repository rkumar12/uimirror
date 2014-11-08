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
 * When Data base failed to write in bulk data
 * with code as {@link ErrorCodes#_599}
 * @author Jay
 */
public class BatchWriteException extends DBException{

	private static final long serialVersionUID = 5952640506213878692L;
	
	public BatchWriteException() {
		super(ErrorCodes._599, "Unable to write bulk record");
	}

	/**
     * Constructs an {@code BatchWriteException} with the specified message and root cause.
     *
     * @param msg the detail message
     */
    public BatchWriteException(String msg) {
        super(ErrorCodes._599, msg);
    }

}
