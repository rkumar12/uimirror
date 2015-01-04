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
package com.uimirror.sso.form;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.form.ClientMetaForm;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Converts the {@link QueryParam} provided in the POST request to get
 * a access key.
 * POST https://api.oauth2server.com/token?grant_type=authorization_codeAMP;code=AUTH_CODE_HEREAMP;redirect_uri=REDIRECT_URIAMP;client_id=CLIENT_IDAMP;client_secret=CLIENT_SECRET
 * @author Jay
 */
public class ClientSecretKeyForm extends ClientMetaForm implements BeanValidatorService{

	private static final long serialVersionUID = -6338697684103708792L;

	@FormParam(AuthConstants.CLIENT_SECRET_CODE)
	private String secretCode;

	@FormParam(AuthConstants.REDIRECT_URI)
	private String redirectURI;

	@FormParam(AuthConstants.CLIENT_ID)
	private String clientId;
	
	@FormParam(AuthConstants.CLIENT_SECRET)
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

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		validate();
		return Boolean.TRUE;
	}
	
	private void validate(){
		if(!StringUtils.hasText(getIp()))
			informIllegalArgument("IP Address Required for any authentication request");
		if(!StringUtils.hasText(getUserAgent()))
			informIllegalArgument("User Agent Required for any authentication request");
		if(!StringUtils.hasText(getRedirectURI()))
			informIllegalArgument("redirect URI should be present");
		if(!StringUtils.hasText(getClientId()))
			informIllegalArgument("Client Id Should present");
		if(!StringUtils.hasText(getClientSecret()))
			informIllegalArgument("Client Secret Should present");
		if(!StringUtils.hasText(getSecretCode()))
			informIllegalArgument("Client Secret Code Should present");
	}
	
	private void informIllegalArgument(String msg){
		throw new IllegalArgumentException(msg);
	}

}
