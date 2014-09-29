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

import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.form.HeaderAuthenticationForm;
import com.uimirror.core.auth.controller.AuthParamExtractor;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the screen locked login screen.
 * 
 * @author Jay
 */
public class ScreenLockAuthenticationForm extends HeaderAuthenticationForm{

	private static final long serialVersionUID = -1268777827570961853L;

	@FormParam(AuthParamExtractor.PASSWORD)
	private String password;
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getUserId()
	 */
	@Override
	public String getUserId() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getKeepMeLogedIn()
	 */
	@Override
	public String getKeepMeLogedIn() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getRedirectUri()
	 */
	@Override
	public String getRedirectUri() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getScope()
	 */
	@Override
	public String getScope() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getClientId()
	 */
	@Override
	public String getClientId() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getClientSecret()
	 */
	@Override
	public String getClientSecret() {
		return null;
	}
	
	public CredentialType getCredentialType() {
		return CredentialType.SCREENLOCK;
	}

	@Override
	public String toString() {
		return "ScreenLockAuthenticationForm [password=" + password + "]";
	}
	
}
