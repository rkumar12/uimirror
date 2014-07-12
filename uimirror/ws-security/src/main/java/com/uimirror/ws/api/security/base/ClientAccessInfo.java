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
import java.util.Set;

/**
 * <p>This will keep track of the client access to API.</p>
 * @author Jayaram
 */
public final class ClientAccessInfo implements Serializable{

	private static final long serialVersionUID = 3997740312315595859L;
	private final String clientId;
	private final Set<ClientAccess> accessDetails;
	private final String forMonth;
	private final boolean isFullyLoaded;
	
	/**
	 * @param clientId
	 * @param accessDetails
	 * @param forMonth
	 */
	public ClientAccessInfo(String clientId, Set<ClientAccess> accessDetails, String forMonth) {
		super();
		this.clientId = clientId;
		this.accessDetails = accessDetails;
		this.forMonth = forMonth;
		this.isFullyLoaded = Boolean.FALSE;
	}

	/**
	 * @param clientId
	 * @param accessDetails
	 * @param forMonth
	 * @param isFullyLoaded
	 */
	public ClientAccessInfo(String clientId, Set<ClientAccess> accessDetails, String forMonth, boolean isFullyLoaded) {
		super();
		this.clientId = clientId;
		this.accessDetails = accessDetails;
		this.forMonth = forMonth;
		this.isFullyLoaded = isFullyLoaded;
	}
	/**
	 * <p>This will update the loading status for the client for a specific month.</p>
	 * @param status
	 * @return
	 */
	public ClientAccessInfo updateLoadingStatus(boolean status){
		return new ClientAccessInfo(this.clientId, this.accessDetails, this.forMonth, status);
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @return the accessDetails
	 */
	public Set<ClientAccess> getAccessDetails() {
		return accessDetails;
	}

	/**
	 * @return the forMonth
	 */
	public String getForMonth() {
		return forMonth;
	}

	/**
	 * @return the isFullyLoaded
	 */
	public boolean isFullyLoaded() {
		return isFullyLoaded;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result
				+ ((forMonth == null) ? 0 : forMonth.hashCode());
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
		ClientAccessInfo other = (ClientAccessInfo) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (forMonth == null) {
			if (other.forMonth != null)
				return false;
		} else if (!forMonth.equals(other.forMonth))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClientAccessInfo [clientId=" + clientId + ", accessDetails="
				+ accessDetails + ", forMonth=" + forMonth + ", isFullyLoaded="
				+ isFullyLoaded + "]";
	}
	
}
