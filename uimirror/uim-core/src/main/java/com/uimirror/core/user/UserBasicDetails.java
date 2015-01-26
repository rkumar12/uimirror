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
import static com.uimirror.core.user.UserBasicDetailsDBFields.EDUCATION;
import static com.uimirror.core.user.UserBasicDetailsDBFields.JOB;
import static com.uimirror.core.user.UserBasicDetailsDBFields.PLACES_LIVED;
import static com.uimirror.core.user.UserBasicDetailsDBFields.PLACES_VISITED;
import static com.uimirror.core.user.UserBasicDetailsDBFields.FAMILY_INFO;
import static com.uimirror.core.user.UserBasicDetailsDBFields.LANGUAGES_KNOWN;
import static com.uimirror.core.user.UserBasicDetailsDBFields.ABOUT_ME;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.CollectionUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.UserCredential.UserCredentailBuilder;

/**
 * 
 * @author Jay
 */
public class UserBasicDetails extends AbstractBeanBasedDocument<UserBasicDetails> implements BeanValidatorService{

	
	private static final long serialVersionUID = -2765893206856182350L;
	private List<Education> education;
	private List<Job> job;
	private List<Place> placesLived;
	private List<Place> placesVisited;
	private List<FamilyInfo> familyInfo;
	private String aboutMe;
	private List<String> languagesKnown;
	
	public UserBasicDetails() {
		//NOP
	}
	
	private UserBasicDetails(UserBasicDetailsBuilder builder){
		super(builder.profileId);
		this.education = builder.education;
		this.job = builder.job;
		this.placesVisited = builder.placesVisited;
		this.placesLived = builder.placesLived;
		this.familyInfo = builder.familyInfo;
		this.aboutMe = builder.aboutMe;
		this.languagesKnown = builder.languagesKnown;
	}
	
	@Override
	public UserBasicDetails updateId(String id) {
		return new UserBasicDetailsBuilder(id)
				.addEducation(education)
				.addJob(job)
				.addPlacesLived(placesLived)
				.addPlacesVisited(placesVisited)
				.addFamilyInfo(familyInfo)
				.addAboutMe(aboutMe)
				.addLanguagesKnown(languagesKnown)
				.build();
	}
	
