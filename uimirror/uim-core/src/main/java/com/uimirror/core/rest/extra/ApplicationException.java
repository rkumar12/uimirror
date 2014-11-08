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

/**
 * Constructs the response with appropriate response code for the application exception
 * that needs to be transmitted to the user.
 * 
 * @author Jay
 */
public abstract class ApplicationException extends WebApplicationException{

	private static final long serialVersionUID = 7633918125632783005L;

	/**
	 * <p>Initialize the exception response to be part of the response</p>
	 * @param res which will be mapped
	 */
	public ApplicationException(Response res) {
		super(res);
	}
	

}
