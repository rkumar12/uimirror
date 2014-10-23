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
package com.uimirror.core.rest.extra;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * An child of {@link WebApplicationException} with default initialization
 * of message and error code 
 * @author Jay
 */
public class AlreadyExistException extends ExceptionInJson{

	private static final long serialVersionUID = 2893053632116181333L;
	
	private static final String MSG = "Last Request was unsuccessful";
	private static final Status CODE = Response.Status.FOUND;

	/**
	 * Constructs the default exception for unauthorized
	 */
	public AlreadyExistException() {
		super(CODE, MSG);
	}
	
	/**
	 * <p>Construct the response with user provided message</p>
	 * @param message
	 */
	public AlreadyExistException(String message) {
		super(CODE, message);
	}
	
	/**
	 * <p>Construct the response with user provided message</p>
	 * @param message
	 */
	public AlreadyExistException(Object message) {
		super(CODE, message);
	}

}
