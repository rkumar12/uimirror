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
package com.uimirror.ws.api.security.bean.base;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.util.Assert;

import com.uimirror.mongo.feature.BeanBasedDocument;
import com.uimirror.ws.api.security.ReadWriteScope;
import com.uimirror.ws.api.security.Scope;
import com.uimirror.ws.api.security.common.SecurityFieldConstants;
import com.uimirror.ws.api.security.ouath.UIMPrincipal;

/**
 * <p>This holds the principal for the security context</p>
 * @author Jay
 *
 */
public final class AccessToken extends BeanBasedDocument implements Serializable, UIMPrincipal{

	private static final long serialVersionUID = 1920019552923852689L;
	private final String token;
	private final ZonedDateTime grantedOn;
	private final long expiresOn;
	private final Scope scope;
	private Client client;
	private User user;
	private final String clientId;
	private final String userId;

	/**
	 * <p>This will populate the AccessToken with token as principal only</p>
	 * <p>Note***Don't treat Access Token object has token means its authorized</p>
	 * <p>This has been added to avoid data base call to each and every request, rather when required it will populate the principal</p>
	 */
	public AccessToken(String token) {
		this.token = token;
		this.grantedOn = null;
		this.expiresOn = 0l;
		this.scope = null;
		this.client = null;
		this.user = null;
		this.clientId = null;
		this.userId = null;
	}
	/**
	 * @param token
	 * @param grantedOn
	 * @param expiresOn
	 * @param scope
	 * @param client
	 * @param user
	 */
	public AccessToken(String token, ZonedDateTime grantedOn, long expiresOn, Scope scope, Client client, User user) {
		super(8);
		this.token = token;
		this.grantedOn = grantedOn;
		this.expiresOn = expiresOn;
		this.scope = scope;
		this.client = client;
		this.clientId = this.client.getId();
		this.user = user;
		this.userId = this.user.getId();
		intialize();
	}
	
	/**
	 * @param token
	 * @param grantedOn
	 * @param expiresOn
	 * @param scope
	 * @param clientId
	 * @param userId
	 */
	public AccessToken(String token, ZonedDateTime grantedOn, long expiresOn, Scope scope, String clientId, String userId) {
		super(8);
		this.token = token;
		this.grantedOn = grantedOn;
		this.expiresOn = expiresOn;
		this.scope = scope;
		this.clientId = clientId;
		this.userId = userId;
		intialize();
	}
	
	/**
	 * <p>It will de-serialize from the map to bean</p>
	 * <p>If the map doesn't have sufficient(As in all required final fields) keys, it might through {@link NullPointerException}, {@link ClassCastException} or {@link IllegalArgumentException}</p>
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	public AccessToken(Map<String, Object> m) {
		super(m);
		Assert.notEmpty(m, "Object Can't be Deserailized");
		String token = (String)m.get(SecurityFieldConstants._ID);
		Map<String, Object> scope = (Map<String, Object>)m.get(SecurityFieldConstants._CLIENT_ACCESS_SCOPE);
		Assert.hasText(token, "Access Token Doesn't have a valid token");
		Assert.notEmpty(scope, "Access Token doesn't have a valid scope");
		int appCode = (int)scope.get(SecurityFieldConstants._APP);
		String grantedAuthority = (String)scope.get(SecurityFieldConstants._CLIENT_ACCESS_READ_WRITE);
		this.token = token;
		this.grantedOn = (ZonedDateTime)m.get(SecurityFieldConstants._CLIENT_ACCESS_GRANTED_ON);
		this.expiresOn = (long)m.get(SecurityFieldConstants._CLIENT_ACCESS_EXPIRIES_ON);
		this.scope = new Scope(appCode, ReadWriteScope.getEnum(grantedAuthority));
		this.clientId = (String)m.get(SecurityFieldConstants._CLIENT_ACCESS_CLIENT_ID);
		this.userId = (String)m.get(SecurityFieldConstants._CLIENT_ACCESS_USER_ID);
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.token;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getUserId()
	 */
	@Override
	public String getUserId() {
		Assert.hasText(this.userId, "No Security context available");
		return this.userId;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getClientId()
	 */
	@Override
	public String getClientId() {
		Assert.hasText(this.clientId, "No Security context available");
		return this.clientId;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getClientApiKey()
	 */
	@Override
	public String getClientApiKey() {
		Assert.notNull(this.client, "No Security context available");
		return this.client.getApiKey();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getScope()
	 */
	@Override
	public Scope getScope() {
		return this.scope;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getClient()
	 */
	@Override
	public Client getClient() {
		return this.client;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getUser()
	 */
	@Override
	public User getUser() {
		return this.user;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getTokenCreationDate()
	 */
	@Override
	public ZonedDateTime getTokenCreationDate() {
		return this.grantedOn;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.ouath.UIMPrincipal#getExpiresOn()
	 */
	@Override
	public long getExpiresOn() {
		return this.expiresOn;
	}
	
	/**
	 * <p>This will update the client details to token</p>
	 * @param client
	 * @return
	 */
	public AccessToken updateClient(Client client) {
		return new AccessToken(this.token, this.grantedOn, this.expiresOn, this.scope, client, this.user);
	}
	
	/**
	 * <p>This will update the user to the token</p>
	 * @param usr
	 * @return
	 */
	public AccessToken updateUser(User usr){
		return new AccessToken(this.token, this.grantedOn, this.expiresOn, this.scope, this.client, user);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessToken other = (AccessToken) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccessToken [token=" + token + ", grantedOn=" + grantedOn
				+ ", expiresOn=" + expiresOn + ", scope=" + scope + ", client="
				+ client + ", user=" + user + "]";
	}
	
	/**
	 * <p>Initialize to the DB Map Object</p>
	 */
	private void intialize(){
		super.put(SecurityFieldConstants._ID, this.token);
		super.put(SecurityFieldConstants._CLIENT_ACCESS_GRANTED_ON, this.grantedOn);
		super.put(SecurityFieldConstants._CLIENT_ACCESS_EXPIRIES_ON, this.expiresOn);
		super.put(SecurityFieldConstants._CLIENT_ACCESS_SCOPE, this.scope);
		super.put(SecurityFieldConstants._CLIENT_ACCESS_CLIENT_ID, this.clientId);
		super.put(SecurityFieldConstants._CLIENT_ACCESS_USER_ID, this.userId);
	}
	
}
