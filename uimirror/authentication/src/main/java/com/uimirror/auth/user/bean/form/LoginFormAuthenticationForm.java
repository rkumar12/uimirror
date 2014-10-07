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

import com.uimirror.core.auth.controller.AuthParamExtractor;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the login screen.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications
 * 
 * @author Jay
 */
public final class LoginFormAuthenticationForm extends AuthenticatedHeaderForm {

	private static final long serialVersionUID = -1215523730014366150L;

	@FormParam(AuthParamExtractor.USER_ID)
	private String userId;
	
	@FormParam(AuthParamExtractor.PASSWORD)
	private String password;
	
	@FormParam(AuthParamExtractor.KEEP_ME_LOGIN)
	private String keepMeLogedIn;

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getKeepMeLogedIn() {
		return keepMeLogedIn;
	}

	@Override
	public String toString() {
		return "LoginFormAuthenticationForm [userId=" + userId + ", password= <<******>>, "
				+ "keepMeLogedIn=" + keepMeLogedIn + "]";
	}

}
