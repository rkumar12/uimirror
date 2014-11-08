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
import java.util.WeakHashMap;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.GeoLongLat;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.location.core.components.AddressComponentType;

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
	private String name;
	private GeoLongLat location;
	private Country country;
	private State state;
	private City city;
	private Locality locality;
	private String pin;
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
		if(StringUtils.hasText(getName()))
			state.put(LocationDBFields.NAME, getName());
		state.putAll(getLocation().toGeoCordMap());
		if(StringUtils.hasText(getCountryId()))
			state.put(LocationDBFields.COUNTRY_ID, getCountryId());
		if(StringUtils.hasText(getStateId()))
			state.put(LocationDBFields.STATE_ID, getStateId());
		if(StringUtils.hasText(getCityId()))
			state.put(LocationDBFields.CITY_ID, getCityId());
		if(StringUtils.hasText(getLocalityId()))
			state.put(LocationDBFields.LOCALITY_ID, getLocalityId());
		if(StringUtils.hasText(getPin()))
			state.put(LocationDBFields.PIN, getPin());
		if(getType() != null)
			state.put(LocationDBFields.LOCATION_TYPE, getType().getLocType());
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java.util.Map)
	 */
	@Override
	public DefaultLocation initFromMap(Map<String, Object> raw) {
		String id = (String)raw.get(LocationDBFields.ID);
		String name = (String)raw.get(LocationDBFields.NAME);
		GeoLongLat geoLongLat = GeoLongLat.initFromGeoCordMap(raw);
		String country_id = (String)raw.get(LocationDBFields.COUNTRY_ID);
		String state_id = (String)raw.get(LocationDBFields.STATE_ID);
		String city_id = (String)raw.get(LocationDBFields.CITY_ID);
		String locality_id = (String)raw.get(LocationDBFields.LOCALITY_ID);
		String pin = (String)raw.get(LocationDBFields.PIN);
		String l_type = (String)raw.get(LocationDBFields.LOCATION_TYPE);
		LocationBuilder builder = new LocationBuilder(id);
		if(StringUtils.hasText(name))
			builder.updateName(name);
		builder.updateLongLat(geoLongLat);
		if(StringUtils.hasText(country_id))
			builder.updateCountry(country_id);
		if(StringUtils.hasText(state_id))
			builder.updateState(state_id);
		if(StringUtils.hasText(city_id))
			builder.updateCity(city_id);
		if(StringUtils.hasText(locality_id))
			builder.updateLocality(locality_id);
		if(StringUtils.hasText(pin))
			builder.updatePin(pin);
		if(StringUtils.hasText(l_type))
			builder.updateLocationType(l_type);
		return builder.build();
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
	
	public double getLongiTude(){
		return getLocation().getLongitude();
	}

	public double getLatiTude(){
		return getLocation().getLatitude();
	}

	public AddressComponentType getType() {
		return type;
	}

	public String getLocationId(){
		return getId();
	}
	
	public State getState() {
		return state;
	}

	public String getStateId(){
		return getState() == null ? null : getState().getStateId();
	}
	public City getCity() {
		return city;
	}
	public String getCityId() {
		return getCity() == null ? null : getCity().getCityId();
	}

	public String getPin() {
		return pin;
	}

	public String getName() {
		return WordUtils.capitalize(name, ',');
	}
	public Locality getLocality() {
		return locality;
	}
	public String getLocalityId() {
		return getLocality() == null ? null : getLocality().getLocalityId();
	}

	@Override
	public String toString() {
		return "DefaultLocation [name=" + name + ", location=" + location
				+ ", country=" + country + ", state=" + state + ", city="
				+ city + ", locality=" + locality + ", pin=" + pin + ", type="
				+ type + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((locality == null) ? 0 : locality.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pin == null) ? 0 : pin.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		DefaultLocation other = (DefaultLocation) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (locality == null) {
			if (other.locality != null)
				return false;
		} else if (!locality.equals(other.locality))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.equals(other.pin))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (type != other.type)
			return false;
		return true;
	}



	/**
	 * Builder To build the location from the given state.
	 * @author Jay
	 */
	public static class LocationBuilder implements Builder<DefaultLocation>{
		private String id;
		private GeoLongLat location;
		private Country country;
		private State state;
		private City city;
		private Locality locality;
		private String pin;
		private AddressComponentType type;
		private String name;
		
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
		public LocationBuilder updateName(String name){
			this.name = name;
			return this;
		}
		public LocationBuilder updateLongLat(GeoLongLat location){
			Assert.notNull(location, "Location ID should present");
			this.location = location;
			return this;
		}
		
		public LocationBuilder updateCountry(Country country){
			Assert.notNull(country, "Country Can't be empty");
			this.country = country;
			return this;
		}
		
		public LocationBuilder updateCountry(String countryId){
			Assert.hasText(countryId, "Country Can't be empty");
			this.country = new Country.CountryBuilder(countryId).build();
			return this;
		}
		public LocationBuilder updateState(State state){
			Assert.notNull(state, "State Can't be empty");
			this.state = state;
			return this;
		}
		
		public LocationBuilder updateState(String stateId){
			Assert.hasText(stateId, "State Can't be empty");
			this.state = new State.StateBuilder(stateId).build();
			return this;
		}
		public LocationBuilder updateCity(City city){
			Assert.notNull(city, "City Can't be empty");
			this.city = city;
			return this;
		}
		
		public LocationBuilder updateCity(String cityId){
			Assert.hasText(cityId, "City Can't be empty");
			this.city = new City.CityBuilder(cityId).build();
			return this;
		}
		public LocationBuilder updateLocality(Locality locality){
			Assert.notNull(locality, "Locality Can't be empty");
			this.locality = locality;
			return this;
		}
		
		public LocationBuilder updateLocality(String localityId){
			Assert.hasText(localityId, "Locality Can't be empty");
			this.locality = new Locality.LocalityBuilder(localityId).build();
			return this;
		}
		public LocationBuilder updatePin(String pin){
			Assert.hasText(pin, "PIN Can't be empty");
			this.pin = pin;
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
		this.name = builder.name;
		this.location = builder.location;
		this.country = builder.country;
		this.state = builder.state;
		this.city = builder.city;
		this.pin = builder.pin;
		this.type = builder.type;
		this.locality = builder.locality;
		super.setId(builder.id);
	}
	
	/**
	 * Gets the expanded map that indeed to send to the caller
	 * @return
	 */
	public Map<String, Object> getExpandedLoc(){
		Map<String, Object> rs = new WeakHashMap<String, Object>(5);
		rs.put(LocationDBFields.ID, getLocationId());
		if(StringUtils.hasText(getName()))
			rs.put(LocationDBFields.NAME, getName());
		if(getLocation() != null)
			rs.put(LocationDBFields.GEOMETRY, getLocation().toGeoCordMap());
		if(getCountry() != null)
			rs.put(LocationDBFields.COUNTRY, getCountry().toMap());
		if(getState() != null)
			rs.put(LocationDBFields.STATE, getState().toMap());
		if(getCity() != null)
			rs.put(LocationDBFields.CITY, getCity().toMap());
		if(getLocality() != null)
			rs.put(LocationDBFields.LOCALITY, getLocality().toMap());
		if(getType() != null)
			rs.put(LocationDBFields.LOCATION_TYPE, getType().getLocType());
		if(StringUtils.hasText(getPin()))
			rs.put(LocationDBFields.PIN, getPin());
		return rs;
	}
	
	/**
	 * gets the short Location map
	 * @return
	 */
	public Map<String, Object> getShortLoc(){
		Map<String, Object> rs = new WeakHashMap<String, Object>(5);
		rs.put(LocationDBFields.ID, getLocationId());
		if(StringUtils.hasText(getName()))
			rs.put(LocationDBFields.NAME, getName());
		if(getLocation() != null)
			rs.put(LocationDBFields.GEOMETRY, getLocation().toGeoCordMap());
		if(getCountryId() != null)
			rs.put(LocationDBFields.COUNTRY_ID, getCountryId());
		if(getStateId() != null)
			rs.put(LocationDBFields.STATE_ID, getStateId());
		if(getCityId() != null)
			rs.put(LocationDBFields.CITY_ID, getCityId());
		if(getLocalityId() != null)
			rs.put(LocationDBFields.LOCALITY_ID, getLocalityId());
		if(getType() != null)
			rs.put(LocationDBFields.LOCATION_TYPE, getType().getLocType());
		if(StringUtils.hasText(getPin()))
			rs.put(LocationDBFields.PIN, getPin());
		return rs;
	}
	
	/**
	 * Gets the updated instance by updating the given parameters
	 * @param old
	 * @param country
	 * @param state
	 * @param city
	 * @param locality
	 * @return
	 */
	public DefaultLocation getUpdatedInstance(Country country, State state, City city, Locality locality) {
		LocationBuilder builder = new DefaultLocation.LocationBuilder(getLocationId());
		builder.updateLongLat(getLocation());
		builder.updateName(getName());
		if(country != null)
			builder.updateCountry(country);
		if(state != null)
			builder.updateState(state);
		if(city != null)
			builder.updateCity(city);
		if(locality != null)
			builder.updateLocality(locality);
		if(getPin() != null)
			builder.updatePin(getPin());
		builder.updateLocationType(getType());
		return builder.build();
	}
	
	/**
	 * Checks if the collate required, i.e if it doesn't have city id, country id, locality id, state id or city id
	 * @param loc
	 * @return
	 */
	public boolean isCollateRequired(){
		boolean collateRequired = Boolean.FALSE;
		if(getCountry() != null &&  getCountry().getName() == null && getCountry().getShortName() == null){
			collateRequired =  Boolean.TRUE;
		}else if(getState() != null &&  getState().getName() == null && getState().getShortName() == null){
			return Boolean.TRUE;
		}else if(getCity() != null &&  getCity().getName() == null && getCity().getShortName() == null){
			return Boolean.TRUE;
		}
		return collateRequired;
	}

}
