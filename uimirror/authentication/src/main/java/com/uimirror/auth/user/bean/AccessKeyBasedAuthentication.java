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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.auth.bean.Authentication;
import com.uimirror.auth.bean.CommonAuthentication;
import com.uimirror.auth.bean.CredentialType;
import com.uimirror.auth.core.AccessTokenFields;
import com.uimirror.auth.core.AuthenticationManager;
import com.uimirror.core.auth.Scope;

/** 
 * <p>Contains the user authentication details which will be the principal details of the user</p>
 * <p>implements the {@link Authentication} which has basic user id in raw format and password in encrypted format</p>
 * This details will be erased once authentication process over by {@link AuthenticationManager#authenticate(Authentication)}
 * This expects a user id and password field needs to be populated
 * @author Jay
 */
public class AccessKeyBasedAuthentication extends CommonAuthentication{
	
	private static final long serialVersionUID = 3795112886906141341L;

	private String accessToken;
	private Map<String, Object> details;

	/**
	 * @param type
	 * @param ip
	 * @param uAgent
	 */
	private AccessKeyBasedAuthentication(CredentialType type, String ip, String uAgent) {
		super(type, ip, uAgent);
	}

	/**
	 * @param type
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	private AccessKeyBasedAuthentication(CredentialType type, Map<String, Object> details, String ip, String uAgent) {
		super(type, details, ip, uAgent);
	}

	/**
	 * @param type
	 * @param details
	 */
	private AccessKeyBasedAuthentication(CredentialType type, Map<String, Object> details) {
		super(type, details);
	}
	
	/**
	 * @param type
	 * @param accessToken
	 * @param paraPharse
	 * @param details
	 * @param ip
	 * @param uAgent
	 */
	public AccessKeyBasedAuthentication(CredentialType type, String accessToken, String paraPharse, Map<String, Object> details, String ip, String uAgent) {
		this(type, details, ip, uAgent);
		Assert.hasText(accessToken, "User Identifier can't be empty");
		this.details = details;
		initialize(accessToken, paraPharse, details);
	}

	/**
	 * @param type
	 * @param accessToken
	 * @param paraPharse
	 * @param details
	 */
	public AccessKeyBasedAuthentication(CredentialType type, String accessToken, String paraPharse, Map<String, Object> details) {
		this(type, details);
		Assert.hasText(accessToken, "User Identifier can't be empty");
		this.details = details;
		initialize(accessToken, paraPharse, details);
	}
	
	/**
	 * @param type
	 * @param accessToken
	 * @param paraPharse
	 * @param ip
	 * @param uAgent
	 */
	public AccessKeyBasedAuthentication(CredentialType type, String accessToken, String paraPharse, String ip, String uAgent) {
		this(type, ip, uAgent);
		initialize(accessToken, paraPharse, null);
	}

	/**
	 * @param type
	 * @param accessToken
	 * @param ip
	 * @param uAgent
	 */
	public AccessKeyBasedAuthentication(CredentialType type, String accessToken, String ip, String uAgent) {
		this(type, ip, uAgent);
		initialize(accessToken, null, null);
	}

	/**
	 * @param type
	 * @param accessToken
	 * @param details
	 */
	public AccessKeyBasedAuthentication(CredentialType type, String accessToken, Map<String, Object> details) {
		this(type, details);
		initialize(accessToken, null, details);
	}
	/**
	 * Initialize the current state of the objects
	 * @param accessToken
	 * @param paraPharse
	 * @param details
	 */
	private void initialize(String accessToken, String paraPharse, Map<String, Object> details){
		Assert.hasText(accessToken, "User Identifier can't be empty");
		this.accessToken = accessToken;
		this.details = details;
		addParaPharse(paraPharse);
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.accessToken;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public String getCredentials() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getDetails()
	 */
	@Override
	public Map<String, Object> getDetails() {
		return this.details;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#keepMeLogin()
	 */
	@Override
	public boolean keepMeLogin() {
		return Boolean.FALSE;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getAuthenticationScope()
	 */
	@Override
	public Scope getAuthenticationScope() {
		return null;
	}
	
	/**
	 * Updates Paraphrase to the map
	 */
	private void addParaPharse(String paraPharse){
		if(CollectionUtils.isEmpty(this.details)){
			this.details = new LinkedHashMap<String, Object>(5);
		}
		if(!StringUtils.hasText(paraPharse))
			return;
		this.details.put(AccessTokenFields.ENCRYPT_STARTEGY, paraPharse);
	}

}
