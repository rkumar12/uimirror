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
package com.uimirror.account.auth.client.bean;

import java.util.Map;

import com.uimirror.account.auth.bean.AbstractAuthentication;
import com.uimirror.account.auth.core.AccessTokenFields;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;

/**
 * Client APIKey for <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * response_type=code&
 * client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&scope=read
 * 
 * @author Jay
 */
public class APIKeyAuthentication extends AbstractAuthentication{

	private static final long serialVersionUID = 347196781678243458L;
	private Object token;

	/**
	 * @param clientId
	 * @param redirectUrl
	 * @param scope
	 */
	public APIKeyAuthentication(String clientId, String redirectUrl, Scope scope) {
		init(clientId, redirectUrl, scope);
	}
	/**
	 * @param clientId
	 * @param redirectUrl
	 * @param scope
	 * @param ip
	 * @param userAgent
	 */
	public APIKeyAuthentication(String clientId, String redirectUrl, Scope scope, String ip, String userAgent) {
		super(ip, userAgent);
		init(clientId, redirectUrl, scope);
	}
	
	/**
	 * @param tokenPrincipal
	 * @param details
	 */
	public APIKeyAuthentication(Object tokenPrincipal, Map<String, Object> details) {
		this.token = tokenPrincipal;
		setDetails(details);
		setAuthenticated(Boolean.TRUE);
	}
	/**
	 * @param tokenPrincipal
	 */
	public APIKeyAuthentication(Object tokenPrincipal) {
		this.token = tokenPrincipal;
		setAuthenticated(Boolean.TRUE);
	}
	
	/**
	 * @param clientId
	 * @param redirectUrl
	 * @param scope
	 */
	private void init(String clientId, String redirectUrl, Scope scope){
		initDetails(redirectUrl, clientId, scope);
	}
	
	/**
	 * Along with the basic details, it will populate the client redirect URI and client ID
	 * @param redirectUrl
	 * @param clientId
	 * @param scope
	 */
	private void initDetails(String redirectUrl, String clientId, Scope scope){
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)getDetails();
		details.put(AuthConstants.REDIRECT_URI, redirectUrl);
		details.put(AuthConstants.CLIENT_ID, clientId);
		details.put(AuthConstants.SCOPE, scope);
		setDetails(details);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.Authentication#getPrincipal()
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
	private String computePrincipalOwner(){
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>) getDetails();
		String owner = null;
		owner = (String)details.get(AuthConstants.CLIENT_ID);
		if(owner == null){
			owner = (String)details.get(AccessTokenFields.AUTH_TKN_OWNER);
		}
		return owner;
	}

	@Override
	public String toString() {
		return "OAuth2APIKeyAuthentication [token=" + token + "]";
	}

}
