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

import javax.ws.rs.QueryParam;

import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.form.ClientMetaForm;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Converts the {@link QueryParam} provided in the GET request for the
 * to get secret key.
 * A standard url will look like /auth?response_type=codeAMP;client_id=CLIENT_IDAMP;redirect_uri=REDIRECT_URIAMP;scope=photosAMP;app=rti
 * 
 * @author Jay
 */
public class ClientAPIForm extends ClientMetaForm implements BeanValidatorService{

	private static final long serialVersionUID = -6338697684103708792L;

	@QueryParam(AuthConstants.CLIENT_ID)
	private String clientId;
	
	@QueryParam(AuthConstants.REDIRECT_URI)
	private String redirectURI;
	
	@QueryParam(AuthConstants.SCOPE)
	private String scope;
	
	public String getClientId() {
		return clientId;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public Scope getScope() {
		return Scope.getEnum(scope);
	}
	
	@Override
	public String toString() {
		return "ClientAPIForm [clientId=" + clientId + ", redirectURI="
				+ redirectURI + ", scope=" + scope + "]";
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
		if(!StringUtils.hasText(scope) || Scope.getEnum(scope) != null)
			informIllegalArgument("Scope Should present");
	}
	
	private void informIllegalArgument(String msg){
		throw new IllegalArgumentException(msg);
	}

}
