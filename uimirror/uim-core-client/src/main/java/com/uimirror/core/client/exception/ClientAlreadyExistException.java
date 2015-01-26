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
package com.uimirror.core.client.exception;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.uimirror.core.rest.extra.ExceptionInJson;

/**
 * When Client Exists in the system
 * @author Jay
 */
public class ClientAlreadyExistException extends ExceptionInJson{

	private static final long serialVersionUID = -8662491100870909594L;
	private static final String MSG = "provided details are already present";
	private static final Status CODE = Response.Status.FOUND;

	/**
	 * Constructs the default exception for unauthorized
	 */
	public ClientAlreadyExistException() {
		super(CODE, MSG);
	}
	
	/**
	 * <p>Construct the response with user provided message</p>
	 * @param message Detailed Exception Message
	 */
	public ClientAlreadyExistException(String message) {
		super(CODE, message);
	}
	
	/**
	 * <p>Construct the response with user provided message</p>
	 * @param message exception Message
	 */
	public ClientAlreadyExistException(Map<String, Object> message) {
		super(CODE, message);
	}

}
