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
package com.uimirror.sso.auth;

import java.util.Map;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;
import com.uimirror.sso.Approval;

/**
 * AccessToken <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * accessToken=codeAMP;
 * tokenEncryptionStartegy=parapharse
 * AMP;scope=rAMP;approval=A
 *  
 * @author Jay
 */
public class ClientAuthorizationAuthentication extends OAuth2Authentication{
	
	private static final long serialVersionUID = 3795112886906141341L;
	
	/**
	 * @param token as parameter
	 * @param scope as parameter 
	 * @param approval as parameter
	 */
	public ClientAuthorizationAuthentication(String token, Scope scope, Approval approval) {
		super(token);
		init(scope, approval);
	}

	/**
	 * @param token as parameter
	 * @param scope as parameter
	 * @param approval as parameter
	 * @param ip as parameter
	 * @param userAgent as parameter
	 */
	public ClientAuthorizationAuthentication(String token, Scope scope, Approval approval, String ip, String userAgent) {
		super(token, ip, userAgent);
		init(scope, approval);
	}

	/**
	 * @param tokenPrincipal as first parameter
	 * @param details as second parameter
	 */
	public ClientAuthorizationAuthentication(Object tokenPrincipal, Map<String, Object> details) {
		super(tokenPrincipal, details);
	}
	
	/**
	 * @param tokenPrincipal as parameter
	 */
	public ClientAuthorizationAuthentication(Object tokenPrincipal) {
		super(tokenPrincipal);
	}
	
	/**
	 * @param scope
	 * @param approval
	 */
	private void init(Scope scope, Approval approval){
		addDetails(scope, approval);
	}
	
	/**
	 * @param scope
	 * @param approval
	 */
	@SuppressWarnings("unchecked")
	private void addDetails(Scope scope, Approval approval){
		Map<String, Object> details = (Map<String, Object>)getDetails();
		details.put(AuthConstants.SCOPE, scope);
		details.put(AuthConstants.APPROVAL, approval);
		setDetails(details);
	}
	
}
