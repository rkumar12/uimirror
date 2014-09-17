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

import com.uimirror.core.BaseRunTimeException;

/**
 * Base exception for all the database related.
 * @author Jay
 */
public abstract class DBException extends BaseRunTimeException{

	private static final long serialVersionUID = -1864093646402956872L;

    /**
     *
     * @param code the error code
     * @param msg the message
     */
    public DBException( int code , String msg ){
        super( code, msg);
    }

    /**
     *
     * @param code the error code
     * @param msg the message
     * @param t the throwable cause
     */
    public DBException( int code , String msg , Throwable t ){
        super( code, msg , t );
    }

}
