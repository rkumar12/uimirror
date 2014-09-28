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
package com.uimirror.core.auth.bean.form;

import java.io.Serializable;

import javax.ws.rs.HeaderParam;

import com.uimirror.core.auth.AuthParamExtractor;
import com.uimirror.core.auth.BearerTokenExtractor;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.bean.form.ClientMetaForm;

/**
 * Converts the {@link HeaderParam} provided in the request for the
 * authentication purpose.
 * 
 * @author Jay
 */
public abstract class HeaderAuthenticationForm extends ClientMetaForm implements Serializable, BasicAuthenticationForm{

	private static final long serialVersionUID = -1215523730014366150L;
	
	@HeaderParam(AuthParamExtractor.TOKEN_ENCRYPTION_STARTEGY)
	private String tokenEncryptionStartegy;
	
	@HeaderParam(AuthParamExtractor.ACCESS_TOKEN)
	private String accessToken;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.BasicAuthenticationForm#getTokenEncryptStartegy()
	 */
	@Override
	public String getTokenEncryptStartegy() {
		return this.tokenEncryptionStartegy;
	}

	public String getAccessToken() {
		return BearerTokenExtractor.extractAccessToken(accessToken);
	}
	
	public CredentialType getCredentialType() {
		return CredentialType.ACCESSKEY;
	}
	
	public String getIp() {
		return super.getIp();
	}

	public String getUserAgent() {
		return super.getUserAgent();
	}

	@Override
	public String toString() {
		return "HeaderAuthenticationForm [tokenEncryptionStartegy="
				+ tokenEncryptionStartegy + ", accessToken=" + accessToken
				+ "]";
	}
	
}
