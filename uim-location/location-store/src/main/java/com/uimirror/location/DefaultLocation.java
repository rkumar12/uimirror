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

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.GeoLongLat;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.location.core.components.AddressComponentType;
import com.uimirror.location.core.components.LocationType;

/**
 * Location Bean document which will have location name, short name, 
 * location type and country details.
 * 
 * This will not mandate the information as such, but it required one location name should be populated.
 * 
 * <p>Country code and country short form is mandatory for every location.</p>
 * 
 * @author Jay
 */
public class DefaultLocation extends BeanBasedDocument<DefaultLocation> implements BeanValidatorService{
	
	private static final long serialVersionUID = 2347155622832246734L;
	private GeoLongLat location;
	private Country country;
	private LocationType accuracyType;
	private AddressComponentType type;

	//Don't Use It untill it has some special requirement
	public DefaultLocation() {
		super();
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
		if(getType() != null)
			state.put(LocationDBFields.LOCATION_TYPE, getType().getLocType());
		if(getAccuracyType() != null)
			state.put(LocationDBFields.LOCATION_ACCURACY_TYPE, getAccuracyType().getType());
		state.put(LocationDBFields.COUNTRY_ID, getCountryId());
		state.putAll(getLocation().toGeoCordMap());
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public DefaultLocation initFromMap(Map<String, Object> raw) {
		String id = (String)raw.get(LocationDBFields.ID);
		String l_type = (String)raw.get(LocationDBFields.LOCATION_TYPE);
		String accuracy_type = (String)raw.get(LocationDBFields.LOCATION_ACCURACY_TYPE);
		String country_id = (String)raw.get(LocationDBFields.COUNTRY_ID);
		GeoLongLat geoLongLat = GeoLongLat.initFromGeoCordMap(raw);
		Country country = new Country.CountryBuilder(country_id).build();
		return new LocationBuilder(id).updateAccuracy(accuracy_type).updateLocationType(l_type).updateCountry(country).updateLongLat(geoLongLat).build();
	}
	
	public String getCountryId(){
		return getCountry() == null ? null : getCountry().getCountryId();
	}
	
	public Country getCountry() {
		return country;
	}
	
	public GeoLongLat getLocation() {
		return location;
	}

	public LocationType getAccuracyType() {
		return accuracyType;
	}

	public AddressComponentType getType() {
		return type;
	}

	public String getLocationId(){
		return getId();
	}
	
	public static class LocationBuilder implements Builder<DefaultLocation>{
		private String id;
		private GeoLongLat location;
		private Country country;
		private LocationType accuracyType;
		private AddressComponentType type;
		
		public LocationBuilder(GeoLongLat location){
			this.location = location;
		}
		public LocationBuilder(String id){
			this.id = id;
		}
		
		public LocationBuilder updateId(String id){
			Assert.hasText(id, "Location ID should present");
			this.id = id;
			return this;
		}
		public LocationBuilder updateLongLat(GeoLongLat location){
			Assert.notNull(location, "Location ID should present");
			this.location = location;
			return this;
		}
		
		public LocationBuilder updateCountry(Country country){
			this.country = country;
			return this;
		}
		
		public LocationBuilder updateCountry(String countryId){
			this.country = new Country.CountryBuilder(countryId).build();
			return this;
		}
		
		public LocationBuilder updateAccuracy(String accuracy){
			Assert.hasText(accuracy, "Location Accuracy should present");
			this.accuracyType = LocationType.getEnum(accuracy);
			return this;
		}
		public LocationBuilder updateAccuracy(LocationType accuracyType){
			this.accuracyType = accuracyType;
			return this;
		}
		public LocationBuilder updateLocationType(String type){
			Assert.hasText(type, "Location Type should present");
			this.type = AddressComponentType.getEnum(type);
			return this;
		}
		public LocationBuilder updateLocationType(AddressComponentType type){
			this.type = type;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public DefaultLocation build() {
			return new DefaultLocation(this);
		}
		
	}
	/**
	 * @param builder
	 */
	private DefaultLocation(LocationBuilder builder){
		this.accuracyType = builder.accuracyType;
		this.country = builder.country;
		this.location = builder.location;
		this.type = builder.type;
		super.setId(builder.id);
	}

}
