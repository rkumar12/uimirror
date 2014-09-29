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
package com.uimirror.core.auth.controller;

import com.uimirror.core.auth.bean.AccessToken;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;
import com.uimirror.core.rest.extra.ApplicationException;

/**
 * Contract which defines, by taking the parameter it should give a valid response
 * @author Jay
 */
public interface AuthenticationController {

	/**
	 * <p>Validate the provided arguments and generate a access token {@link AccessToken}</p>
	 * @param param
	 * @return {@link AccessToken} if successful authenticated
	 * @throws ApplicationException
	 */
	Object doAuthenticate(BasicAuthenticationForm param) throws ApplicationException;
	
	/**
	 * Extracts the {@link Authentication} from the parameters
	 * @param param
	 * @return {@link Authentication} principal coresponds to the request
	 * @throws ApplicationException
	 */
	Authentication extractAuthentication(BasicAuthenticationForm param) throws ApplicationException;

}
