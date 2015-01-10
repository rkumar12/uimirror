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

import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.token.AccessTokenFields;
import com.uimirror.sso.AbstractAuthentication;

/**
 * Client APIKey for <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * response_type=codeAMP;client_id=CLIENT_IDAMP;redirect_uri=REDIRECT_URIAMP;scope=read
 * 
 * @author Jay
 */
public final class APIKeyAuthentication extends AbstractAuthentication{

	private static final long serialVersionUID = 347196781678243458L;

	private AccessToken token;
	
	/**
	 * @param tokenPrincipal authenticated token principal
	 * @param details details associated with this token
	 */
	public APIKeyAuthentication(AccessToken tokenPrincipal, Map<String, Object> details) {
		this.token = tokenPrincipal;
		setDetails(details);
		setAuthenticated(Boolean.TRUE);
	}
	/**
	 * @param tokenPrincipal authenticated token principal
	 */
	public APIKeyAuthentication(AccessToken tokenPrincipal) {
		this.token = tokenPrincipal;
		setAuthenticated(Boolean.TRUE);
	}
	
	/**
	 * Along with the basic details, it will populate the client redirect URI and client ID
	 * @param redirectUrl
	 * @param apiKey
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
	
	public static class APIKeyBuilder implements Builder<APIKeyAuthentication>{
		
		private String apiKey;
		private String redirectUrl; 
		private Scope scope; 
		private String ip; 
		private String userAgent;
		
		public APIKeyBuilder(String apiKey){
			this.apiKey = apiKey;
		}
		
		public APIKeyBuilder addRedirectURI(String redirectUri){
			this.redirectUrl = redirectUri;
			return this;
		}
		
		public APIKeyBuilder addScope(String scope){
			if(!StringUtils.hasText(scope))
				throw new IllegalArgumentException("Scope is invalid.");
			this.scope = Scope.getEnum(scope);
			return this;
		}
		public APIKeyBuilder addScope(Scope scope){
			this.scope = scope;
			return this;
		}
		public APIKeyBuilder addIP(String ip){
			this.ip = ip;
			return this;
		}
		public APIKeyBuilder addUserAgent(String agent){
			this.userAgent = agent;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public APIKeyAuthentication build() {
			return new APIKeyAuthentication(this);
		}
		
	}
	
	private APIKeyAuthentication(APIKeyBuilder builder){
		super(builder.ip, builder.userAgent);
		initDetails(builder.redirectUrl, builder.apiKey, builder.scope);
	}

}
