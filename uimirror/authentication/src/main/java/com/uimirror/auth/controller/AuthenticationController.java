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
package com.uimirror.auth.controller;

import javax.ws.rs.WebApplicationException;

import com.uimirror.core.auth.Authentication;

/**
 * Contract which defines, by taking the parameter it should give a valid response
 * @author Jay
 */
public interface AuthenticationController {

	/**
	 * <p>Validate the provided arguments and generate a access token</p>
	 * @param param
	 * @return
	 * @throws WebApplicationException
	 */
	Object getAccessToken(Object param) throws WebApplicationException;
	
	/**
	 * Extracts the {@link Authentication} from the parameters
	 * @param param
	 * @return
	 * @throws WebApplicationException
	 */
	Authentication getAuthentication(Object param) throws WebApplicationException;
	
	/**
	 * Builds forbidden response, when something wrong happens with the login process
	 * @return
	 */
	Object unAuthorizedResponse();

}
