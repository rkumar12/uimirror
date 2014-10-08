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

import java.util.Map;

import com.uimirror.auth.bean.AbstractAuthentication;
import com.uimirror.auth.core.AccessTokenFields;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;

/**
 * Client APIKey for <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * response_type=code&
 * client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&scope=read&app=rti
 * 
 * @author Jay
 */
public class OAuth2APIKeyAuthentication extends AbstractAuthentication{

	private static final long serialVersionUID = 347196781678243458L;
	private Map<String, Object> token;

	public OAuth2APIKeyAuthentication(String clientId, String redirectUrl, String scope, String app) {
		init(clientId, redirectUrl, scope, app);
	}
	public OAuth2APIKeyAuthentication(String clientId, String redirectUrl, String scope, String app, String ip, String userAgent) {
		super(ip, userAgent);
		init(clientId, redirectUrl, scope, app);
	}
	
	public OAuth2APIKeyAuthentication(Map<String, Object> tokenPrincipal, Map<String, Object> details) {
		this.token = tokenPrincipal;
		setDetails(details);
	}
	
	private void init(String clientId, String redirectUrl, String scope, String app){
		initDetails(redirectUrl, clientId, scope, app);
	}
	
	/**
	 * Along with the basic details, it will populate the client redirect URI and client ID
	 * @param redirectUrl
	 * @param clientId
	 * @param scope
	 * @param app
	 */
	private void initDetails(String redirectUrl, String clientId, String scope, String app){
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)getDetails();
		details.put(AuthConstants.REDIRECT_URI, redirectUrl);
		details.put(AuthConstants.CLIENT_ID, clientId);
		details.put(AuthConstants.SCOPE, Scope.getEnum(scope));
		details.put(AuthConstants.APP, app);
		setDetails(details);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return null;
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

}
