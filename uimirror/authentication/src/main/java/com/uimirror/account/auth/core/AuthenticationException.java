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
package com.uimirror.account.auth.core;

import com.uimirror.core.auth.Authentication;
import com.uimirror.core.exceptions.BaseRunTimeException;


/**
 * Abstract superclass for all exceptions related to an {@link Authentication} object being invalid for whatever
 * reason.
 * 
 * @author Jay
 */
public abstract class AuthenticationException extends BaseRunTimeException{

	private static final long serialVersionUID = -1887837148759197895L;

	/**
     * Constructs an {@code AuthenticationException} with the specified message and root cause.
     *
     *@param errorCode
     * @param msg the detail message
     * @param t the root cause
     */
    public AuthenticationException(int errorCode, String msg, Throwable t) {
        super(errorCode, msg, t);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no root cause.
     *@param errorCode
     * @param msg the detail message
     */
    public AuthenticationException(int errorCode, String msg) {
        super(errorCode, msg);
    }

}
