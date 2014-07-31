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
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Bean to hold all the client Created Information</p>
 * <p>Class has been marked as immutable, leaving the modifier as final to enable other users
 * to extend it, but please note, any body wants to extend it take proper care to make this as immutable</p>
 * 
 * @author Jayaram
 */
public class Client implements Serializable, Principal{
	
	private static final long serialVersionUID = -4993504324270707065L;
	private final long id;
	private final String apiKey;
	private final ClientDetails clientDetails;
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
		if(this.clientDetails == null){
			throw new IllegalArgumentException("Client Details Can't be empty.");
		}
		this.clientDetails = clientDetails;
		this.roles = (roles == null ? new HashSet<Role>() : roles);
		this.apiKey = UUID.randomUUID().toString();
		this.isActive = Boolean.TRUE;
		this.id = 0l;
	}
	
	/**
	 * <p>Constructor to populate all the fields when de-serializing.</p>
	 * @param id
	 * @param apiKey
	 * @param clientDetails
	 * @param roles
	 * @param isActive
	 */
	protected Client(long id, String apiKey, ClientDetails clientDetails,
			Set<Role> roles, boolean isActive) {
		super();
		this.apiKey = apiKey;
		this.clientDetails = clientDetails;
		this.roles = roles;
		this.isActive = isActive;
		this.id = id;
	}
	
	/**
	 * <p>This will add a new role for the client</p>
	 * <p>If the role is already present it will not do anything</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client addRole(String role){
		if(StringUtils.isBlank(role)){
			throw new IllegalArgumentException("Role Can't be invalid");
		}
		this.roles.add(Role.getEnum(role));
		return new Client(this.id, this.apiKey, this.clientDetails, this.roles, this.isActive);
	}
	
	/**
	 * <p>This will revoke the role for the client</p>
	 * <p>If the role is already present it will remove else will not do anything</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client revokeRole(String role){
		if(StringUtils.isBlank(role)){
			throw new IllegalArgumentException("Role Can't be invalid");
		}
		this.roles.remove(Role.getEnum(role));
		return new Client(this.id, this.apiKey, this.clientDetails, this.roles, this.isActive);
	}
	
	/**
	 * <p>This will update the active status of the client.</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client updateActiveStatus(boolean status){
		return new Client(this.id, this.apiKey, this.clientDetails, this.roles, status);
	}
	
	/**
	 * <p>This will update the api Key of the client.</p>
	 * 
	 * @param role
	 * @return new instance of <code>{@link Client#Client(String, ClientDetails, long, Set, boolean)}</code>
	 */
	public Client regenrateApiKey(){
		return new Client(this.id, UUID.randomUUID().toString(), this.clientDetails, this.roles, this.isActive);
	}
	/**
	 * <p>This will update the client details.</p>
	 * @return
	 */
	public Client updateClientDetails(ClientDetails clientDetails){
		if(clientDetails == null){
			throw new IllegalArgumentException("Client Details can't be empty");
		}
		return new Client(clientDetails.getClientId(), this.apiKey, clientDetails, this.roles, this.isActive);
	}
	public Client updateClientId(long id){
		if(id < 0l){
			throw new IllegalArgumentException("Client Id is not valid");
		}
		return new Client(id, this.apiKey, this.clientDetails, this.roles, this.isActive);
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
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
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
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Client [id=" + id + ", apiKey=" + apiKey + ", clientDetails="
				+ clientDetails + ", roles=" + roles + ", isActive=" + isActive
				+ "]";
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
