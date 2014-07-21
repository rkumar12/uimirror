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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * <p>This holds the information regarding the api access information.</p>
 * @author Jayaram
 */
public class ClientAccess implements Serializable{

	private static final long serialVersionUID = -7517381066832518298L;
	private final long id;
	private final long accssedOn;
	private final String requestedFor;
	private final String ip;
	private final RequestStatus requestStatus;
	
	/**
	 *<p>This will populate the Map from the details available</p> 
	 */
	public Map<String, String> serailizeToDoucmentMap() {
		Map<String, String> map = new HashMap<String, String>(6,1);
		
		map.put(SecurityFieldConstants._ACCESSED_ON, String.valueOf(this.accssedOn));
		
		if(StringUtils.isNotBlank(this.requestedFor)){
			map.put(SecurityFieldConstants._REQUESTED_FOR, this.requestedFor);
		}
		if(StringUtils.isNotBlank(this.ip)){
			map.put(SecurityFieldConstants._IP, this.ip);
		}
		if(this.requestStatus != null){
			map.put(SecurityFieldConstants._REQUESTED_STATUS, this.requestStatus.getStatus());
		}
		if(this.id > 0l){
			map.put(SecurityFieldConstants._ID, String.valueOf(this.id));
		}
		return map;
	}
	
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
		this.id = 0l;
	}

	/**
	 * @param accssedOn
	 * @param requestedFor
	 * @param ip
	 * @param requestStatus
	 */
	public ClientAccess(long id, long accssedOn, String requestedFor, String ip, RequestStatus requestStatus) {
		super();
		this.id = id;
		this.accssedOn = accssedOn;
		this.requestedFor = requestedFor;
		this.ip = ip;
		this.requestStatus = requestStatus;
	}
	
	/**
	 * <p>This will convert a map to the ClientAccess object</p>
	 * <p>While deserailizing it verifies if it has any id associated with it or not, if not
	 * <code>throws new {@link IllegalArgumentException}</code></p>
	 * @param data
	 * @return a new instance of <code>{@link ClientAccess}</code>
	 */
	public static ClientAccess deserailizeToObject(Map<String, String> data){
		if(CollectionUtils.isEmpty(data) || !StringUtils.isNumeric(data.get(SecurityFieldConstants._ID))){
			throw new IllegalArgumentException("Data Can't be Deserailized as it has no data.");
		}
		return new ClientAccess(Long.valueOf(data.get(SecurityFieldConstants._ID)), 
				Long.valueOf(data.get(SecurityFieldConstants._ACCESSED_ON)), 
				data.get(SecurityFieldConstants._REQUESTED_FOR), 
				data.get(SecurityFieldConstants._IP), 
				RequestStatus.getEnum(data.get(SecurityFieldConstants._REQUESTED_STATUS)));
	}
	
	/**
	 * <p>This will update the document ID.</p>
	 * @param id
	 * @return
	 */
	public ClientAccess updateId(long id){
		return new ClientAccess(this.id, this.accssedOn, this .requestedFor, this.ip, requestStatus);
	}
	
	/**
	 * <p>This will update the request status for the request made.</p>
	 * @param requestStatus
	 * @return
	 */
	public ClientAccess updateRequestStatus(RequestStatus requestStatus){
		return new ClientAccess(this.id, this.accssedOn, this .requestedFor, this.ip, requestStatus);
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
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
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
		return "ClientAccess [id=" + id + ", accssedOn=" + accssedOn
				+ ", requestedFor=" + requestedFor + ", ip=" + ip
				+ ", requestStatus=" + requestStatus + "]";
	}
	
}
