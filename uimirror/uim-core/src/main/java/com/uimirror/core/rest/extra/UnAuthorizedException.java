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
public class UnAuthorizedException extends ApplicationException{

	private static final long serialVersionUID = 2893053632116181333L;
	
	private static final String MSG = "Not Authorized";
	private static final Status CODE = Response.Status.UNAUTHORIZED;

	/**
	 * Constructs the default exception for unauthorized
	 */
	public UnAuthorizedException() {
		super(CODE, MSG);
	}
	
	/**
	 * <p>Construct the response with user provided message</p>
	 * @param message
	 */
	public UnAuthorizedException(String message) {
		super(CODE, message);
	}

}
