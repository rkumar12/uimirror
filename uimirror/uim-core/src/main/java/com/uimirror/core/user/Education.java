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
import static com.uimirror.core.user.EducationDBFields.INSTITUTION_NAME;
import static com.uimirror.core.user.EducationDBFields.START_DATE;
import static com.uimirror.core.user.EducationDBFields.EDUCATION_TYPE;
import static com.uimirror.core.user.EducationDBFields.END_DATE;

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
 * contains all the educational background of a person like the school or college name, type of degree, start and completion date along the 
 * location details of the school or college
 * These details will be saved in the DB uim_usr schema with the collection name basic_info 
 * @author Jay
 */
public final class Education extends AbstractBeanBasedDocument<Education> implements BeanValidatorService{
	
	private static final long serialVersionUID = 9212338247924114202L;
	private EducationType type;
	private String institutionName;
	private String startDate;
	private String endDate;
	private GeoLongLat geoLongLat;
	
	/**
	 * 
	 */
	public Education() {
		// NOP
	}
	
	private Education(EducationBuilder builder){
		super(builder.profileId);
		this.institutionName = builder.institutionName;
		this.type = builder.type;
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.geoLongLat = builder.geoLongLat;
	}
	
	public Education updateId(String id){
		return new EducationBuilder(id)
		.addInstitutionName(institutionName)
		.addType(type)
		.addStartDate(startDate)
		.addEndDate(endDate)
		.addGeoLongLat(geoLongLat)
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
		state.put(EDUCATION_TYPE, type.getType());
		state.put(INSTITUTION_NAME,getInstitutionName());
		state.put(START_DATE, getStartDate());
		if(StringUtils.hasText(getEndDate()))
			state.put(END_DATE, getEndDate());
		state.putAll(geoLongLat.toGeoCordMap());
		return state;
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

	public EducationType getType() {
		return type;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public GeoLongLat getGeoLatLong() {
		return geoLongLat;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public Education readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}
	
	/**
	 * converts a map that comes from DB into Education object.
	 * @param raw {@link Map} from which it will be intialized
	 * @return {@link Education}
	 */
	private Education init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		String institutionName = (String) raw.get(INSTITUTION_NAME);
		String startDate = (String) raw.get(START_DATE);
		String endDate = (String) raw.get(END_DATE);
		String educationType = (String)raw.get(EDUCATION_TYPE);
		EducationType education = StringUtils.hasText(educationType) ? EducationType.getEnum(educationType) : null;
		GeoLongLat geoLongLatitude = GeoLongLat.initFromGeoCordMap(raw);
		EducationBuilder builder = new EducationBuilder(id)
		.addInstitutionName(institutionName)
		.addType(education)
		.addStartDate(startDate)
		.addEndDate(endDate)
		.addGeoLongLat(geoLongLatitude);
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if (!StringUtils.hasText(getInstitutionName()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getStartDate()))
			valid = Boolean.FALSE;
		if(type == null){
			valid = Boolean.FALSE;
		}
		if(geoLongLat == null){
			valid = Boolean.FALSE;
		}
		
		return valid;
	}

	public static class EducationBuilder implements Builder<Education>{

		private String profileId;
		private EducationType type;
		private String institutionName;
		private String startDate;
		private String endDate;
		private GeoLongLat geoLongLat;
		
		
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Education build() {
			return new Education(this);
		}
		
		public EducationBuilder(String profileId) {
			this.profileId = profileId;
		}
		
		public EducationBuilder addType(EducationType type){
			this.type = type;
			return this;
		}
		
		public EducationBuilder addInstitutionName(String institutionName){
			this.institutionName = institutionName;
			return this;
		}
		
		public EducationBuilder addStartDate(String startDate){
			this.startDate = startDate;
			return this;
		}
		
		public EducationBuilder addEndDate(String endDate){
			this.endDate = endDate;
			return this;
		}
		
		public EducationBuilder addGeoLongLat(GeoLongLat geoLongLat){
			this.geoLongLat = geoLongLat;
			return this;
		}
	}

}
