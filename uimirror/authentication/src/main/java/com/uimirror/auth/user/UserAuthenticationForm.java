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
package com.uimirror.auth.user;

import java.io.Serializable;

import javax.ws.rs.FormParam;

import com.uimirror.auth.AuthParamExtractor;
import com.uimirror.core.Constants;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose.
 * 
 * @author Jay
 */
public final class UserAuthenticationForm implements Serializable{

	private static final long serialVersionUID = -1215523730014366150L;

	@FormParam(AuthParamExtractor.CREDENTIAL_TYPE)
	private String credentialType;
	
	@FormParam(AuthParamExtractor.DEVICE_TYPE)
	private String deviceType;
	
	@FormParam(AuthParamExtractor.REFRESH_TOKEN_INTERVAL)
	private String refreshTokenInterval;
	
	@FormParam(AuthParamExtractor.USER_ID)
	private String userId;
	
	@FormParam(AuthParamExtractor.PASSWORD)
	private String password;
	
	@FormParam(AuthParamExtractor.KEEP_ME_LOGIN)
	private String keepMeLogedIn;
	
	@FormParam(Constants.IP)
	private String ip;
	
	@FormParam(Constants.USER_AGENT)
	private String userAgent;
	
	@FormParam(AuthParamExtractor.ACCESS_TOKEN)
	private String accessToken;

	public String getCredentialType() {
		return credentialType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public String getRefreshTokenInterval() {
		return refreshTokenInterval;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getKeepMeLogedIn() {
		return keepMeLogedIn;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public String toString() {
		return "UserAuthenticationForm [credentialType=" + credentialType
				+ ", deviceType=" + deviceType + ", refreshTokenInterval="
				+ refreshTokenInterval + ", userId=" + userId + ", password="
				+ password + ", keepMeLogedIn=" + keepMeLogedIn + ", ip=" + ip
				+ ", userAgent=" + userAgent + ", accessToken=" + accessToken
				+ "]";
	}

}
