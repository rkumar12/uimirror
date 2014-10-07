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
package com.uimirror.core.bean.form;

import java.io.Serializable;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

import com.uimirror.core.auth.BearerTokenExtractor;
import com.uimirror.core.auth.bean.Token;
import com.uimirror.core.auth.controller.AuthParamExtractor;

/**
 * Converts the {@link HeaderParam} provided in the request for the
 * authentication purpose.
 * 
 * @author Jay
 */
public class AuthenticatedHeaderForm extends ClientMetaForm implements Serializable, AuthenticatedRequestParam{

	private static final long serialVersionUID = -1215523730014366150L;
	
	@HeaderParam(AuthParamExtractor.TOKEN_ENCRYPTION_STARTEGY)
	private String tokenEncryptionStartegy;
	
	@HeaderParam(AuthParamExtractor.AUTHORIZATION_TOKEN)
	private String accessToken;
	
	//OUTH2 allows accessToken can be part of the query parameter as well
	@QueryParam(AuthParamExtractor.ACCESS_TOKEN)
	private String accessTokenInRequestParam;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.AuthenticatedRequestParam#getToken()
	 */
	@Override
	public Token getToken() {
		return new Token(getAccessToken(), tokenEncryptionStartegy).getDecrypted();
	}
	
	/**
	 * This will return the Extracted token from the request.
	 * @return
	 */
	private String getAccessToken(){
		return BearerTokenExtractor.extractAccessToken(accessToken, accessTokenInRequestParam);
	}
	
	@Override
	public String toString() {
		return "HeaderAuthenticationForm [tokenEncryptionStartegy="
				+ tokenEncryptionStartegy + ", accessToken=" + accessToken
				+ ", accessTokenInRequestParam=" + accessTokenInRequestParam
				+ "]";
	}
	
}
