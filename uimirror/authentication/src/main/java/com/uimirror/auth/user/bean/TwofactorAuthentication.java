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

import org.springframework.util.Assert;

import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.Scope;

/** 
 * <p>Contains the user authentication details which will be the principal details of the user which should be of type form</p>
 * <p>Which might have only password field but doesn't have any accesskey Header information</p>
 * This details will be erased once authentication process over by {@link AuthenticationManager#authenticate(Authentication)}
 * This expects a otp field needs to be populated
 * @author Jay
 */
public class TwofactorAuthentication extends AccessKeyBasedAuthentication{
	
	private static final long serialVersionUID = 3795112886906141341L;
	
	private String otp;

	/**
	 * @param accesskey
	 * @param paraPharse
	 * @param ip
	 * @param uAgent
	 */
	private TwofactorAuthentication(String accesskey, String paraPharse, String ip, String uAgent) {
		super(CredentialType._2FA, accesskey, paraPharse, ip, uAgent);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	private TwofactorAuthentication(String accessKey, String paraPharse, Map<String, Object> details, String ip, String uAgent) {
		super(CredentialType._2FA, accessKey, paraPharse, details, ip, uAgent);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param details
	 */
	private TwofactorAuthentication(String accessKey, String paraPharse, Map<String, Object> details) {
		super(CredentialType._2FA, accessKey, paraPharse, details);
	}
	
	/**
	 * @param accessKey
	 * @param details
	 */
	private TwofactorAuthentication(String accessKey, Map<String, Object> details) {
		super(CredentialType._2FA, accessKey, details);
	}
	
	/**
	 * @param accessKey
	 * @param ip
	 * @param uAgent
	 */
	private TwofactorAuthentication(String accessKey, String ip, String uAgent) {
		super(CredentialType._2FA, accessKey, ip, uAgent);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param otp
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	public TwofactorAuthentication(String accessKey, String paraPharse, String otp, Map<String, Object> details, String ip, String uAgent) {
		this(accessKey, paraPharse, details, ip, uAgent);
		intialize(otp);
	}

	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param otp
	 * @param details
	 */
	public TwofactorAuthentication(String accessKey, String paraPharse, String otp, Map<String, Object> details) {
		this(accessKey, paraPharse, details);
		intialize(otp);
	}
	
	/**
	 * @param details
	 * @param otp
	 * @param accessKey
	 */
	public TwofactorAuthentication(Map<String, Object> details, String otp, String accessKey) {
		this(accessKey, details);
		intialize(otp);
	}
	
	/**
	 * @param accessKey
	 * @param paraPharse
	 * @param otp
	 * @param ip
	 * @param uAgent
	 */
	public TwofactorAuthentication(String accessKey, String paraPharse, String otp, String ip, String uAgent) {
		this(accessKey, paraPharse, ip, uAgent);
		intialize(otp);
	}
	
	private void intialize(String otp){
		Assert.hasText(otp, "OTP Can't Be Empty");
		this.otp = otp;
	}
	
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public String getCredentials() {
		return this.otp;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getAuthenticationScope()
	 */
	@Override
	public Scope getAuthenticationScope() {
		return null;
	}

	@Override
	public String toString() {
		return "FormBasedAuthentication [otp=<<*****>>]";
	}
	
}
