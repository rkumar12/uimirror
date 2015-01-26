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
import static com.uimirror.core.user.FamilyInfoDBDetails.CREATED_ON;
import static com.uimirror.core.user.FamilyInfoDBDetails.FROM_WHEN;
import static com.uimirror.core.user.FamilyInfoDBDetails.RELATION_ID;
import static com.uimirror.core.user.FamilyInfoDBDetails.RELATION_TYPE;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;


/**
 * Contains all the family relationship details of the person like relationship type of the relative.
 * These details will be saved in the DB uim_usr schema with the collection name family_info 
 * @author Jay
 */
public class FamilyInfo extends AbstractBeanBasedDocument<FamilyInfo> implements BeanValidatorService{

	
	private static final long serialVersionUID = -6842486542778089221L;
	
	private String relationId;
	private RelationShipType relationShipType;
	private String createdOn;
	private String fromWhen;
	

	public FamilyInfo() {
		// NOP
	}
	
	public FamilyInfo(FamilyInfoBuilder builder){
		super(builder.profileId);
		this.relationId = builder.relationId;
		this.relationShipType = builder.relationShipType;
		this.createdOn = builder.createdOn;
		this.fromWhen = builder.fromWhen;
	}
	
	public FamilyInfo update(String id){
		return new FamilyInfoBuilder(id)
		.addRelationId(relationId)
		.addCreatedOn(createdOn)
		.addRelationShipType(relationShipType)
		.addFromWhen(fromWhen)
		.build();
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public FamilyInfo readFromMap(Map<String, Object> src) {
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
	private FamilyInfo init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		String relationId = (String) raw.get(RELATION_ID);
		String createdOn = (String) raw.get(CREATED_ON);
		String fromWhen = (String) raw.get(FROM_WHEN);
		String relation = (String)raw.get(RELATION_TYPE);
		RelationShipType relationType = StringUtils.hasText(relation) ? RelationShipType.getEnum(relation) : null;
		FamilyInfoBuilder builder = new FamilyInfoBuilder(id)
		.addRelationId(relationId)
		.addCreatedOn(createdOn)
		.addRelationShipType(relationType)
		.addFromWhen(fromWhen);

		return builder.build();
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
		state.put(RELATION_ID, getRelationId());
		if(relationShipType != null){
			state.put(RELATION_TYPE, relationShipType.getRelationType());
		}
		state.put(CREATED_ON, getCreatedOn());
		state.put(FROM_WHEN, getFromWhen());
		return state;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if (!StringUtils.hasText(getRelationId()))
			valid = Boolean.FALSE;
		if(relationShipType == null){
			valid = Boolean.FALSE;
		}
		
		return valid;
	}
	public static class FamilyInfoBuilder implements Builder<FamilyInfo>{
		
		private String profileId;
		private String relationId;
		private RelationShipType relationShipType;
		private String createdOn;
		private String fromWhen;

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public FamilyInfo build() {
			return new FamilyInfo(this);
		}
		
		public FamilyInfoBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public FamilyInfoBuilder addRelationId(String relationId){
			this.relationId = relationId;
			return this;
		}
		
		public FamilyInfoBuilder addRelationShipType(RelationShipType relationShipType){
			this.relationShipType = relationShipType;
			return this;
		}
		
		public FamilyInfoBuilder addCreatedOn(String createdOn){
			this.createdOn = createdOn;
			return this;
		}
		
		public FamilyInfoBuilder addFromWhen(String fromWhen){
			this.fromWhen = fromWhen;
			return this;
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
	
	public String getRelationId() {
		return relationId;
	}

	public RelationShipType getRelationShipType() {
		return relationShipType;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public String getFromWhen() {
		return fromWhen;
	}

}
