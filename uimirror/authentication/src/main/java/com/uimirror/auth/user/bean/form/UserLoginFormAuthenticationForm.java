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
import com.uimirror.core.auth.controller.AuthParamExtractor;
import com.uimirror.core.bean.form.DefaultHeaderForm;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the login screen.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications
 * 
 * @author Jay
 */
public final class UserLoginFormAuthenticationForm extends DefaultHeaderForm {

	private static final long serialVersionUID = -1215523730014366150L;

	@FormParam(AuthParamExtractor.USER_ID)
	private String userId;
	
	@FormParam(AuthParamExtractor.PASSWORD)
	private String password;
	
	@FormParam(AuthParamExtractor.KEEP_ME_LOGIN)
	private String keepMeLogedIn;

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getTokenEncryptStartegy()
	 */
	@Override
	public String getTokenEncryptStartegy() {
		return null;
	}

	@Override
	public String getKeepMeLogedIn() {
		return keepMeLogedIn;
	}

	@Override
	public String getAccessToken() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getRedirectUri()
	 */
	@Override
	public String getRedirectUri() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getScope()
	 */
	@Override
	public String getScope() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getClientId()
	 */
	@Override
	public String getClientId() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getClientSecret()
	 */
	@Override
	public String getClientSecret() {
		return null;
	}
	
	//######Below are the common authentication details########//
	@Override
	public CredentialType getCredentialType() {
		return CredentialType.LOGINFORM;
	}

	@Override
	public String toString() {
		return "UserLoginFormAuthenticationForm [userId=" + userId
				+ ", password=" + password + ", keepMeLogedIn=" + keepMeLogedIn
				+ "]";
	}

}
