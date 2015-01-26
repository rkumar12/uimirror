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
import static com.uimirror.core.user.JobDBFields.EMPLOYER_NAME;
import static com.uimirror.core.user.JobDBFields.END_DATE;
import static com.uimirror.core.user.JobDBFields.POSITION_HELD;
import static com.uimirror.core.user.JobDBFields.START_DATE;

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
 * This class contains all the job details of a person like the name of the organization and the his job position, joining date and the exit date along
 * with location details of the organization 
 * These details will be saved in the DB uim_usr schema with the collection name basic_info
 * @author Jay
 */
public class Job extends AbstractBeanBasedDocument<Job> implements BeanValidatorService{
	
	private static final long serialVersionUID = 8447569801508497546L;
	private String employerName; 
	private String positionHeld;
	private String startDate;
	private String endDate;
	private GeoLongLat geoLongLat;
	
	public Job() {
		// NOP
	}
	
	public Job update(String id){
		return new JobBuilder(id)
		.addEmployeeName(employerName)
		.addPositionHeld(positionHeld)
		.addStartDate(startDate)
		.addEndDate(endDate)
		.addGeoLongLat(geoLongLat)
		.build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public Job readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}
	
	/**
	 * converts a map that comes from DB into Job object.
	 * @param raw {@link Map} from which it will be initialized
	 * @return {@link Job}
	 */
	private Job init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		String employerName = (String) raw.get(EMPLOYER_NAME);
		String startDate = (String) raw.get(START_DATE);
		String endDate = (String) raw.get(END_DATE);
		String positionHeld = (String)raw.get(POSITION_HELD);
		GeoLongLat geoLongLatitude = GeoLongLat.initFromGeoCordMap(raw);
		JobBuilder builder = new JobBuilder(id)
		.addEmployeeName(employerName)
		.addPositionHeld(positionHeld)
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
		if (!StringUtils.hasText(getEmployerName()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getPositionHeld()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getStartDate()))
			valid = Boolean.FALSE;
		if(geoLongLat == null){
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
		state.put(EMPLOYER_NAME,getEmployerName());
		if(StringUtils.hasText(getPositionHeld()))
			state.put(POSITION_HELD, getPositionHeld());
		state.put(START_DATE, getStartDate());
		if(StringUtils.hasText(getEndDate()))
			state.put(END_DATE, getEndDate());
		state.putAll(geoLongLat.toGeoCordMap());
		return state;
	}
	
	
	private Job(JobBuilder builder){
		super(builder.profileId);
		this.employerName = builder.employerName;
		this.positionHeld = builder.positionHeld;
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.geoLongLat = builder.geoLongLat;
	}
	
	
	
	
	public static class JobBuilder implements Builder<Job>{
		
		private String profileId;
		private String employerName; 
		private String positionHeld;
		private String startDate;
		private String endDate;
		private GeoLongLat geoLongLat;
		
		public JobBuilder(String profileId) {
			this.profileId = profileId;
		}
		
		public JobBuilder addEmployeeName(String employerName){
			this.employerName = employerName;
			return this;
		}
		
		public JobBuilder addPositionHeld(String positionHeld){
			this.positionHeld = positionHeld;
			return this;
		}
		
		public JobBuilder addStartDate(String startDate){
			this.startDate = startDate;
			return this;
		}
		public JobBuilder addEndDate(String endDate){
			this.endDate = endDate;
			return this;
		}
		
		public JobBuilder addGeoLongLat(GeoLongLat geoLongLat){
			this.geoLongLat = geoLongLat;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public Job build() {

			return new Job(this);
		}
		
	}

	public String getProfileId() {
		return getId();
	}

	public String getEmployerName() {
		return employerName;
	}

	public String getPositionHeld() {
		return positionHeld;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public GeoLongLat getGeoLongLat() {
		return geoLongLat;
	}
	


	
	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}

}
