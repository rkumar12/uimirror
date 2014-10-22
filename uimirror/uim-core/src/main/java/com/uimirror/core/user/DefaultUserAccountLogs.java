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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * User's account logs statement such as when account created,
 * when modified, if any history needs to maintain then can be done here
 * @author Jay
 */
public class DefaultUserAccountLogs extends BeanBasedDocument<DefaultUserAccountLogs> implements BeanValidatorService {
	
	private static final long serialVersionUID = -6504474875834652281L;
	private long createdOn;
	private long modifiedOn;
	private Map<String,Object> details;
	
	//Don't Use this until it has specific requirement
	public DefaultUserAccountLogs() {
		super();
	}
	
	public DefaultUserAccountLogs(Map<String, Object> details) {
		super(details);
	}

	public DefaultUserAccountLogs(String id,long createdOn, long modifiedOn, Map<String, Object> details) {
		super(id);
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.details = details;
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
	
	public DefaultUserAccountLogs updateProfileId(String profileId){
		return new DefaultUserAccountLogs(profileId, this.createdOn, this.modifiedOn, this.details);
	}
	
	@Override
	public Map<String, Object> toMap() {
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
		if(!StringUtils.hasText(getId()))
			valid = Boolean.FALSE;
		if(getCreatedOn() <= 0l)
			valid = Boolean.FALSE;
		return valid;
	}

	@Override
	public DefaultUserAccountLogs initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
	}
	
	/**
	 * converts a map that comes from DB into DefaultUserAccountLogs object.
	 * 
	 * @param raw
	 * @return {@link DefaultUserAccountLogs}
	 */
	@SuppressWarnings("unchecked")
	private DefaultUserAccountLogs init(Map<String, Object> raw) {
		String id = (String) raw.get(UserDBFields.ID);
		long creatOn = 0l;
		long modifiedOn = 0l;
		if(raw.get(UserDBFields.CREATED_ON) != null){
			creatOn = (long) raw.get(UserDBFields.CREATED_ON);
		}
		if(raw.get(UserDBFields.MODIFIED_ON) != null){
			modifiedOn = (long) raw.get(UserDBFields.MODIFIED_ON);
		}
		Map<String,Object> details =  (Map<String, Object>) raw.get(UserDBFields.DETAILS);
		return new DefaultUserAccountLogs(id,creatOn,modifiedOn,details);
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(9);
		state.put(UserDBFields.ID, getId());
		state.put(UserDBFields.CREATED_ON, getCreatedOn());
		if(getModifiedOn() > 0l)
			state.put(UserDBFields.MODIFIED_ON, getModifiedOn());
		if(!CollectionUtils.isEmpty(getDetails()))
			state.put(UserDBFields.DETAILS, getDetails());
		return state;
	}
	
}
