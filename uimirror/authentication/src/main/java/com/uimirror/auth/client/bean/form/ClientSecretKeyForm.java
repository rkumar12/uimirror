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
package com.uimirror.auth.client.bean.form;

import javax.ws.rs.QueryParam;

import com.uimirror.core.auth.controller.AuthParamExtractor;
import com.uimirror.core.bean.form.ClientMetaForm;

/**
 * Converts the {@link QueryParam} provided in the POST request to get
 * a access key.
 * POST https://api.oauth2server.com/token?grant_type=authorization_code&code=AUTH_CODE_HERE&
 * redirect_uri=REDIRECT_URI&
 * client_id=CLIENT_ID&
 * client_secret=CLIENT_SECRET
 * @author Jay
 */
public class ClientSecretKeyForm extends ClientMetaForm{

	private static final long serialVersionUID = -6338697684103708792L;

	@QueryParam(AuthParamExtractor.CLIENT_SECRET_CODE)
	private String secretCode;

	@QueryParam(AuthParamExtractor.REDIRECT_URI)
	private String redirectURI;

	@QueryParam(AuthParamExtractor.CLIENT_ID)
	private String clientId;
	
	@QueryParam(AuthParamExtractor.CLIENT_SECRET)
	private String clientSecret;

	public String getRedirectURI() {
		return redirectURI;
	}

	public String getSecretCode() {
		return secretCode;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public String toString() {
		return "ClientAccessCodeForm [code=" + secretCode + ", redirectURI="
				+ redirectURI + ", clientId=" + clientId + ", clientSecret="
				+ clientSecret + "]";
	}

}
