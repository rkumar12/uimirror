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
package com.uimirror.auth.user.bean.form;

import javax.ws.rs.FormParam;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the screen locked login screen.
 * 
 * @author Jay
 */
public class ScreenLockAuthenticationForm extends AuthenticatedHeaderForm{

	private static final long serialVersionUID = -1268777827570961853L;

	@FormParam(AuthConstants.PASSWORD)
	private String password;

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "ScreenLockAuthenticationForm [password=" + password + "]";
	}
	
	
}
