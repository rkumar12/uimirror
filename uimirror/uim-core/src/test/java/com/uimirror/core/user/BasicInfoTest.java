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
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATE;
import static com.uimirror.core.user.UserDBFields.ACCOUNT_STATUS;
import static com.uimirror.core.user.UserDBFields.EMAIL;
import static com.uimirror.core.user.UserDBFields.FIRST_NAME;
import static com.uimirror.core.user.UserDBFields.GENDER;
import static com.uimirror.core.user.UserDBFields.LAST_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jay
 */
public class BasicInfoTest {
	
	private BasicInfo info;
	private BasicInfo info1;
	
	private Map<String, Object> im;
	private Map<String, Object> im1;
	

	@Before
	public void setUp(){
		info = new BasicInfo.BasicInfoBuilder(null).
				addEmail("jayaramimca@gmail.com").
				addFirstName("Jayaram").addGender(Gender.MALE).
				addLastName("Pradhan").addState(AccountState.NEW).addStatus(AccountStatus.ACTIVE).build();
		info1 = new BasicInfo.BasicInfoBuilder(null).
				addEmail("jayaramimca@gmail.com").
				addFirstName("Jayaram").addGender(Gender.MALE).
				addLastName("Pradhan").addState(AccountState.NEW).addStatus(AccountStatus.ACTIVE).build();
		im = new WeakHashMap<String, Object>();
//		im.put(ID, getId());
		im.put(FIRST_NAME, "Jayaram");
		im.put(LAST_NAME, "Pradhan");
		im.put(EMAIL, "jayaramimca@gmail.com");
		im.put(GENDER, Gender.MALE.getGender());
		im.put(ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		im.put(ACCOUNT_STATE, AccountState.NEW.getState());
		im1 = new WeakHashMap<String, Object>();
//		im.put(ID, getId());
		im1.put(FIRST_NAME, "Jayaram");
		im1.put(LAST_NAME, "Pradhan");
		im1.put(EMAIL, "jayaramimca@gmail.com");
		im1.put(GENDER, Gender.MALE.getGender());
		im1.put(ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		im1.put(ACCOUNT_STATE, AccountState.NEW.getState());
	}
	
	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(BasicInfo.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

	@Test
	public void serializeTest(){
		assertThat(info.writeToMap()).isEqualTo(im);
		assertThat(info1.writeToMap()).isEqualTo(im1);
		im.put(ID, "123");
		assertThat(info1.updateId("123").writeToMap()).isEqualTo(im);
		im.remove(ID);
	}
	
	@Test
	public void deSerializeTest(){
		assertThat(info.readFromMap(im)).isEqualTo(info);
		assertThat(info.readFromMap(im1)).isEqualTo(info1);
		im.put(ID, "123");
		assertThat(info1.readFromMap(im)).isEqualTo(info.updateId("123"));
		im.remove(ID);
	}
}
