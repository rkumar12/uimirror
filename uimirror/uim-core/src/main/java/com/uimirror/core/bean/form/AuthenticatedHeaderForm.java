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

import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.BearerTokenExtractor;
import com.uimirror.core.auth.Token;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Converts the {@link HeaderParam} provided in the request for the
 * authentication purpose.
 * 
 * @author Jay
 */
public class AuthenticatedHeaderForm extends ClientMetaForm implements Serializable, AuthenticatedRequestParam, BeanValidatorService{

	private static final long serialVersionUID = -1215523730014366150L;
	
	@HeaderParam(AuthConstants.TOKEN_ENCRYPTION_STARTEGY)
	private String tokenEncryptionStartegy;
	
	@HeaderParam(AuthConstants.AUTHORIZATION_TOKEN)
	private String accessToken;
	
	//OUTH2 allows accessToken can be part of the query parameter as well
	@QueryParam(AuthConstants.ACCESS_TOKEN)
	private String accessTokenInRequestParam;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.AuthenticatedRequestParam#getToken()
	 */
	@Override
	public String getToken() {
		return new Token(getAccessToken(), tokenEncryptionStartegy).getDecrypted().getToken();
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

	//**********************Don't forgot to call this method from all the subclass
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		validate();
		return Boolean.FALSE;
	}
	
	private void validate(){
		if(!StringUtils.hasText(getIp()))
			informIllegalArgument("IP Address Required for any authentication request");
		if(!StringUtils.hasText(getUserAgent()))
			informIllegalArgument("User Agent Required for any authentication request");
		if(!StringUtils.hasText(getToken()))
			informIllegalArgument("Access Token Should present");
	}
	
	private void informIllegalArgument(String msg){
		throw new IllegalArgumentException(msg);
	}
	
}
