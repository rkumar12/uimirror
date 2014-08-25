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
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.uimirror.mongo.feature.BeanBasedDocument;
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
	private final LocalDateTime requestTime;
	private final LocalDateTime responseTime;
	private final String resource;
	private final String clientId;
	private final Status status;
	
	//Preventing to intaiate
	@SuppressWarnings("unused")
	private Audit() {
		this.id = null;
		this.requestTime = null;
		this.responseTime = null;
		this.resource = null;
		this.clientId = null;
		this.status = null;
	}

	//Constructor with all the value needs to be populate
	public Audit(String id, LocalDateTime requestTime, LocalDateTime responseTime, String resource, String clientId, Status status) {
		super(10);
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
		this.requestTime = (LocalDateTime)m.get(AuditFieldConstants._RQ_TIME);
		this.responseTime = (LocalDateTime)m.get(AuditFieldConstants._RS_TIME);
		this.resource = (String)m.get(AuditFieldConstants._RESOURCE);
		this.clientId = (String)m.get(AuditFieldConstants._CLIENT_ID);
		this.status = Status.getEnum((String) m.get(AuditFieldConstants._STATUS));
	}
	
	/**
	 * <p>This will start the audit process, allocates a unique id for keeping track of the request</p>
	 * @param clientId a valid String representing the client ID
	 * @param resource a valid resource identifier
	 * @return a new Instance of {@code Audit}
	 */
	public static Audit initateAudit(String clientId, String resource){
		Assert.hasText(clientId, "Audit requires a valid clientId");
		Assert.hasText(resource, "Audit requires to know the resource which will be used by client");
		
		//Creating a new object with UUID as id and marking the status as progress
		return new Audit(UUID.randomUUID().toString(), LocalDateTime.now(), null, resource, clientId, Status.PROGRESS);
	}
	
	/**
	 * <p>Will update the audit status to complete along with update the response time</p>
	 * @return a new Instance of {@code Audit}
	 */
	public Audit markAsComplete(){
		return new Audit(this.id, this.requestTime, LocalDateTime.now(), this.resource, this.clientId, Status.COMPLETE);
	}
	
	/**
	 * <p>Will update the audit status to failed along with update the response time</p>
	 * @return a new Instance of {@code Audit}
	 */
	public Audit markAsFailed(){
		return new Audit(this.id, this.requestTime, LocalDateTime.now(), this.resource, this.clientId, Status.FAILED);
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	public LocalDateTime getResponseTime() {
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

	/**
	 * <p>This will initialize the document map for Mongo</p>
	 */
	private void initialize(){
		super.put(AuditFieldConstants._ID, this.id);
		super.put(AuditFieldConstants._CLIENT_ID, this.clientId);
		super.put(AuditFieldConstants._RESOURCE, this.resource);
		super.put(AuditFieldConstants._STATUS, this.status);
		if(this.requestTime != null)
			super.put(AuditFieldConstants._RQ_TIME, this.requestTime);
		if(this.responseTime != null)
			super.put(AuditFieldConstants._RS_TIME, this.responseTime);
	}
	
	
}
