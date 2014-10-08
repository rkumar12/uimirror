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

import com.uimirror.auth.bean.CommonAuthentication;
import com.uimirror.auth.bean.CredentialType;
import com.uimirror.core.auth.Scope;

/**
 * When Client request for a authorization request,
 * it expects it should have below parameters to be present
 * grant_type=authorization_code&
 *   code=AUTH_CODE_HERE&
 *   redirect_uri=REDIRECT_URI&
 *   client_id=CLIENT_ID&
 *   client_secret=CLIENT_SECRET
 * @author Jay
 */
public class ClientTokenAuthentication extends CommonAuthentication {

	private String clientId;
	private String clientSecret;
	private String code;
	private String redirectUri;
	
	private ClientTokenAuthentication(String password, String ip, String uAgent){
		super(CredentialType.CLIENTSECRECTKEY, ip, uAgent);
	}
	private ClientTokenAuthentication(String password, Map<String, Object> details, String ip, String uAgent){
		super(CredentialType.CLIENTSECRECTKEY, details, ip, uAgent);
	}
	private ClientTokenAuthentication(String password, Map<String, Object> details) {
		super(CredentialType.CLIENTSECRECTKEY, details);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#keepMeLogin()
	 */
	@Override
	public boolean keepMeLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.auth.bean.Authentication#getAuthenticationScope()
	 */
	@Override
	public Scope getAuthenticationScope() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
