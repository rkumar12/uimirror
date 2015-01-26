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
import static com.uimirror.core.user.UserBasicDetailsDBFields.EDUCATION;
import static com.uimirror.core.user.UserEventsLogDBDetails.USER_EVENTS;

import java.util.ArrayList;
import java.util.List;
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
 * contains the details of all important life events of the person.
 * These details will be saved in the DB uim_usr schema with the collection name User_life_dates
 * @author Jay
 */
public class UserLifeDates extends AbstractBeanBasedDocument<UserLifeDates>  implements BeanValidatorService{

	private static final long serialVersionUID = 5099700513192081307L;
	List<UserEventsLog> userEvents;
	
	public UserLifeDates() {
		//NOP
	}
	
	private UserLifeDates(UserLifeDatesBuilder builder){
		super(builder.profileId);
		this.userEvents = builder.userEvents;
	} 

	public UserLifeDates update(String id){
		return new UserLifeDatesBuilder(id)
		.addUserEvents(userEvents)
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
		
		if(getUserEvents() != null){
			List<Map<String, Object>> userEvents = new ArrayList<Map<String,Object>>(getUserEvents().size());
			getUserEvents().forEach((userEvent) -> {
				userEvents.add(userEvent.writeToMap());
			});
			state.put(USER_EVENTS,userEvents);
		}
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public UserLifeDates readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}
	
	/**
	 * converts a map that comes from DB into UserLifeDates object.
	 * @param raw {@link Map} from which it will be initialized
	 * @return {@link UserLifeDates}
	 */
	@SuppressWarnings("unchecked")
	private UserLifeDates init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		List<UserEventsLog> userEvents = null;
		
		List<Map<String, Object>> events = (List<Map<String, Object>>)raw.get(USER_EVENTS);
		if(null != events){
			
			userEvents = new ArrayList<UserEventsLog>(events.size());
			for(Map<String, Object> eventsMap : events){
				userEvents.add(new UserEventsLog().readFromMap(eventsMap));
			}
		}
		
		UserLifeDatesBuilder builder = new UserLifeDatesBuilder(id)
		.addUserEvents(userEvents);
		return builder.build();
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(CollectionUtils.isEmpty(getUserEvents()))
			valid = Boolean.FALSE;
		return valid;
	}
	
	public static class UserLifeDatesBuilder implements Builder<UserLifeDates>{
		
		private String profileId;
		private List<UserEventsLog> userEvents;
		 
		public UserLifeDatesBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public UserLifeDatesBuilder addUserEvents(List<UserEventsLog> userEvents){
			this.userEvents = userEvents;
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public UserLifeDates build() {
			return new UserLifeDates(this);
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

	public List<UserEventsLog> getUserEvents() {
		return userEvents;
	}
	

}
