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
package com.uimirror.ws.api.audit;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.ws.api.audit.common.AuditFieldConstants;

/**
 * <p>Request and response audit details</p>
 * <p>This will store the client call details like which resource </p>
 * <ul>
 * <li>client was trying to access</li>
 * <li>which time request landed</li>
 * <li>Which time response goes</li>
 * <li>Final status of the response</li>
 * </ul>
 * 
 * @author Jayaram
 */
public final class Audit  extends BeanBasedDocument implements Serializable{

	private static final long serialVersionUID = -6606261756865155348L;
	protected static final Logger LOG = LoggerFactory.getLogger(Audit.class);
	
	private final String id;
	private final long requestTime;
	private final long responseTime;
	private final String resource;
	private final String clientId;
	private final Status status;
	
	//Preventing to intaiate
	@SuppressWarnings("unused")
	private Audit() {
		this.id = null;
		this.requestTime = 0l;
		this.responseTime = 0l;
		this.resource = null;
		this.clientId = null;
		this.status = null;
	}

	//Constructor with all the value needs to be populate
	public Audit(String id, long requestTime, long responseTime, String resource, String clientId, Status status) {
		//super(10);
		this.id = id;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.resource = resource;
		this.clientId = clientId;
		this.status = status;
		//Populate the document Map
		initialize();
	}
	
	/**
	 * <p>This will deseralize the map to java object</p>
	 * @param m 
	 */
	public Audit(Map<String, Object> m){
		super(m);
		this.id = (String)m.get(AuditFieldConstants._ID);
		this.requestTime = (long)m.get(AuditFieldConstants._RQ_TIME);
		this.responseTime = (long)m.get(AuditFieldConstants._RS_TIME);
		this.resource = (String)m.get(AuditFieldConstants._RESOURCE);
		this.clientId = (String)m.get(AuditFieldConstants._CLIENT_ID);
		this.status = Status.getEnum((String) m.get(AuditFieldConstants._STATUS));
	}
	
	/**
	 * <p>This will start the audit process, allocates a unique id for keeping track of the request</p>
	 * <p>This will mark the audit as success by default, assuming job has been completed</p>
	 * <p>This will mark the response time as the current time in UTC format</p>
	 * @param clientId a valid String representing the client ID
	 * @param resource a valid resource identifier
	 * @param requestTime A time specifying the requested source time in EPOCH format
	 * @return a new Instance of {@code Audit}
	 */
	public static Audit success(String clientId, String resource, long requestTime){
		validate(clientId, resource, requestTime);
		//Creating a new object with UUID as id and marking the status as complete
		return new Audit(UUID.randomUUID().toString(), requestTime, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), resource, clientId, Status.COMPLETE);
	}
	
	/**
	 * <p>This will start the audit process, allocates a unique id for keeping track of the request</p>
	 * <p>This will mark the audit as success by default, assuming job has been completed with error</p>
	 * @return a new Instance of {@code Audit}
	 */
	public static Audit failed(String clientId, String resource, long requestTime){
		//Creating a new object with UUID as id and marking the status as failed
		return new Audit(UUID.randomUUID().toString(), requestTime, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), resource, clientId, Status.FAILED);
	}
	
	/**
	 * <p>Helper to the utility methods to help the validation process in a single space</p>
	 * @param clientId
	 * @param resource
	 * @param requestTime
	 */
	private static void validate(String clientId, String resource, long requestTime){
		Assert.hasText(clientId, "Audit requires a valid clientId");
		Assert.hasText(resource, "Audit requires to know the resource which will be used by client");
		if(requestTime <= 0l)
			throw new IllegalArgumentException("Audit requires a Resource request time.");
	}

	/**
	 * <p>This will initialize the document map for Mongo</p>
	 */
	private void initialize(){
//		super.put(AuditFieldConstants._ID, this.id);
//		super.put(AuditFieldConstants._CLIENT_ID, this.clientId);
//		super.put(AuditFieldConstants._RESOURCE, this.resource);
//		super.put(AuditFieldConstants._STATUS, this.status);
//		if(this.requestTime > 0l)
//			super.put(AuditFieldConstants._RQ_TIME, this.requestTime);
//		if(this.responseTime > 0l)
//			super.put(AuditFieldConstants._RS_TIME, this.responseTime);
	}

	public String getId() {
		return id;
	}

	public long getRequestTime() {
		return requestTime;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public String getResource() {
		return resource;
	}

	public String getClientId() {
		return clientId;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (requestTime ^ (requestTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Audit other = (Audit) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestTime != other.requestTime)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Audit [id=" + id + ", requestTime=" + requestTime
				+ ", responseTime=" + responseTime + ", resource=" + resource
				+ ", clientId=" + clientId + ", status=" + status + "]";
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public Object initFromMap(Map src) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
