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
import static com.uimirror.core.user.PlaceDBFields.ADDRESS_TYPE;
import static com.uimirror.core.user.PlaceDBFields.END_DATE;
import static com.uimirror.core.user.PlaceDBFields.START_DATE;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.GeoLongLat;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * contains all the details of place where the person has lived or visited.
 *  details like location, start date and end date and it is his/her permanent or present stay.
 * These details will be saved in the DB uim_usr schema with the collection name place_info 
 * @author Jay
 */

public class Place extends AbstractBeanBasedDocument<Place> implements BeanValidatorService{

	
	private static final long serialVersionUID = 8074050889519918339L;
	private GeoLongLat geoLongLat;
	private String startDate;
	private String endDate;
	private AddressType type;

	/**
	 * 
	 */
	public Place() {
		//NOP
	}
	
	private Place(PlaceBuilder builder){
		
		super(builder.profileId);
		this.geoLongLat = builder.geoLongLat;
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.type = builder.type;
	}
	
	public Place update(String id){
		return new PlaceBuilder(id)
		.addGeoLongLat(geoLongLat)
		.addStartDate(startDate)
		.addEndDate(endDate)
		.addType(type)
		.build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public Place readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}
	
	/**
	 * converts a map that comes from DB into Place object.
	 * @param raw {@link Map} from which it will be initialized
	 * @return {@link Place}
	 */
	private Place init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		GeoLongLat geoLongLatitude = GeoLongLat.initFromGeoCordMap(raw);
		String startDate = (String) raw.get(START_DATE);
		String endDate = (String) raw.get(END_DATE);
		String addressType = (String)raw.get(ADDRESS_TYPE);
		AddressType type = StringUtils.hasText(addressType) ? AddressType.getEnum(addressType) : null;
		PlaceBuilder builder = new PlaceBuilder(id)
		.addGeoLongLat(geoLongLatitude)
		.addType(type)
		.addStartDate(startDate)
		.addEndDate(endDate);
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(geoLongLat == null){
			valid = Boolean.FALSE;
		}
		if (!StringUtils.hasText(getStartDate()))
			valid = Boolean.FALSE;
		if(type == null){
			valid = Boolean.FALSE;
		}
		
		return valid;
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
		state.putAll(geoLongLat.toGeoCordMap());
		if(type != null){
			state.put(ADDRESS_TYPE, type.getType());
		}
		state.put(START_DATE, getStartDate());
		if(StringUtils.hasText(getEndDate()))
			state.put(END_DATE, getEndDate());
		return state;
	}
	
	public static class PlaceBuilder implements Builder<Place>{
		
		private String profileId;
		private GeoLongLat geoLongLat;
		private String startDate;
		private String endDate;
		private AddressType type;
		
		public PlaceBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public PlaceBuilder addGeoLongLat(GeoLongLat geoLongLat){
			this.geoLongLat = geoLongLat;
			return this;
		}
		
		public PlaceBuilder addStartDate(String startDate){
			this.startDate = startDate;
			return this;
		}
		
		public PlaceBuilder addEndDate(String endDate){
			this.endDate = endDate;
			return this;
		}
		
		public PlaceBuilder addType(AddressType type){
			this.type = type;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Place build() {
			return new Place(this);
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

	public GeoLongLat getGeoLongLat() {
		return geoLongLat;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public AddressType getType() {
		return type;
	}

}
