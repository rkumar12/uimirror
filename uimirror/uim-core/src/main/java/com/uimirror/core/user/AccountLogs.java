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
package com.uimirror.core.user;

import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.user.UserAccountLogDBFields.CREATED_ON;
import static com.uimirror.core.user.UserAccountLogDBFields.DETAILS;
import static com.uimirror.core.user.UserAccountLogDBFields.MODIFIED_ON;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * User's account logs statement such as when account created,
 * when modified, if any history needs to maintain then can be done here
 * @author Jay
 */
public class AccountLogs extends AbstractBeanBasedDocument<AccountLogs> implements BeanValidatorService {
	
	private static final long serialVersionUID = -6504474875834652281L;
	private long createdOn;
	private long modifiedOn;
	private Map<String,Object> details;
	
	//Don't Use this until it has specific requirement
	public AccountLogs() {
		//NOP
	}
	
	@Override
	public AccountLogs updateId(String id) {
		return new LogBuilder(id).updatedCreatedOn(createdOn).updateModifiedOn(modifiedOn).updateDetails(details).build();
	}
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(getCreatedOn() <= 0l)
			valid = Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return {@link Map} representation of the current state
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new WeakHashMap<String, Object>(9);
		if(StringUtils.hasText(getProfileId()))
			state.put(ID, getId());
		state.put(CREATED_ON, getCreatedOn());
		if(getModifiedOn() > 0l)
			state.put(MODIFIED_ON, getModifiedOn());
		if(!CollectionUtils.isEmpty(getDetails()))
			state.put(DETAILS, getDetails());
		return state;
	}
	

	@Override
	public AccountLogs readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);
	}
	
	/**
	 * converts a map that comes from DB into AccountLogs object.
	 * 
	 * @param raw {@link Map} from which state will be preserved
	 * @return {@link AccountLogs}
	 */
	@SuppressWarnings("unchecked")
	private AccountLogs init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		long creatOn = (long) raw.getOrDefault(CREATED_ON, 0l);
		long modifiedOn = (long) raw.getOrDefault(MODIFIED_ON, 0l);
		Map<String,Object> details =  (Map<String, Object>) raw.get(DETAILS);
		return new LogBuilder(id).updatedCreatedOn(creatOn).updateModifiedOn(modifiedOn).updateDetails(details).build();
	}
	
	public static class LogBuilder implements Builder<AccountLogs>{
		private String profileId;
		private long createdOn;
		private long modifiedOn;
		private Map<String,Object> details;
		
		public LogBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public LogBuilder updatedCreatedOn(long mills){
			this.createdOn = mills;
			return this;
		}
		
		public LogBuilder updateModifiedOn(long mills){
			this.modifiedOn = mills;
			return this;
		}
		
		public LogBuilder updateDetails(Map<String,Object> details){
			this.details = details;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public AccountLogs build() {
			return new AccountLogs(this);
		}
		
	}
	
	private AccountLogs(LogBuilder builder){
		super(builder.profileId);
		this.createdOn = builder.createdOn;
		this.modifiedOn = builder.modifiedOn;
		this.details = builder.details;
	}
	
	public String getProfileId(){
		return getId();
	}
	
	public long getCreatedOn() {
		return createdOn;
	}

	public long getModifiedOn() {
		return modifiedOn;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}
}
