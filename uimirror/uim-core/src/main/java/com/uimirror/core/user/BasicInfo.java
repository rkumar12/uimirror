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

import static com.uimirror.core.Constants.SINGLE_SPACE;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATUS;
import static com.uimirror.core.user.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.user.UserDBFields.EMAIL;
import static com.uimirror.core.user.UserDBFields.FIRST_NAME;
import static com.uimirror.core.user.UserDBFields.GENDER;
import static com.uimirror.core.user.UserDBFields.LAST_NAME;
import static com.uimirror.core.user.UserDBFields.BLOOD_GROUP;
import static com.uimirror.core.user.UserDBFields.RELATION_SHIP_STATUS;
import static com.uimirror.core.user.UserDBFields.CURRENCY;
import static com.uimirror.core.user.UserDBFields.LOCALE;
import static com.uimirror.core.user.UserDBFields.TIME_ZONE;
import static com.uimirror.core.user.UserDBFields.SNAP_LOC;
import static com.uimirror.core.user.UserDBFields.COVER_SNAP_LOC;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.DOB;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Basic Information of a user 
 * such as name, email, gender, date of birth, profile snap location, current account status and state of the account.
 * @author Jay
 */
public final class BasicInfo extends AbstractBeanBasedDocument<BasicInfo> implements BeanValidatorService {

	private static final long serialVersionUID = -5282406171053226490L;
	private String firstName;
	private String lastName;
	private String email;
	private Gender gender;
	private DOB dateOfBirth;
	private String profileSnapLoc;
	private String coverSnapLoc;
	private AccountStatus accountStatus;
	private AccountState accountState;
	private RelationShipStatus status;
	private BloodGroup bloodGroup;
	private Locales locale;
	private Currencies currency;
	private TimeZones timeZone;

	// DOn't Use this until it has specific requirement
	public BasicInfo() {
		//NOP
	}


	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#updateId(java.lang.String)
	 */
	@Override
	public BasicInfo updateId(String id) {
		return new BasicInfoBuilder(id).
				addEmail(email).
				addFirstName(firstName).
				addGender(gender).
				addLastName(lastName).
				addState(accountState).
				addDOB(dateOfBirth).
				addSnapLoc(profileSnapLoc).
				addCoverSnapLoc(coverSnapLoc).
				addStatus(accountStatus).
				addRelationShipStatus(status).
				addBloodGroup(bloodGroup).
				addLocale(locale).
				addCurrency(currency).
				addTimeZone(timeZone).
				build();
	}

