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

import java.util.LinkedHashMap;
import java.util.Map;

import com.uimirror.core.Builder;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.token.AccessTokenFields;
import com.uimirror.sso.AbstractAuthentication;

/**
 * Client Accesstoken for <code>Authentication</code> objects.
 * <p>
 * Implementations which use this class should be immutable.
 * Should accomodate the following details
 * code=AUTH_CODE_HERE&
 * redirect_uri=REDIRECT_URI&
 * client_id=CLIENT_ID&
 * client_secret=CLIENT_SECRET
 * 
 * @author Jay
 */
public class OAuth2SecretKeyAuthentication extends AbstractAuthentication{

	private static final long serialVersionUID = 347196781678243458L;
	private Map<String, String> credentials; 
	private Object token;

	/* (non-Javadoc)
	 * @see com.uimirror.account.auth.bean.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return credentials;
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
	
	public static class OAuth2SecretKeyBuilder implements Builder<OAuth2SecretKeyAuthentication>{
		private String code;
		private String redirectUrl;
		private String apiKey;
		private String clientSecret;
		private String ip;
		private String userAgent;
		
		public OAuth2SecretKeyBuilder(String code){
			this.code = code;
		}
		
		public OAuth2SecretKeyBuilder addRedirectURI(String redirectURI){
			this.redirectUrl = redirectURI;
			return this;
		}
		
		public OAuth2SecretKeyBuilder addApiKey(String apiKey){
			this.apiKey = apiKey;
			return this;
		}
		public OAuth2SecretKeyBuilder addClientSecret(String clientSecret){
			this.clientSecret = clientSecret;
			return this;
		}
		public OAuth2SecretKeyBuilder addIp(String ip){
			this.ip = ip;
			return this;
		}
		public OAuth2SecretKeyBuilder addAgent(String userAgent){
			this.userAgent = userAgent;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public OAuth2SecretKeyAuthentication build() {
			return new OAuth2SecretKeyAuthentication(this);
		}
	}
	
	private OAuth2SecretKeyAuthentication(OAuth2SecretKeyBuilder builder){
		super(builder.ip, builder.userAgent);
		init(builder.code, builder.redirectUrl, builder.apiKey, builder.clientSecret);
	}
	
	public OAuth2SecretKeyAuthentication(Object tokenPrincipal, Map<String, Object> details) {
		this.token = tokenPrincipal;
		setDetails(details);
	}
	
	private void init(String code, String redirectUrl, String clientId, String clientSecret){
		initCredentials(code, clientSecret);
		initDetails(redirectUrl, clientId);
	}
	
	/**
	 * Populate the credentials for this authentication
	 * This will have two key {@linkplain AuthConstants.CLIENT_SECRET_CODE}
	 * and {@linkplain AuthConstants.CLIENT_SECRET}
	 * 
	 * @param code
	 * @param clientSecret
	 */
	private void initCredentials(String code, String clientSecret){
		credentials = new LinkedHashMap<String, String>(5);
		credentials.put(AuthConstants.CLIENT_SECRET_CODE, code);
		credentials.put(AuthConstants.CLIENT_SECRET, clientSecret);
	}
	
	/**
	 * Along with the basic details, it will populate the client redirect URI and client ID
	 * @param redirectUrl
	 * @param apiKey
	 */
	private void initDetails(String redirectUrl, String clientId){
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)getDetails();
		details.put(AuthConstants.REDIRECT_URI, redirectUrl);
		details.put(AuthConstants.CLIENT_ID, clientId);
		setDetails(details);
	}

}