	/**
	 * Create a map that needs to be persisted
	 * @return state in a {@link Map}
	 * @throws IllegalStateException  when the state is not valid
	 */
	@Override
	public Map<String, Object> writeToMap() throws IllegalStateException{
		//First check if it represents a valid state then can be serialized
		if(!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * @return serialized {@link Map}
	 */
	public Map<String, Object> serailize(){
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(ID, getId());
		if(getEducation() != null){
			List<Map<String, Object>> educations = new ArrayList<Map<String,Object>>(getEducation().size());
			getEducation().forEach((education) -> {
				educations.add(education.writeToMap());
			});
			state.put(EDUCATION,educations);
		}
		
		if(getJob() != null){
			List<Map<String, Object>> jobs = new ArrayList<Map<String,Object>>(getJob().size());
			getJob().forEach((job) -> {
				jobs.add(job.writeToMap());
			});
			state.put(JOB, jobs);
		}
		
		if(getPlacesLived() != null){
			List<Map<String, Object>> placesLived = new ArrayList<Map<String,Object>>(getPlacesLived().size());
			getPlacesLived().forEach((placeVisited) -> {
				placesLived.add(placeVisited.writeToMap());
			});
			state.put(PLACES_LIVED, placesLived);
		}
		
		if(getPlacesVisited() != null){
			List<Map<String, Object>> placesVisited = new ArrayList<Map<String,Object>>(getPlacesVisited().size());
			getPlacesLived().forEach((placeVisited) -> {
				placesVisited.add(placeVisited.writeToMap());
			});
			state.put(PLACES_VISITED, placesVisited);
		}
		
		if(getFamilyInfo() != null){
			List<Map<String, Object>> familyInfo = new ArrayList<Map<String,Object>>(getFamilyInfo().size());
			getFamilyInfo().forEach((family) -> {
				familyInfo.add(family.writeToMap());
			});
			state.put(FAMILY_INFO, familyInfo);
		}
		
		state.put(ABOUT_ME, aboutMe);
		
		state.put(LANGUAGES_KNOWN, getLanguagesKnown());
		
		return state;
		
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public UserBasicDetails readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}
	
	/**
	 * converts a map that comes from DB into UserBasicDetails object.
	 * @param raw {@link Map} from which it will be initialized
	 * @return {@link UserBasicDetails}
	 */
	private UserBasicDetails init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		List<Education> education = null;
		List<Job> job = null;
		List<Place> lived = null;
		List<Place> visited = null;
		List<FamilyInfo> family = null;
		
		List<Map<String, Object>> educations = (List<Map<String, Object>>)raw.get(EDUCATION);
		if(null != educations){
			
			education = new ArrayList<Education>(educations.size());
			for(Map<String, Object> educationsMap : educations){
				education.add(new Education().readFromMap(educationsMap));
			}
		}
		
		List<Map<String, Object>> jobs = (List<Map<String, Object>>)raw.get(JOB);
		if(null != jobs){
			job = new ArrayList<Job>(jobs.size());
			
			for(Map<String, Object> jobsMap : jobs){
				
				job.add(new Job().readFromMap(jobsMap));
			}
		}
		
		
		List<Map<String, Object>> placesLived = (List<Map<String, Object>>)raw.get(PLACES_LIVED);
		if(null != placesLived){
			lived = new ArrayList<Place>(placesLived.size());
			
			for(Map<String, Object> livedMap : placesLived){
				
				lived.add(new Place().readFromMap(livedMap));
			}
		}
		
		List<Map<String, Object>> placesVisited = (List<Map<String, Object>>)raw.get(PLACES_VISITED);
		if(null != placesLived){
			visited = new ArrayList<Place>(placesVisited.size());
			
			for(Map<String, Object> visitedMap : placesVisited){
				
				visited.add(new Place().readFromMap(visitedMap));
			}
		}
		
		List<Map<String, Object>> familyInfo = (List<Map<String, Object>>)raw.get(FAMILY_INFO);
		if(null != familyInfo){
			family = new ArrayList<FamilyInfo>(familyInfo.size());
			
			for(Map<String, Object> familyMap : familyInfo){
				
				family.add(new FamilyInfo().readFromMap(familyMap));
			}
		}
		
		String aboutMe = (String)raw.get(ABOUT_ME);
		
		List<String> languagesKnown = (List<String>) raw.get(LANGUAGES_KNOWN);
		
		
		UserBasicDetailsBuilder builder = new UserBasicDetailsBuilder(id)
		.addEducation(education)
		.addJob(job)
		.addPlacesLived(lived)
		.addPlacesVisited(visited)
		.addFamilyInfo(family)
		.addAboutMe(aboutMe)
		.addLanguagesKnown(languagesKnown);
		
		return builder.build();
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(CollectionUtils.isEmpty(getEducation()))
			valid = Boolean.FALSE;
		if(!CollectionUtils.isEmpty(getJob()))
			valid = Boolean.FALSE;
		
		return valid;
	}
	
	public static class UserBasicDetailsBuilder implements Builder<UserBasicDetails>{
		
		private String profileId;
		private List<Education> education;
		private List<Job> job;
		private List<Place> placesLived;
		private List<Place> placesVisited;
		private List<FamilyInfo> familyInfo;
		private String aboutMe;
		private List<String> languagesKnown;
		
		public UserBasicDetailsBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public UserBasicDetailsBuilder addEducation(List<Education> education) {
			this.education = education;
			return this;
		}
		
		public UserBasicDetailsBuilder addJob(List<Job> job) {
			this.job = job;
			return this;
		}
		
		public UserBasicDetailsBuilder addPlacesLived(List<Place> placesLived) {
			this.placesLived = placesLived;
			return this;
		}
		
		public UserBasicDetailsBuilder addPlacesVisited(List<Place> placesVisited) {
			this.placesVisited = placesVisited;
			return this;
		}
		
		public UserBasicDetailsBuilder addFamilyInfo(List<FamilyInfo> familyInfo) {
			this.familyInfo = familyInfo;
			return this;
		}
		
		public UserBasicDetailsBuilder addAboutMe(String aboutMe) {
			this.aboutMe = aboutMe;
			return this;
		}
		
		public UserBasicDetailsBuilder addLanguagesKnown(List<String> languagesKnown) {
			this.languagesKnown = languagesKnown;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public UserBasicDetails build() {
			return new UserBasicDetails(this);
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

	public List<Education> getEducation() {
		return education;
	}

	public List<Job> getJob() {
		return job;
	}

	public List<Place> getPlacesLived() {
		return placesLived;
	}

	public List<Place> getPlacesVisited() {
		return placesVisited;
	}

	public List<FamilyInfo> getFamilyInfo() {
		return familyInfo;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public List<String> getLanguagesKnown() {
		return languagesKnown;
	}
	
	

}
