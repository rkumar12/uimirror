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
package com.uimirror.ws.api.security.base;

import java.io.Serializable;
import java.security.Principal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * <p>Bean to hold all the client Created Information</p>
 * <p>Class has been marked as immutable, leaving the modifier as final to enable other users
 * to extend it, but please note, any body wants to extend it take proper care to make this as immutable</p>
 * 
 * @author Jayaram
 */
public class Client implements Serializable, Principal{
	
	private static final long serialVersionUID = -4993504324270707065L;
	private final String apiKey;
	private final ClientDetails clientDetails;
	private final long createdOn;
	private final Set<Role> roles;
	private final boolean isActive;
	/**
	 * <p>First Time Creating a Client will have to populate the client details along with the
	 * role, other details are system populated.</p>
	 * @param clientDetails
	 * @param roles
	 * @param isActive
	 */
	public Client(ClientDetails clientDetails, Set<Role> roles) {
		super();
		this.clientDetails = clientDetails;
		this.roles = (roles == null ? new HashSet<Role>() : roles);
		this.apiKey = UUID.randomUUID().toString();
		this.createdOn = Instant.now().toEpochMilli();
		this.isActive = Boolean.TRUE;
	}
	/**
	 * <p>Constructor to populate all the fields when de-serializing.</p>
	 * @param apiKey
	 * @param clientDetails
	 * @param createdOn
	 * @param roles
	 * @param isActive
	 */
	public Client(String apiKey, ClientDetails clientDetails, long createdOn,
			Set<Role> roles, boolean isActive) {
		super();
		this.apiKey = apiKey;
		this.clientDetails = clientDetails;
		this.createdOn = createdOn;
		this.roles = roles;
		this.isActive = isActive;
	}
	
	/**
	 * <p>This will add a new role for the client</p>
	 * <p>If the role is already present it will not do anything</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client addRole(String role){
		this.roles.add(Role.getEnum(role));
		return new Client(this.apiKey, this.clientDetails, this.createdOn, this.roles, this.isActive);
	}
	
	/**
	 * <p>This will revoke the role for the client</p>
	 * <p>If the role is already present it will remove else will not do anything</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client revokeRole(String role){
		this.roles.remove(Role.getEnum(role));
		return new Client(this.apiKey, this.clientDetails, this.createdOn, this.roles, this.isActive);
	}
	
	/**
	 * <p>This will update the active status of the client.</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client updateActiveStatus(boolean status){
		return new Client(this.apiKey, this.clientDetails, this.createdOn, this.roles, status);
	}
	
	/**
	 * <p>This will update the api Key of the client.</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client regenrateApiKey(){
		return new Client(UUID.randomUUID().toString(), this.clientDetails, this.createdOn, this.roles, this.isActive);
	}
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	/**
	 * @return the clientDetails
	 */
	public ClientDetails getClientDetails() {
		return clientDetails;
	}
	/**
	 * @return the createdOn
	 */
	public long getCreatedOn() {
		return createdOn;
	}
	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
		result = prime * result + (int) (createdOn ^ (createdOn >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (apiKey == null) {
			if (other.apiKey != null)
				return false;
		} else if (!apiKey.equals(other.apiKey))
			return false;
		if (createdOn != other.createdOn)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Client [apiKey=" + apiKey + ", clientDetails=" + clientDetails
				+ ", createdOn=" + createdOn + ", roles=" + roles
				+ ", isActive=" + isActive + "]";
	}
	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getApiKey();
	}

	
}
