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
 * to invalidate a perticualr login session.
 * 
 * @author Jay
 */
public class InvalidateLoginSessionsForm extends AuthenticatedHeaderForm{

	private static final long serialVersionUID = 3625790752921721133L;
	
	@QueryParam(AuthConstants.SESSION_ID)
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String toString() {
		return "InvalidateLoginSessionsForm [sessionId=" + sessionId + "]";
	}

}
