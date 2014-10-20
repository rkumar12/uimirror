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
package com.uimirror.account.auth.form;

import javax.ws.rs.QueryParam;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.form.AuthenticatedHeaderForm;

/**
 * This is the {@linkplain QueryParam} details for the end point 
 * to retrieve all the login sessions with query limit.
 * 
 * @author Jay
 */
public class LoginSessionForm extends AuthenticatedHeaderForm{

	private static final long serialVersionUID = 3625790752921721133L;
	
	@QueryParam(AuthConstants.LIMIT)
	private int limit;

	/**
	 * Checks if the url don't have any query limit 
	 * default to 10
	 * @return
	 */
	public int getLimit() {
		return limit == 0 ? 10 : limit;
	}

	@Override
	public String toString() {
		return "LoginSessionsForm [limit=" + limit + "]";
	}

}
