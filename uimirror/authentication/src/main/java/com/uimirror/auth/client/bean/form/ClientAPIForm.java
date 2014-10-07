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
 * Converts the {@link QueryParam} provided in the GET request for the
 * to get secret key.
 * A standard url will look like /auth?response_type=code&client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&scope=photos&app=rti
 * 
 * @author Jay
 */
public class ClientAPIForm extends ClientMetaForm{

	private static final long serialVersionUID = -6338697684103708792L;

	@QueryParam(AuthParamExtractor.CLIENT_ID)
	private String clientId;
	
	@QueryParam(AuthParamExtractor.REDIRECT_URI)
	private String redirectURI;
	
	@QueryParam(AuthParamExtractor.SCOPE)
	private String scope;
	
	@QueryParam(AuthParamExtractor.APP)
	private String app;
	
	public String getClientId() {
		return clientId;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public String getScope() {
		return scope;
	}

	public String getApp() {
		return app;
	}

	@Override
	public String toString() {
		return "ClientSecretCodeForm [clientId=" + clientId + ", redirectURI="
				+ redirectURI + ", scope=" + scope + ", app=" + app + "]";
	}

}
