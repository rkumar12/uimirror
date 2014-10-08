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
package com.uimirror.auth.client.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import com.uimirror.auth.bean.AbstractAuthentication;
import com.uimirror.auth.core.AccessTokenFields;
import com.uimirror.core.auth.AuthConstants;

/**
 * AccessToken <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * accessToken=code&
 * tokenEncryptionStartegy=parapharse
 * 
 * @author Jay
 */
public class OAuth2Authentication extends AbstractAuthentication{

	private static final long serialVersionUID = 347196781678243458L;
	private Map<String, Object> token;
	private Map<String, String> credentials;

	public OAuth2Authentication(String token) {
		init(token);
	}
	public OAuth2Authentication(String token, String ip, String userAgent) {
		super(ip, userAgent);
		init(token);
	}
	
	public OAuth2Authentication(Map<String, Object> tokenPrincipal, Map<String, Object> details) {
		this.token = tokenPrincipal;
		setDetails(details);
	}
	
	private void init(String token){
		initCredentials(token);
	}
	
	/**
	 * Populate the credentials for this authentication
	 * This will have two key {@linkplain AuthConstants.ACCESS_TOKEN}
	 * 
	 * @param code
	 * @param clientSecret
	 */
	private void initCredentials(String token){
		credentials = new LinkedHashMap<String, String>(5);
		credentials.put(AuthConstants.ACCESS_TOKEN, token);
	}
	
	/**
	 * Any subclass who wants to update the credentials can populate here
	 */
	protected void updateCredentials(Map<String, String> credentials){
		this.credentials = credentials;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return credentials;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.Authentication#getPrincipal()
	 */
	@Override
	public Object getPrincipal() {
		return token;
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return computePrincipalOwner();
	}
	
	/**
	 * Compute the owner for this Authentication
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String computePrincipalOwner(){
		String owner = null;
		owner = ((Map<String, String>)getCredentials()).get(AuthConstants.ACCESS_TOKEN);
		if(owner == null){
			Map<String, Object> details = (Map<String, Object>) getDetails();
			owner = (String)details.get(AccessTokenFields.AUTH_TKN_OWNER);
		}
		return owner;
	}

}
