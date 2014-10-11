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
package com.uimirror.auth.user.bean;

import java.util.Map;

import com.uimirror.auth.client.bean.OAuth2Authentication;
import com.uimirror.core.auth.AuthConstants;

/**
 * AccessToken <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * accessToken=code&
 * tokenEncryptionStartegy=parapharse
 * &otp=otp
 *  
 * @author Jay
 */
public class OTPAuthentication extends OAuth2Authentication{
	
	private static final long serialVersionUID = 3795112886906141341L;
	
	/**
	 * @param token
	 * @param otp
	 */
	public OTPAuthentication(String token, String otp) {
		super(token);
		init(otp);
	}
	/**
	 * @param token
	 * @param otp
	 * @param ip
	 * @param userAgent
	 */
	public OTPAuthentication(String token, String otp, String ip, String userAgent) {
		super(token, ip, userAgent);
		init(otp);
	}

	/**
	 * @param tokenPrincipal
	 * @param details
	 */
	public OTPAuthentication(Object tokenPrincipal, Map<String, Object> details) {
		super(tokenPrincipal, details);
	}
	
	/**
	 * @param tokenPrincipal
	 */
	public OTPAuthentication(Object tokenPrincipal) {
		super(tokenPrincipal);
	}
	
	/**
	 * @param otp
	 */
	private void init(String otp){
		addCrdentials(otp);
	}
	
	/**
	 * @param otp
	 */
	@SuppressWarnings("unchecked")
	private void addCrdentials(String otp){
		Map<String, String> credentials = (Map<String, String>)getCredentials();
		credentials.put(AuthConstants.PASSWORD, otp);
		updateCredentials(credentials);
	}
	
}
