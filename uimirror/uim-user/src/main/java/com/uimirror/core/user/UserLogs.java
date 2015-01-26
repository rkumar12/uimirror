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
import static com.uimirror.core.user.UserLogsDBFields.BLOCKED_ON;
import static com.uimirror.core.user.UserLogsDBFields.CREATED_ON;
import static com.uimirror.core.user.UserLogsDBFields.MODIFIED_ON;
import static com.uimirror.core.user.UserLogsDBFields.LAST_LOGIN;
import static com.uimirror.core.user.UserLogsDBFields.DEACTIVATED_ON;
import static com.uimirror.core.user.UserLogsDBFields.REACTIVATED_ON;
import static com.uimirror.core.user.UserLogsDBFields.UNBLOCKED_ON;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * contains the details of the state of the person like when the account was created, modified. when did the person lastLogin.
 * If the person account is deactivated or blocked if any issues with the account. Again unblock or reactivate the account if user comes back with valid details.
 * These details will be saved in the DB uim_usr schema with the collection name users_logs
 * @author Jay
 */
public class UserLogs  extends AbstractBeanBasedDocument<UserLogs> implements BeanValidatorService{

	private static final long serialVersionUID = 1163091982626410001L;
	private long createdOn;
	private long modifiedOn;
	private long lastLogin;
	private long deactivatedOn;
	private long blockedOn;
	private long unBlockedOn;
	private long reactivatedOn;

	
	public UserLogs() {
		//NOP
	}
	
	private UserLogs(UserLogsBuilder builder){
		super(builder.profileId);
		this.createdOn = builder.createdOn;
		this.modifiedOn = builder.modifiedOn;
		this.lastLogin = builder.lastLogin;
		this.deactivatedOn = builder.deactivatedOn;
		this.blockedOn = builder.blockedOn;
		this.unBlockedOn = builder.unBlockedOn;
		this.reactivatedOn = builder.reactivatedOn;
		
	}
	
	public UserLogs update(String id){
		return new UserLogsBuilder(id)
		.addCreatedOn(createdOn)
		.addModifiedOn(modifiedOn)
		.addLastLogin(lastLogin)
		.addDeactivatedOn(deactivatedOn)
		.addBlockedOn(blockedOn)
		.addUnBlockedOn(unBlockedOn)
		.addReactivatedOn(reactivatedOn)
		.build();
	}
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return {@link Map} with the current state
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new WeakHashMap<String, Object>(16);
		if(StringUtils.hasText(getId()))
			state.put(ID, getId());
		state.put(CREATED_ON, getCreatedOn());
		state.put(MODIFIED_ON, getModifiedOn());
		state.put(LAST_LOGIN, getLastLogin());
		state.put(DEACTIVATED_ON, getDeactivatedOn());
		state.put(BLOCKED_ON, getBlockedOn());
		state.put(UNBLOCKED_ON, getUnBlockedOn());
		state.put(REACTIVATED_ON, getReactivatedOn());
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public UserLogs readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}
	
	/**
	 * converts a map that comes from DB into UserLogs object.
	 * @param raw {@link Map} from which it will be initialized
	 * @return {@link UserLogs}
	 */
	private UserLogs init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		long createdOn = (long) raw.get(CREATED_ON);
		long modifiedOn = (long) raw.get(MODIFIED_ON);
		long lastLogin = (long) raw.get(LAST_LOGIN);
		long deactivatedOn = (long) raw.get(DEACTIVATED_ON);
		long blockedOn = (long) raw.get(BLOCKED_ON);
		long unBlockedOn = (long) raw.get(UNBLOCKED_ON);
		long reactivatedOn = (long) raw.get(REACTIVATED_ON);
		
		UserLogsBuilder builder = new UserLogsBuilder(id)
		.addCreatedOn(createdOn)
		.addModifiedOn(modifiedOn)
		.addLastLogin(lastLogin)
		.addDeactivatedOn(deactivatedOn)
		.addBlockedOn(blockedOn)
		.addUnBlockedOn(unBlockedOn)
		.addReactivatedOn(reactivatedOn);
		return builder.build();
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {

		boolean valid = Boolean.TRUE;
		if(createdOn == 0){
			valid = Boolean.FALSE;
		}
		return valid;
	}
	
	public static class UserLogsBuilder implements Builder<UserLogs>{
		
		private String profileId;
		private long createdOn;
		private long modifiedOn;
		private long lastLogin;
		private long deactivatedOn;
		private long blockedOn;
		private long unBlockedOn;
		private long reactivatedOn;
		
		public UserLogsBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public UserLogsBuilder addCreatedOn(long createdOn){
			this.createdOn = createdOn;
			return this;
		}
		
		public UserLogsBuilder addModifiedOn(long modifiedOn){
			this.modifiedOn = modifiedOn;
			return this;
		}
		
		public UserLogsBuilder addLastLogin(long lastLogin){
			this.lastLogin = lastLogin;
			return this;
		}
		
		public UserLogsBuilder addDeactivatedOn(long deactivatedOn){
			this.deactivatedOn = deactivatedOn;
			return this;
		}
		
		public UserLogsBuilder addBlockedOn(long blockedOn){
			this.blockedOn = blockedOn;
			return this;
		}
		
		public UserLogsBuilder addUnBlockedOn(long unBlockedOn){
			this.unBlockedOn = unBlockedOn;
			return this;
		}
		
		public UserLogsBuilder addReactivatedOn(long reactivatedOn){
			this.reactivatedOn = reactivatedOn;
			return this;
		}
		
		
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public UserLogs build() {
			return new UserLogs(this);
		}
		
	}
	
	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}
	
	public String getProfileId() {
		return getId();
	}

	public long getCreatedOn() {
		return createdOn;
	}


	public long getModifiedOn() {
		return modifiedOn;
	}


	public long getLastLogin() {
		return lastLogin;
	}


	public long getDeactivatedOn() {
		return deactivatedOn;
	}


	public long getBlockedOn() {
		return blockedOn;
	}


	public long getUnBlockedOn() {
		return unBlockedOn;
	}


	public long getReactivatedOn() {
		return reactivatedOn;
	}
	
	

}
