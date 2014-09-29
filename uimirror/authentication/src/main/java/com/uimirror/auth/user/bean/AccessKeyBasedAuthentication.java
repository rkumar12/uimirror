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

import com.uimirror.core.auth.AccessTokenFields;
import com.uimirror.core.auth.AuthenticationManager;
import com.uimirror.core.auth.bean.Authentication;
import com.uimirror.core.auth.bean.CommonAuthentication;
import com.uimirror.core.auth.bean.CredentialType;
import com.uimirror.core.auth.bean.Scope;

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
	private String paraPharse;
	private Map<String, Object> details;

	/**
	 * @param type
	 * @param ip
	 * @param uAgent
	 */
	private AccessKeyBasedAuthentication(CredentialType type, String ip, String uAgent) {
		super(type, ip, uAgent);
	}
	
	public AccessKeyBasedAuthentication(String accessToken, String paraPharse, Map<String, Object> details, String ip, String uAgent) {
		this(CredentialType.ACCESSKEY, ip, uAgent);
		Assert.hasText(accessToken, "User Identifier can't be empty");
		this.details = details;
		this.accessToken = accessToken;
		this.paraPharse = paraPharse;
		addParaPharse();
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
	private void addParaPharse(){
		if(CollectionUtils.isEmpty(details)){
			details = new LinkedHashMap<String, Object>(2);
		}
		details.put(AccessTokenFields.ENCRYPT_STARTEGY, paraPharse);
	}

}
