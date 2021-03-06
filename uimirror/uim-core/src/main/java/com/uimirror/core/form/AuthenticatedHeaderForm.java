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
package com.uimirror.core.form;

import static com.uimirror.core.auth.AuthConstants.ACCESS_TOKEN;
import static com.uimirror.core.auth.AuthConstants.AUTHORIZATION_TOKEN;
import static com.uimirror.core.auth.AuthConstants.TOKEN_ENCRYPTION_STARTEGY;

import java.io.Serializable;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

import org.springframework.util.StringUtils;

import com.uimirror.core.auth.BearerTokenExtractor;
import com.uimirror.core.auth.Token;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Converts the {@link HeaderParam} provided in the request for the
 * authentication purpose.
 * 
 * Every request needs an access token to access the system. every calss or operation which are
 * Dependent of access Token should extend this class
 * 
 * @author Jay
 */
public class AuthenticatedHeaderForm extends ClientMetaForm implements Serializable, AuthenticatedRequestParam, BeanValidatorService{

	private static final long serialVersionUID = -1215523730014366150L;
	
	@HeaderParam(TOKEN_ENCRYPTION_STARTEGY)
	private String tokenEncryptionStartegy;
	
	//Can be part of the header param
	@HeaderParam(AUTHORIZATION_TOKEN)
	private String accessToken;
	
	//OUTH2 allows accessToken can be part of the query parameter as well
	@QueryParam(ACCESS_TOKEN)
	private String accessTokenInRequestParam;

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.form.AuthenticatedRequestParam#getToken()
	 */
	@Override
	@ApiModelProperty(value="Token", hidden=true)
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
	@ApiModelProperty(value="valid", hidden=true)
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
