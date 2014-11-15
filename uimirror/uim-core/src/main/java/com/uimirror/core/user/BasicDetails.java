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
import static com.uimirror.core.user.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.user.UserDBFields.INFO;
import static com.uimirror.core.user.UserDBFields.META_INFO;
import static com.uimirror.core.user.UserDBFields.PERMANET_ADDRESS;
import static com.uimirror.core.user.UserDBFields.PRESENT_ADDRESS;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.DOB;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the User details of the user
 * @author Jay
 */
public class BasicDetails extends AbstractBeanBasedDocument<BasicDetails> implements BeanValidatorService {

	private static final long serialVersionUID = -5282406171053226490L;
	private String presentAddress;
	private String permanetAddress;
	private DOB dateOfBirth;
	private MetaInfo metaInfo;
	private Map<String, Object> details;

	// DOn't Use this until it has specific requirement
	public BasicDetails() {
		//NOP
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#updateId()
	 */
	@Override
	public BasicDetails updateId(String id) {
		return new BasicDetailsBuilder(id).
				updateDetails(details).
				updateDOB(dateOfBirth).
				updatePermanetAddress(permanetAddress).
				updatePresentAddress(presentAddress).
				updateMetaInfo(metaInfo).
				build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java
	 * .util.Map)
	 */
	@Override
	public BasicDetails readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);
	}
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(getDateOfBirth() == null || !getDateOfBirth().isMoreThanighteen())
			valid = Boolean.FALSE;
		return valid;
	}

	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return {@link Map} represention of the current state
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new WeakHashMap<String, Object>(16);
		if(StringUtils.hasText(getProfileId()))
			state.put(ID, getProfileId());
		if(StringUtils.hasText(getPermanetAddress()))
			state.put(PERMANET_ADDRESS, getPermanetAddress());
		if(StringUtils.hasText(getPresentAddress()))
			state.put(PRESENT_ADDRESS, getPresentAddress());
		state.put(DATE_OF_BIRTH, getDateOfBirth().toMap());
		if(getMetaInfo() != null)
			state.put(META_INFO, getMetaInfo().toMap());
		if(!CollectionUtils.isEmpty(getDetails()))
			state.put(INFO, getDetails());
		return state;
	}

	/**
	 * converts a map that comes from DB into BasicDetails object.
	 * 
	 * @param raw from which it will be serialized
	 * @return {@link BasicDetails}
	 */
	private BasicDetails init(Map<String, Object> raw) {
		MetaInfo info = null;
		String id = (String) raw.get(ID);
		DOB dob = DOB.initFromMap(raw);
		@SuppressWarnings("unchecked")
		Map<String, Object> metaMap = (Map<String, Object>)raw.get(META_INFO);
		if(!CollectionUtils.isEmpty(metaMap))
			info = MetaInfo.initFromMap(metaMap);
		String presentAddId = (String)raw.get(PRESENT_ADDRESS);
		String permanetAddId = (String)raw.get(PERMANET_ADDRESS);
		@SuppressWarnings("unchecked")
		Map<String, Object> extraInfo = (Map<String, Object>)raw.get(INFO);
		return new BasicDetailsBuilder(id).
				updateDetails(extraInfo).
				updateDOB(dob).
				updatePermanetAddress(permanetAddId).
				updatePresentAddress(presentAddId).
				updateMetaInfo(info).
				build();
	}
	
	public static class BasicDetailsBuilder implements Builder<BasicDetails>{
		private String profileId;
		private String presentAddress;
		private String permanetAddress;
		private DOB dateOfBirth;
		private Map<String, Object> details;
		private MetaInfo metaInfo;
		
		public BasicDetailsBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public BasicDetailsBuilder updatePresentAddress(String locationId){
			this.presentAddress = locationId;
			return this;
		}
		
		public BasicDetailsBuilder updatePermanetAddress(String locationId){
			this.permanetAddress = locationId;
			return this;
		}
		
		public BasicDetailsBuilder updateDOB(DOB dob){
			this.dateOfBirth = dob;
			return this;
		}
		
		public BasicDetailsBuilder updateDetails(Map<String, Object> details){
			this.details = details;
			return this;
		}
		
		public BasicDetailsBuilder updateMetaInfo(MetaInfo info){
			this.metaInfo = info;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public BasicDetails build() {
			return new BasicDetails(this);
		}
		
	}
	
	private BasicDetails(BasicDetailsBuilder builder){
		super(builder.profileId);
		this.dateOfBirth = builder.dateOfBirth;
		this.details = builder.details;
		this.permanetAddress = builder.permanetAddress;
		this.presentAddress = builder.presentAddress;
		this.metaInfo = builder.metaInfo;
	}
	
	public String getProfileId() {
		return getId();
	}

	public DOB getDateOfBirth() {
		return this.dateOfBirth;
	}

	public Map<String, Object> getDetails() {
		return this.details;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public String getPermanetAddress() {
		return permanetAddress;
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
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
