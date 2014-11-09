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
 * {"country" = {
 *		"_id" : "1",
 *		"name" : "India",
 *		"sh_name" : "IN",
 *		"code: : 91 
 * 	}
 * } 
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
		return new CountryBuilder(_id).updateName(name).updateShortName(sh_name).updateCode(code).build();
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
		if(StringUtils.hasText(getCountryId()))
			state.put(CountryDBFields.ID, getCountryId());
		if(StringUtils.hasText(getShortName()))
			state.put(CountryDBFields.SHORT_NAME, getShortName());
		if(StringUtils.hasText(getName()))
			state.put(CountryDBFields.NAME, getName());
		if(getCode() > 0)
			state.put(CountryDBFields.CODE, getCode());
		return state;
	}

	public String getShortName() {
		return StringUtils.hasText(shortName) ? shortName.toUpperCase() : null;
	}

	public String getName() {
		return WordUtils.capitalizeFully(name);
	}

	public int getCode() {
		return code;
	}
	
	public String getCountryId(){
		return getId();
	}

	@Override
	public String toString() {
		return "Country [shortName=" + shortName + ", name=" + name + ", code="
				+ code + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + code;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Country other = (Country) obj;
		if ((code > 0 || other.code >0) && code != other.code)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	public static class CountryBuilder implements Builder<Country>{
		private String shortName;
		private String name;
		private int code;
		private String id;
		
		public CountryBuilder(String id){
			this.id = id;
		}
		public CountryBuilder updateName(String name){
			this.name = name;
			return this;
		}
		public CountryBuilder updateShortName(String name){
			this.shortName = name;
			return this;
		}
		public CountryBuilder updateCode(int code){
			this.code = code;
			return this;
		}
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Country build() {
			return new Country(this);
		}
	}
	
	private Country(CountryBuilder builder){
		super(builder.id);
		this.name = builder.name;
		this.shortName = builder.shortName;
		this.code = builder.code;
	}

}
