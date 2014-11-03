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
package com.uimirror.location;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Persist the {@link Country}  into the Mongo Store in the format
 * {"state" = {
 *		"_id" : "1",
 *		"name" : "Karnataka",
 *		"sh_name" : "KA"
 * 	}
 * } 
 * @author Jay
 */
public class State  extends BeanBasedDocument<State> implements BeanValidatorService {
	
	private static final long serialVersionUID = -5656425279379477707L;

	private String shortName;
	private String name;

	//Don't Use It untill it has some special requirment
	public State() {
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public State initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
	}
	
	/**
	 * converts a map that comes from DB into Country object object.
	 * @param raw
	 * @return {@link State}
	 */
	private State init(Map<String, Object> raw) {
		//First validate the source for invalid MAP
		validateSource(raw);
		String _id = (String)raw.get(CountryDBFields.ID);
		String sh_name = (String)raw.get(CountryDBFields.SHORT_NAME);
		String name = (String)raw.get(CountryDBFields.NAME);
		return new StateBuilder(_id).updateShortName(sh_name).updateName(name).build();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#toMap()
	 */
	@Override
	public Map<String, Object> toMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getName()) && !StringUtils.hasText(getShortName()))
			valid = Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(7);
		if(StringUtils.hasText(getStateId()))
			state.put(StateDBFields.ID, getStateId());
		if(StringUtils.hasText(getShortName()))
			state.put(StateDBFields.SHORT_NAME, getShortName());
		if(StringUtils.hasText(getName()))
			state.put(StateDBFields.NAME, getName());
		return state;
	}

	public String getShortName() {
		return StringUtils.hasText(shortName) ? shortName.toUpperCase() : null;
	}

	public String getName() {
		return WordUtils.capitalizeFully(name);
	}
	
	public String getStateId(){
		return getId();
	}
	
	@Override
	public String toString() {
		return "State [shortName=" + shortName + ", name=" + name + "]";
	}


	public static class StateBuilder implements Builder<State>{
		private String shortName;
		private String name;
		private String id;
		
		public StateBuilder(String id){
			this.id = id;
		}
		public StateBuilder updateName(String name){
			this.name = name;
			return this;
		}
		public StateBuilder updateShortName(String shortName){
			this.shortName = shortName;
			return this;
		}
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public State build() {
			return new State(this);
		}
	}
	
	private State(StateBuilder builder){
		this.name = builder.name;
		this.shortName = builder.shortName;
		super.setId(builder.id);
	}

}
