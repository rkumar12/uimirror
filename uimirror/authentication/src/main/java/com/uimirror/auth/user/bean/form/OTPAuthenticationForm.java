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
 * authentication purpose from the two factor authentication form.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications or from the client
 * 
 * @author Jay
 */
public final class OTPAuthenticationForm extends AuthenticatedHeaderForm {

	private static final long serialVersionUID = -1215523730014366150L;
	
	@FormParam(AuthConstants.OTP)
	private String otp;

	public String getOtp() {
		return otp;
	}

	@Override
	public String toString() {
		return "TwoFactorUserLoginAuthenticationForm [otp=" + otp + "]";
	}

}
