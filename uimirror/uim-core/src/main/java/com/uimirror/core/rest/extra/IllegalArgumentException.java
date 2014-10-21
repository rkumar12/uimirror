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

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * An child of {@link WebApplicationException} with default initialization
 * of message and error code 
 * @author Jay
 */
public class IllegalArgumentException extends ExceptionInJson{

	private static final long serialVersionUID = 2893053632116181333L;
	
	private static final String MSG = "provided details are unsuficent";
	private static final Status CODE = Response.Status.NOT_ACCEPTABLE;

	/**
	 * Constructs the default exception for unauthorized
	 */
	public IllegalArgumentException() {
		super(CODE, MSG);
	}
	
	/**
	 * <p>Construct the response with user provided message</p>
	 * @param message
	 */
	public IllegalArgumentException(String message) {
		super(CODE, message);
	}
	
	/**
	 * <p>Construct the response with user provided message</p>
	 * @param message
	 */
	public IllegalArgumentException(Map<String, Object> message) {
		super(CODE, message);
	}
	

}
