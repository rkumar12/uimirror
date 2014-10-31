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

import org.springframework.util.StringUtils;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Persist the {@link Country}  into the Mongo Store in the format
 * {"country" = {
 *		"_id" : "1",
 *		"name" : "India",
 *		"sh_name" : "IN",
 *		"code: : 91 
 * 	}
 * } 
 * @author Jay
 */
/**
 * @author Jay
 */
public class Country  extends BeanBasedDocument<Country> implements BeanValidatorService {
	
	private static final long serialVersionUID = -5656425279379477707L;

	private String shortName;
	private String name;
	private int code;

	//Don't Use It untill it has some special requirment
	public Country() {
	}

	/**
	 * @param id
	 * @param shortName
	 * @param name
	 * @param code
	 */
	public Country(String id, String shortName, String name, int code) {
		super(id);
		this.shortName = shortName;
		this.name = name;
		this.code = code;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public Country initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		validateSource(src);
		// Initialize the state
		return init(src);
	}
	
	/**
	 * converts a map that comes from DB into Country object object.
	 * @param raw
	 * @return {@link Country}
	 */
	private Country init(Map<String, Object> raw) {
		//First validate the source for invalid MAP
		validateSource(raw);
		String _id = (String)raw.get(CountryDBFields.ID);
		String sh_name = (String)raw.get(CountryDBFields.SHORT_NAME);
		String name = (String)raw.get(CountryDBFields.NAME);
		int code = (int)raw.getOrDefault(CountryDBFields.CODE, 0);
		return new Country(_id, name, sh_name, code);
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
		if(StringUtils.hasText(this.getId()))
			state.put(CountryDBFields.ID, getCountryId());
		if(StringUtils.hasText(this.getShortName()))
			state.put(CountryDBFields.SHORT_NAME, getShortName());
		if(StringUtils.hasText(this.getName()))
			state.put(CountryDBFields.NAME, getName());
		if(getCode() > 0)
			state.put(CountryDBFields.CODE, getCode());
		return state;
	}

	public String getShortName() {
		return shortName;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}
	
	public String getCountryId(){
		return this.getId();
	}

	@Override
	public String toString() {
		return "Country [shortName=" + shortName + ", name=" + name + ", code="
				+ code + "]";
	}

	
	/** 
	 * Has code, if code present, else first check for the name otherwise check 
	 * for the short name
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	//TODO not sure about the hashCode here, come back with proper analysis.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if(code <= 0)
			result = prime * result + code;
		else if(StringUtils.hasText(getName()))
			result = prime * result + name.hashCode();
		else if(StringUtils.hasText(getShortName()))
			result = prime * result + shortName.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (code != other.code)
			return false;
		return true;
	}
	
	

}
