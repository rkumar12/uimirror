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
import static com.uimirror.core.user.UserAccountLogDBFields.CREATED_ON;
import static com.uimirror.core.user.UserAuthDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserAuthDBFields.ACCOUNT_STATUS;
import static com.uimirror.core.user.UserAuthDBFields.PASSWORD;
import static com.uimirror.core.user.UserAuthDBFields.SCREEN_PASSWORD;
import static com.uimirror.core.user.UserAuthDBFields.USER_ID;
import static com.uimirror.core.user.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.user.UserDBFields.EMAIL;
import static com.uimirror.core.user.UserDBFields.FIRST_NAME;
import static com.uimirror.core.user.UserDBFields.GENDER;
import static com.uimirror.core.user.UserDBFields.LAST_NAME;
import static com.uimirror.core.user.UserDBFields.META_INFO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.DOB;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.util.DateTimeUtil;

/**
 * @author Jay
 */
public class DefaultUserTest {
	
	private BasicInfo info;
	private BasicInfo info1;
	
	private Map<String, Object> infm;
	private Map<String, Object> infm1;
	
	private Credentials c1;
	private Credentials c2;
	
	private Map<String, Object> cm1;
	private Map<String, Object> cm2;
	
	private MetaInfo dinfo = new MetaInfo.MetaBuilder("America/Los_Angeles").addCountryCode("US").addLang("en").build();
	private DOB dob = new DOB.DOBBuilder("1988-03-18").build();
	private BasicDetails dt = new BasicDetails.BasicDetailsBuilder(null).updateDOB(dob).updateMetaInfo(dinfo).build();
	private Map<String, Object> op = new WeakHashMap<String, Object>();
	
	private AccountLogs logs;
	private AccountLogs logsWithProfileId;
	private Map<String, Object> res;
	private Map<String, Object> resWithProfileId;
	
	private DefaultUser u1;
	private DefaultUser u2;
	
	private Map<String, Object> um1;
	private Map<String, Object> um2;

	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(DefaultUser.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
	
	@Test
	public void serializeTest(){
		assertThat(u1.writeToMap()).isEqualTo(um1);
		assertThat(u2.writeToMap()).isNotEqualTo(um1);
		assertThat(u2.writeToMap()).isEqualTo(um2);
	}
	
	@Test
	public void deserailizeTest(){
		assertThat(u1.readFromMap(um1).toString()).isEqualTo(u1.toString());
//		assertThat(u2.readFromMap(um2).toString()).isEqualTo(u2.toString());
//		assertThat(u2.writeToMap()).isEqualTo(um2);
	}
	
	@Before
	public void setUp(){
		initalizeDetails();
		intializeBasicInfo();
		intializeCredentials();
		intializeLogs();
		u1 = new DefaultUser.DefaultUserBuilder(null).addBasicInfo(info).addCredentials(c1).addDetails(dt).addLogs(logs).build();
		u2 = new DefaultUser.DefaultUserBuilder(null).addBasicInfo(info1).addCredentials(c2).addDetails(dt).addLogs(logsWithProfileId).build();
		um1 = new WeakHashMap<String, Object>();
		um1.putAll(infm);
		um1.putAll(cm1);
		um1.putAll(op);
		um1.putAll(res);
		um2 = new WeakHashMap<String, Object>();
		um2.putAll(infm1);
		um2.putAll(cm2);
		um2.putAll(op);
		um2.putAll(resWithProfileId);
	}
	
	private void intializeBasicInfo(){
		info = new BasicInfo.BasicInfoBuilder(null).
				addEmail("jayaramimca@gmail.com").
				addFirstName("Jayaram").addGender(Gender.MALE).
				addLastName("Pradhan").addState(AccountState.NEW).addStatus(AccountStatus.ACTIVE).build();
		info1 = new BasicInfo.BasicInfoBuilder(null).
				addEmail("jayaramimca@gmail.com").
				addFirstName("Jayaram").addGender(Gender.MALE).
				addLastName("Pradhan").addState(AccountState.NEW).addStatus(AccountStatus.ACTIVE).build();
		infm = new WeakHashMap<String, Object>();
//		im.put(ID, getId());
		infm.put(FIRST_NAME, "Jayaram");
		infm.put(LAST_NAME, "Pradhan");
		infm.put(EMAIL, "jayaramimca@gmail.com");
		infm.put(GENDER, Gender.MALE.getGender());
		infm.put(ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		infm.put(ACCOUNT_STATE, AccountState.NEW.getState());
		infm1 = new WeakHashMap<String, Object>();
//		im.put(ID, getId());
		infm1.put(FIRST_NAME, "Jayaram");
		infm1.put(LAST_NAME, "Pradhan");
		infm1.put(EMAIL, "jayaramimca@gmail.com");
		infm1.put(GENDER, Gender.MALE.getGender());
		infm1.put(UserDBFields.ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		infm1.put(UserDBFields.ACCOUNT_STATE, AccountState.NEW.getState());
	}
	
	private void intializeCredentials(){
		List<String> username = new ArrayList<String>();
		username.add("1234");
		username.add("456");
		c1  = new Credentials.CredentialsBuilder("123").
				addPassword("123").addScreenPassword("4520").
				addState(AccountState.NEW).addStatus(AccountStatus.ACTIVE).
				addUserNames(username).build();
		c2 = new Credentials.CredentialsBuilder("123").
				addPassword("123").addScreenPassword("4520").
				addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).
				addUserNames(username).build();
		cm1 = new WeakHashMap<String, Object>();
		cm1.put(ID, c1.getProfileId());
		cm1.put(USER_ID, c1.getUserId());
		cm1.put(PASSWORD, c1.getPassword());
		cm1.put(SCREEN_PASSWORD, c1.getScreenPassword());
		cm1.put(ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		cm1.put(ACCOUNT_STATE, AccountState.NEW.getState());
		cm2 = new WeakHashMap<String, Object>();
		cm2.put(ID, c2.getProfileId());
		cm2.put(USER_ID, c2.getUserId());
		cm2.put(PASSWORD, c2.getPassword());
		cm2.put(SCREEN_PASSWORD, c2.getScreenPassword());
		cm2.put(ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		cm2.put(ACCOUNT_STATE, AccountState.ENABLED.getState());
	}
	
	private void initalizeDetails(){
		op.put(DATE_OF_BIRTH, dob.toMap());
		op.put(META_INFO, dinfo.toMap());
	}
	
	private void intializeLogs(){
		logs = new AccountLogs.LogBuilder(null).updatedCreatedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).build();
		logsWithProfileId = new AccountLogs.LogBuilder("123").updatedCreatedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).build();
		res = new WeakHashMap<String, Object>();
		res.put(CREATED_ON, logs.getCreatedOn());
		resWithProfileId = new WeakHashMap<String, Object>();
		resWithProfileId.put(CREATED_ON, logsWithProfileId.getCreatedOn());
		resWithProfileId.put(ID, logsWithProfileId.getProfileId());
	}

}
