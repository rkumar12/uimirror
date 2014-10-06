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
 * authentication purpose from the two factor authentication form.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications or from the client
 * 
 * @author Jay
 */
public final class TwoFactorUserLoginAuthenticationForm extends HeaderAuthenticationForm {

	private static final long serialVersionUID = -1215523730014366150L;
	
	@FormParam(AuthParamExtractor.OTP)
	private String otp;

	@Override
	public String getUserId() {
		return null;
	}

	@Override
	public String getPassword() {
		return otp;
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

	@Override
	public CredentialType getCredentialType() {
		return CredentialType._2FA;
	}

	@Override
	public String toString() {
		return "TwoFactorUserLoginAuthenticationForm [otp=" + otp
				+ "]";
	}

}
