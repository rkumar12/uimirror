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
package com.uimirror.auth.bean.form;

import javax.ws.rs.QueryParam;

import com.uimirror.core.auth.controller.AuthParamExtractor;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;

/**
 * This is the {@linkplain QueryParam} details for the end point 
 * to invalidate a perticualr login session.
 * 
 * @author Jay
 */
public class ForgetAClientForm extends AuthenticatedHeaderForm{

	private static final long serialVersionUID = 3625790752921721133L;
	
	@QueryParam(AuthParamExtractor.CLIENT_ID)
	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return "ForgetAClientForm [clientId=" + clientId + "]";
	}

}
