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

import com.uimirror.core.auth.AuthParamExtractor;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.form.HeaderAuthenticationForm;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the login screen pushed from the client 
 * request.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications
 * 
 * @author Jay
 */
public final class ClientUserLoginFormAuthenticationForm extends HeaderAuthenticationForm {

	private static final long serialVersionUID = -1215523730014366150L;

	@FormParam(AuthParamExtractor.USER_ID)
	private String userId;
	
	@FormParam(AuthParamExtractor.PASSWORD)
	private String password;
	
	@FormParam(AuthParamExtractor.SCOPE)
	private String scope;

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getKeepMeLogedIn() {
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
		return this.scope;
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

	@Override
	public CredentialType getCredentialType() {
		return CredentialType.USERLOGINFORMFROMCLIENT;
	}

	@Override
	public String toString() {
		return "ClientUserLoginFormAuthenticationForm [userId=" + userId
				+ ", password=" + password + ", scope=" + scope + "]";
	}

}
