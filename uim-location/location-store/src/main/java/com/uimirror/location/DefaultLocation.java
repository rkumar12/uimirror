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
import com.uimirror.location.core.components.LocationType;

/**
 * Location Bean document which will have location name, short name, 
 * location type and country details.
 * 
 * This will not mandate the information as such, but it required one location name should be populated.
 * 
 * <p>Country code and country short form is mandetory for every location.</p>
 * 
 * @author Jay
 */
public class DefaultLocation extends BeanBasedDocument<DefaultLocation> implements BeanValidatorService{
	
	private static final long serialVersionUID = 2347155622832246734L;
	private String locName;
	private Country country;
	private LocationType type;

	//Don't Use It untill it has some special requirement
	public DefaultLocation() {
		super();
	}

	/**
	 * Location details 
	 * @param id
	 * @param locName
	 * @param country
	 * @param type
	 */
	public DefaultLocation(String id, String locName, Country country, LocationType type) {
		super(id);
		this.locName = locName;
		this.country = country;
		this.type = type;
	}

	public DefaultLocation(String id, String locName, String countryId, String type) {
		super(id);
		this.locName = locName;
		this.country = new Country(countryId, null, null, 0);
		this.type = LocationType.getEnum(type);
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
		if(!StringUtils.hasText(getCountryId()))
			valid = Boolean.FALSE;
		return valid;
	}
	
	/**
	 * Seralize the state of the object which needs to be present in the store
	 * @return
	 */
	public Map<String, Object> serailize(){
		Map<String, Object> state = new LinkedHashMap<String, Object>(7);
		if(StringUtils.hasText(getId()))
			state.put(LocationDBFields.ID, getId());
		if(StringUtils.hasText(getLocName()))
			state.put(LocationDBFields.NAME, getLocName());
		if(getType() != null)
			state.put(LocationDBFields.LOCATION_TYPE, getType().getLocationType());
		state.put(LocationDBFields.COUNTRY_ID, getCountryId());
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public DefaultLocation initFromMap(Map<String, Object> raw) {
		String id = (String)raw.get(LocationDBFields.ID);
		String name = (String)raw.get(LocationDBFields.NAME);
		String l_type = (String)raw.get(LocationDBFields.LOCATION_TYPE);
		String country_id = (String)raw.get(LocationDBFields.COUNTRY_ID);
		return new DefaultLocation(id, name, country_id, l_type);
	}
	
	public String getCountryId(){
		return getCountry() == null ? null : getCountry().getCountryId();
	}
	
	public String getLocName() {
		return locName;
	}

	public Country getCountry() {
		return country;
	}

	public LocationType getType() {
		return type;
	}
	
	public String getLocationId(){
		return getId();
	}

	@Override
	public String toString() {
		return "DefaultLocation [locName=" + locName + ", country=" + country
				+ ", type=" + type + "]";
	}

}
