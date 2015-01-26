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
import static com.uimirror.core.user.UserEventsLogDBDetails.EVENT_ON;
import static com.uimirror.core.user.UserEventsLogDBDetails.EVENT_DETAILS;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the details of the person life events like married, anniversary ect. eventOn will have the date of the event and eventDetails will have
 * details of the respective event.
 * These details will be saved in the DB uim_usr schema with the collection name users_events  
 * @author Jay
 */
public class UserEventsLog extends AbstractBeanBasedDocument<UserEventsLog>  implements BeanValidatorService {

	private static final long serialVersionUID = -1270117781976591336L;
	private String eventOn;
	private String eventDetails;
	
	/**
	 * 
	 */
	public UserEventsLog() {
		//NOP
	}
	
	private UserEventsLog(UserEventsLogBuilder builder){
		super(builder.profileId);
		this.eventOn = builder.eventOn;
		this.eventDetails = builder.eventDetails;
	}
	
	public UserEventsLog update(String id){
		return new UserEventsLogBuilder(id)
		.addEventOn(eventOn)
		.addEventDetails(eventDetails)
		.build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public UserEventsLog readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}
	
	/**
	 * converts a map that comes from DB into UserEventsLog object.
	 * @param raw {@link Map} from which it will be initialized
	 * @return {@link UserEventsLog}
	 */
	private UserEventsLog init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		String eventOn = (String) raw.get(EVENT_ON);
		String eventDetails = (String) raw.get(EVENT_DETAILS);
		
		UserEventsLogBuilder builder = new UserEventsLogBuilder(id)
		.addEventOn(eventOn)
		.addEventDetails(eventDetails);
		return builder.build();
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
		state.put(EVENT_ON, getEventOn());
		state.put(EVENT_DETAILS, getEventDetails());
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {

		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getEventOn())){
			valid = Boolean.FALSE;
		}
		if(!StringUtils.hasText(getEventDetails())){
			valid = Boolean.FALSE;
		}
		return valid;
	}
	
	public static class UserEventsLogBuilder implements Builder<UserEventsLog>{
		
		private String profileId;
		private String eventOn;
		private String eventDetails;
		
		public UserEventsLogBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public UserEventsLogBuilder addEventOn(String eventOn){
			this.eventOn = eventOn;
			return this;
		}
		
		public UserEventsLogBuilder addEventDetails(String eventDetails){
			this.eventDetails = eventDetails;
			return this;
		}
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public UserEventsLog build() {

			return new UserEventsLog(this);
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

	public String getEventOn() {
		return eventOn;
	}

	public String getEventDetails() {
		return eventDetails;
	}
	
	

}
