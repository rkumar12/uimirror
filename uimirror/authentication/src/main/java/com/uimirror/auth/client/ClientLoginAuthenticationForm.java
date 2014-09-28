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
package com.uimirror.auth.client;

import javax.ws.rs.FormParam;

import com.uimirror.core.auth.AuthParamExtractor;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.bean.form.DefaultHeaderForm;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the client api key to get secret key.
 * 
 * @author Jay
 */
public class ClientLoginAuthenticationForm extends DefaultHeaderForm{

	private static final long serialVersionUID = -6338697684103708792L;

	@FormParam(AuthParamExtractor.CLIENT_ID)
	private String clientId;
	
	@FormParam(AuthParamExtractor.REDIRECT_URI)
	private String redirectURI;
	
	@FormParam(AuthParamExtractor.SCOPE)
	private String scope;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getUserId()
	 */
	@Override
	public String getUserId() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getTokenEncryptStartegy()
	 */
	@Override
	public String getTokenEncryptStartegy() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getPassword()
	 */
	@Override
	public String getPassword() {
		return null;
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
		return this.redirectURI;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getScope()
	 */
	@Override
	public String getScope() {
		return this.scope;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getAccessToken()
	 */
	@Override
	public String getAccessToken() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getClientId()
	 */
	@Override
	public String getClientId() {
		return this.clientId;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.BasicAuthenticationForm#getClientSecret()
	 */
	@Override
	public String getClientSecret() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.CommonAuthenticationForm#getCredentialType()
	 */
	@Override
	public CredentialType getCredentialType() {
		return CredentialType.APIKEY;
	}

	@Override
	public String toString() {
		return "ClientLoginAuthenticationForm [clientId=" + clientId
				+ ", redirectURI=" + redirectURI + ", scope=" + scope + "]";
	}

}
