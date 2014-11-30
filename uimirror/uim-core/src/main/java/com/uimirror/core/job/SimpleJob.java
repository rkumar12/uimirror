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
package com.uimirror.core.job;

import static com.uimirror.core.job.JobConstants.COMPLETED_ON;
import static com.uimirror.core.job.JobConstants.DETAILS;
import static com.uimirror.core.job.JobConstants.MESSAGE;
import static com.uimirror.core.job.JobConstants.NAME;
import static com.uimirror.core.job.JobConstants.STARTED_ON;
import static com.uimirror.core.job.JobConstants.STATUS;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.DateTimeUtil;


/**
 * Common bean definition for the jobs
 * @author Jay
 */
public class SimpleJob extends AbstractBeanBasedDocument<SimpleJob> implements BeanValidatorService{
	
	private static final long serialVersionUID = -7039185813045016374L;

	//Never use, if no special requirment
	public SimpleJob(){
		//NOP
	}
	private String name;
	private Status status;
	private long startedOn;
	private long completedOn;
	private String message;
	private Map<String, Object> details;

	public static class JobBuilder implements Builder<SimpleJob>{
		private String id;
		private String name;
		private Status status;
		private long startedOn;
		private long completedOn;
		private String message;
		private Map<String, Object> details;
		
		public JobBuilder(String id){
			this.id = id;
		}
		public JobBuilder addName(String name){
			this.name = name;
			return this;
		}
		
		public JobBuilder addStatus(Status status){
			this.status = status;
			return this;
		}
		
		public JobBuilder addStatus(String status){
			if(!StringUtils.hasText(status))
				throw new IllegalArgumentException("Invalid status");
			this.status = Status.getEnum(status);
			return this;
		}
		
		public JobBuilder addStartedOn(long startedOn){
			this.startedOn = startedOn;
			return this;
		}
		
		public JobBuilder addCompletedOn(long completedOn){
			this.completedOn = completedOn;
			return this;
		}
		
		public JobBuilder addMessage(String message){
			this.message = message;
			return this;
		}
		
		public JobBuilder addDetails(Map<String, Object> details){
			this.details = details;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public SimpleJob build() {
			return new SimpleJob(this);
		}
		
	}
	
	private SimpleJob(JobBuilder builder){
		this.name = builder.name;
		this.status = builder.status;
		this.startedOn = builder.startedOn;
		this.completedOn = builder.completedOn;
		this.message = builder.message;
		this.details = builder.details;
		setId(builder.id);
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.AbstractBeanBasedDocument#updateId(java.lang.String)
	 */
	@Override
	public SimpleJob updateId(String id){
		return new JobBuilder(id).
				addName(name).
				addStatus(status).
				addStartedOn(startedOn).
				addCompletedOn(completedOn).
				addMessage(message).
				addDetails(details).
				build();
	}
	
	
	public String getName() {
		return name;
	}
	public Status getStatus() {
		return status;
	}
	public long getStartedOn() {
		return startedOn;
	}
	public long getCompletedOn() {
		return completedOn;
	}
	public String getMessage() {
		return message;
	}
	public Map<String, Object> getDetails() {
		return details;
	}
	
	public String getJobId(){
		return getId();
	}

	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public SimpleJob readFromMap(Map<String, Object> raw) throws IllegalArgumentException {
		isValidSource(raw);
		return deSerailize(raw);
	}

	public static SimpleJob initFromMap(Map<String, Object> raw) throws IllegalArgumentException {
		return deSerailize(raw);
	}
	
	public static SimpleJob deSerailize(Map<String, Object> raw){
		String id = (String) raw.get(ID);
		String name = (String) raw.get(NAME);
		Status status = Status.getEnum((String) raw.getOrDefault(STATUS, "NA"));
		long startedOn = (long)raw.getOrDefault(STARTED_ON, 0l);
		long completedOn = (long)raw.getOrDefault(COMPLETED_ON, 0l);
		String message = (String) raw.get(MESSAGE);
		@SuppressWarnings("unchecked")
		Map<String, Object> details = (Map<String, Object>)raw.get(DETAILS);
		return new JobBuilder(id).
				addName(name).
				addStatus(status).
				addStartedOn(startedOn).
				addCompletedOn(completedOn).
				addMessage(message).
				addDetails(details).
				build();
	}
	
	public SimpleJob markAsComplete(){
		return new JobBuilder(getId()).
				addName(name).
				addStatus(Status.COMPLETE).
				addStartedOn(startedOn).
				addCompletedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).
				addMessage(message).
				addDetails(details).
				build();
	}
	
	public SimpleJob markAsFailed(){
		return new JobBuilder(getId()).
				addName(name).
				addStatus(Status.FAILED).
				addStartedOn(startedOn).
				addCompletedOn(completedOn).
				addMessage(message).
				addDetails(details).
				build();
	}
	
	public SimpleJob addMessage(String message){
		return new JobBuilder(getId()).
				addName(name).
				addStatus(status).
				addStartedOn(startedOn).
				addCompletedOn(completedOn).
				addMessage(message).
				addDetails(details).
				build();
	}
	
	public SimpleJob addDetails(Map<String, Object> details){
		return new JobBuilder(getId()).
				addName(name).
				addStatus(status).
				addStartedOn(startedOn).
				addCompletedOn(completedOn).
				addMessage(message).
				addDetails(details).
				build();
	}
	
	/**
	 * Create a map that needs to be persisted
	 * @return map of the details
	 * @throws IllegalStateException when it can't be serailized
	 */
	@Override
	public Map<String, Object> writeToMap() throws IllegalStateException{
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}

	/**
	 * @return
	 */
	private Map<String, Object> serailize() {
		Map<String, Object> rs = new WeakHashMap<String, Object>(9);
		rs.put(NAME, getName());
		rs.put(STATUS, status.getStatus());
		rs.put(STARTED_ON, getStartedOn());
		if(completedOn > 0l)
			rs.put(COMPLETED_ON, getCompletedOn());
		if(StringUtils.hasText(message))
			rs.put(MESSAGE, getMessage());
		if(!CollectionUtils.isEmpty(details))
			rs.put(DETAILS, getDetails());
		return rs;
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(name))
			valid = Boolean.FALSE;
		if(status == null)
			valid = Boolean.FALSE;
		if(startedOn == 0l)
			valid = Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Says if the secified job is Failed
	 * @return true if it is
	 */
	public boolean isFailed(){
		return Status.FAILED == getStatus();
	}

	/**Says if specified job is completed
	 * @return <code>true</code> if it is
	 */
	public boolean isComplete(){
		return Status.COMPLETE == getStatus();
	}
	
	/**
	 * Says if the job is started.
	 * @return <code>true</code> if it is
	 */
	public boolean isStarted(){
		return Status.STRATED == getStatus();
	}
	
	/**
	 * Says if job is in started status then it is running
	 * @return <code>true</code> if it is
	 */
	public boolean isRunning(){
		return isStarted();
	}

}
