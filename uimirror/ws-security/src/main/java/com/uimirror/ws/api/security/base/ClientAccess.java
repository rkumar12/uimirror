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
import java.time.Instant;

/**
 * <p>This holds the information regarding the api access information.</p>
 * @author Jayaram
 */
public class ClientAccess implements Serializable{

	private static final long serialVersionUID = -7517381066832518298L;
	private final long accssedOn;
	private final String requestedFor;
	private final String ip;
	private final RequestStatus requestStatus;
	
	/**
	 * @param requestedFor
	 * @param ip
	 */
	public ClientAccess(String requestedFor, String ip) {
		super();
		this.requestedFor = requestedFor;
		this.ip = ip;
		this.requestStatus = RequestStatus.REQUESTED;
		this.accssedOn = Instant.now().toEpochMilli();
	}

	/**
	 * @param accssedOn
	 * @param requestedFor
	 * @param ip
	 * @param requestStatus
	 */
	public ClientAccess(long accssedOn, String requestedFor, String ip, RequestStatus requestStatus) {
		super();
		this.accssedOn = accssedOn;
		this.requestedFor = requestedFor;
		this.ip = ip;
		this.requestStatus = requestStatus;
	}
	
	/**
	 * <p>This will update the request status for the request made.</p>
	 * @param requestStatus
	 * @return
	 */
	public ClientAccess updateRequestStatus(RequestStatus requestStatus){
		return new ClientAccess(this.accssedOn, this .requestedFor, this.ip, requestStatus);
	}

	/**
	 * @return the accssedOn
	 */
	public long getAccssedOn() {
		return accssedOn;
	}

	/**
	 * @return the requestedFor
	 */
	public String getRequestedFor() {
		return requestedFor;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @return the requestStatus
	 */
	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accssedOn ^ (accssedOn >>> 32));
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((requestedFor == null) ? 0 : requestedFor.hashCode());
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
		ClientAccess other = (ClientAccess) obj;
		if (accssedOn != other.accssedOn)
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (requestedFor == null) {
			if (other.requestedFor != null)
				return false;
		} else if (!requestedFor.equals(other.requestedFor))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClientAccess [accssedOn=" + accssedOn + ", requestedFor="
				+ requestedFor + ", ip=" + ip + ", requestStatus="
				+ requestStatus + "]";
	}
	
}
