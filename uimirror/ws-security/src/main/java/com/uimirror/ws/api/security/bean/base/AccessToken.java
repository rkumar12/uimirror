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

import org.springframework.util.Assert;

import com.uimirror.ws.api.security.Scope;
import com.uimirror.ws.api.security.ouath.UIMPrincipal;

/**
 * <p>This holds the principal for the security context</p>
 * @author Jay
 *
 */
public final class AccessToken implements Serializable, UIMPrincipal{

	private static final long serialVersionUID = 1920019552923852689L;
	private final String token;
	private final ZonedDateTime grantedOn;
	private final ZonedDateTime expiresOn;
	private final Scope scope;
	private Client client;
	private User user;

	public AccessToken(String token, ZonedDateTime grantedOn, ZonedDateTime expiresOn, Scope scope, Client client, User user) {
		super();
		this.token = token;
		this.grantedOn = grantedOn;
		this.expiresOn = expiresOn;
		this.scope = scope;
		this.client = client;
		this.user = user;
	}

	@Override
	public String getName() {
		this.validateContext();
		return this.token;
	}

	@Override
	public String getUserId() {
		this.validateContext();
		return this.user.getId();
	}

	@Override
	public String getClientId() {
		this.validateContext();
		return this.client.getId();
	}
	
	@Override
	public String getClientApiKey() {
		this.validateContext();
		return this.client.getApiKey();
	}

	@Override
	public Scope getScope() {
		this.validateContext();
		return this.scope;
	}

	@Override
	public Client getClient() {
		this.validateContext();
		return this.client;
	}

	@Override
	public User getUser() {
		this.validateContext();
		return this.user;
	}

	@Override
	public ZonedDateTime getTokenCreationDate() {
		this.validateContext();
		return this.grantedOn;
	}

	@Override
	public ZonedDateTime getExpiresOn() {
		this.validateContext();
		return this.expiresOn;
	}
	
	private void validateContext(){
		Assert.hasText(this.token, "No Security context available");
		Assert.notNull(this.user, "No Security context available");
		Assert.notNull(this.client, "No Security context available");
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
	
}