	public BasicInfo enable() {
		return new BasicInfoBuilder(getProfileId()).
				addEmail(email).
				addFirstName(firstName).
				addGender(gender).
				addLastName(lastName).
				addDOB(dateOfBirth).
				addSnapLoc(profileSnapLoc).
				addCoverSnapLoc(coverSnapLoc).
				addState(AccountState.ENABLED).
				addStatus(accountStatus).
				addRelationShipStatus(status).
				addBloodGroup(bloodGroup).
				addLocale(locale).
				addCurrency(currency).
				addTimeZone(timeZone).
				build();
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
		if (!StringUtils.hasText(getFirstName()))
			valid = Boolean.FALSE;
		if (!StringUtils.hasText(getEmail()))
			valid = Boolean.FALSE;
		if (getGender() == null)
			valid = Boolean.FALSE;
		if(getAccountState() == null)
			valid = Boolean.FALSE;
		if(getAccountStatus() == null)
			valid = Boolean.FALSE;
		if(getDateOfBirth() == null || !getDateOfBirth().isMoreThanighteen())
			valid = Boolean.FALSE;
		if(getBloodGroup() == null){
			valid = Boolean.FALSE;
		}
		if(!StringUtils.hasText(getProfileSnapLoc())){
			valid = Boolean.FALSE;
		}
		if(!StringUtils.hasText(getCoverSnapLoc())){
			valid = Boolean.FALSE;
		}
		if(getStatus() == null){
			valid = Boolean.FALSE;
		}
		if(getTimeZone() == null){
			valid = Boolean.FALSE;
		}
		if(getLocale() == null){
			valid = Boolean.FALSE;
		}
		if(getCurrency() == null){
			valid = Boolean.FALSE;
		}
		return valid;

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
		state.put(FIRST_NAME, getFirstName());
		if(StringUtils.hasText(getLastName()))
			state.put(LAST_NAME, getLastName());
		state.put(EMAIL, getEmail());
		state.put(GENDER, getGender().toString());
		state.put(ACCOUNT_STATUS, getAccountStatus().getStatus());
		state.put(ACCOUNT_STATE, getAccountState().getState());
		state.put(DATE_OF_BIRTH, getDateOfBirth().toMap());
		if(StringUtils.hasText(getProfileSnapLoc()))
			state.put(SNAP_LOC, getProfileSnapLoc());
		if(StringUtils.hasText(getCoverSnapLoc()))
			state.put(COVER_SNAP_LOC, getCoverSnapLoc());
		state.put(BLOOD_GROUP, bloodGroup.getGroup());
		state.put(RELATION_SHIP_STATUS, status.getStatus());
		state.put(LOCALE, locale.getLocale());
		state.put(CURRENCY, currency.getCurrency());
		state.put(TIME_ZONE, timeZone.getTimeZone().getID());
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uimirror.core.mongo.feature.MongoDocumentSerializer#initFromMap(java
	 * .util.Map)
	 */
	@Override
	public BasicInfo readFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
		isValidSource(src);
		// Initialize the state
		return init(src);

	}

	/**
	 * converts a map that comes from DB into BasicInfo object.
	 * @param raw {@link Map} from which it will be intialized
	 * @return {@link BasicInfo}
	 */
	private BasicInfo init(Map<String, Object> raw) {
		String id = (String) raw.get(ID);
		String firstName = (String) raw.get(FIRST_NAME);
		String lastName = (String) raw.get(LAST_NAME);
		String email = (String) raw.get(EMAIL);
		String gender = (String) raw.get(GENDER);
		Gender genderVal = StringUtils.hasText(gender) ? Gender.getEnum(gender) : null;
		String statVal = (String) raw.get(ACCOUNT_STATUS);
		String stateVal = (String) raw.get(ACCOUNT_STATE);
		AccountStatus accountStatus = StringUtils.hasText(statVal) ? AccountStatus.getEnum(statVal): null;
		AccountState accountState = StringUtils.hasText(stateVal) ? AccountState.getEnum(stateVal): null;
		DOB dob = DOB.initFromMap(raw);
		String snapLoc = (String)raw.get(SNAP_LOC);
		String coverSnapLoc = (String)raw.get(COVER_SNAP_LOC);
		
		String group = (String)raw.get(BLOOD_GROUP);
		BloodGroup bloodGroup = StringUtils.hasText(group) ? BloodGroup.getEnum(group): null;
		String relationStatus = (String)raw.get(RELATION_SHIP_STATUS);
		RelationShipStatus status = StringUtils.hasText(relationStatus) ? RelationShipStatus.getEnum(relationStatus): null;
		String loc = (String)raw.get(LOCALE);
		Locales locale = StringUtils.hasText(loc) ? Locales.getEnum(loc): null;
		String curr = (String)raw.get(CURRENCY);
		Currencies currency = StringUtils.hasText(curr) ? Currencies.getEnum(curr): null;
		String zone = (String)raw.get(TIME_ZONE);
		TimeZones timeZone = StringUtils.hasText(zone) ? TimeZones.getEnum(zone): null;
		BasicInfoBuilder builder = new BasicInfoBuilder(id);
		builder.addEmail(email).addFirstName(firstName).
		addGender(genderVal).addLastName(lastName).addState(accountState).addStatus(accountStatus)
		.addDOB(dob).addCoverSnapLoc(coverSnapLoc).addSnapLoc(snapLoc)
		.addBloodGroup(bloodGroup)
		.addRelationShipStatus(status)
		.addLocale(locale)
		.addCurrency(currency)
		.addTimeZone(timeZone);
		
		return builder.build();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		if(StringUtils.hasText(lastName) && StringUtils.hasText(firstName))
			return this.firstName + SINGLE_SPACE+ this.lastName;
		else if(StringUtils.hasText(firstName))
			return this.firstName;
		return this.lastName;
	}
	
	public DOB getDateOfBirth() {
		return dateOfBirth;
	}

	public String getProfileSnapLoc() {
		return profileSnapLoc;
	}

	public String getCoverSnapLoc() {
		return coverSnapLoc;
	}

	public String getEmail() {
		return email;
	}

	public Gender getGender() {
		return gender;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public AccountState getAccountState() {
		return accountState;
	}
	
	

	public String getProfileId() {
		return getId();
	}
	

	public RelationShipStatus getStatus() {
		return status;
	}

	public BloodGroup getBloodGroup() {
		return bloodGroup;
	}

	public Locales getLocale() {
		return locale;
	}

	public Currencies getCurrency() {
		return currency;
	}

	public TimeZones getTimeZone() {
		return timeZone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((getProfileId() == null) ? 0 : getProfileId().hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicInfo other = (BasicInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (getProfileId() == null) {
			if (other.getProfileId() != null)
				return false;
		} else if (!getProfileId().equals(other.getProfileId()))
			return false;
		return true;
	}
	
	public static class BasicInfoBuilder implements Builder<BasicInfo>{
		private String profileId;
		private String firstName;
		private String lastName;
		private String email;
		private Gender gender;
		private DOB dateOfBirth;
		private AccountStatus accountStatus;
		private AccountState accountState;
		private String profileSnapLoc;
		private String coverSnapLoc;
		private RelationShipStatus status;
		private BloodGroup bloodGroup;
		private Locales locale;
		private Currencies currency;
		private TimeZones timeZone;
		
		public BasicInfoBuilder(String profileId) {
			this.profileId = profileId;
		}
		
		public BasicInfoBuilder addFirstName(String firstName){
			this.firstName = firstName;
			return this;
		}
		
		public BasicInfoBuilder addLastName(String lastName){
			this.lastName = lastName;
			return this;
		}
		
		public BasicInfoBuilder addEmail(String email){
			this.email = email;
			return this;
		}
		
		public BasicInfoBuilder addGender(Gender gender){
			this.gender = gender;
			return this;
		}
		
		public BasicInfoBuilder addStatus(String status){
			this.accountStatus = AccountStatus.getEnum(status);
			return this;
		}
		
		public BasicInfoBuilder addStatus(AccountStatus status){
			this.accountStatus = status;
			return this;
		}
		
		public BasicInfoBuilder addState(String state){
			this.accountState = AccountState.getEnum(state);
			return this;
		}
		
		public BasicInfoBuilder addState(AccountState state){
			this.accountState = state;
			return this;
		}
		
		public BasicInfoBuilder addDOB(DOB dob){
			this.dateOfBirth = dob;
			return this;
		}
		
		public BasicInfoBuilder addSnapLoc(String snapLoc){
			this.profileSnapLoc = snapLoc;
			return this;
		}
		
		public BasicInfoBuilder addCoverSnapLoc(String coverSnapLoc){
			this.coverSnapLoc = coverSnapLoc;
			return this;
		}
		
		public BasicInfoBuilder addBloodGroup(BloodGroup bloodGroup){
			this.bloodGroup = bloodGroup;
			return this;
		}
		
		public BasicInfoBuilder addRelationShipStatus(RelationShipStatus status){
			this.status = status;
			return this;
		}
		
		public BasicInfoBuilder addLocale(Locales locale){
			this.locale = locale;
			return this;
		}
		
		public BasicInfoBuilder addCurrency(Currencies currency){
			this.currency = currency;
			return this;
		}
		
		public BasicInfoBuilder addTimeZone(TimeZones timeZone){
			this.timeZone = timeZone;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public BasicInfo build() {
			return new BasicInfo(this);
		}
		
	}
	
	private BasicInfo(BasicInfoBuilder builder){
		super(builder.profileId);
		this.accountState = builder.accountState;
		this.accountStatus = builder.accountStatus;
		this.email = builder.email;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.gender = builder.gender;
		this.dateOfBirth = builder.dateOfBirth;
		this.profileSnapLoc = builder.profileSnapLoc;
		this.coverSnapLoc = builder.coverSnapLoc;
		this.bloodGroup = builder.bloodGroup;
		this.status = builder.status;
		this.currency = builder.currency;
		this.locale = builder.locale;
		this.timeZone = builder.timeZone;
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
