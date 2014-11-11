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

import java.util.Map;
import java.util.WeakHashMap;

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
	private final String presentAddress;
	private final String permanetAddress;
	private final DOB dateOfBirth;
	private final MetaInfo metaInfo;
	private final Map<String, Object> details;

	// DOn't Use this until it has specific requirement
	public BasicDetails() {
		this.permanetAddress = null;
		this.presentAddress = null;
		this.dateOfBirth = null;
		this.metaInfo = null;
		this.details = null;
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
			state.put(UserDBFields.ID, getProfileId());
		if(StringUtils.hasText(getPermanetAddress()))
			state.put(UserDBFields.PERMANET_ADDRESS, getPermanetAddress());
		if(StringUtils.hasText(getPresentAddress()))
			state.put(UserDBFields.PRESENT_ADDRESS, getPresentAddress());
		state.put(UserDBFields.DATE_OF_BIRTH, getDateOfBirth().toMap());
		if(getMetaInfo() != null)
			state.put(UserDBFields.META_INFO, getMetaInfo().toMap());
		if(!CollectionUtils.isEmpty(getDetails()))
			state.put(UserDBFields.INFO, getDetails());
		return state;
	}

	/**
	 * converts a map that comes from DB into BasicDetails object.
	 * 
	 * @param raw from which it will be serialized
	 * @return {@link BasicDetails}
	 */
	@SuppressWarnings("unchecked")
	private BasicDetails init(Map<String, Object> raw) {
		String id = (String) raw.get(UserDBFields.ID);
		DOB dob = DOB.initFromMap(raw);
		MetaInfo info = MetaInfo.initFromMap(raw);
		String presentAddId = (String)raw.get(UserDBFields.PRESENT_ADDRESS);
		String permanetAddId = (String)raw.get(UserDBFields.PERMANET_ADDRESS);
		Map<String, Object> extraInfo = (Map<String, Object>)raw.get(UserDBFields.INFO);
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
		return "BasicDetails [presentAddress=" + presentAddress
				+ ", permanetAddress=" + permanetAddress + ", dateOfBirth="
				+ dateOfBirth + ", metaInfo=" + metaInfo + ", details="
				+ details + "]";
	}

}
