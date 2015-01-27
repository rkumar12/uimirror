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
package com.uimirror.user;

import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.user.DOBDBFields.DOB_FORMAT;
import static com.uimirror.core.user.MetaInfoDBFields.LOCALE;
import static com.uimirror.core.user.UserDBFields.DATE_OF_BIRTH;
import static com.uimirror.core.user.UserDBFields.META_INFO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

import com.uimirror.core.AccountState;
import com.uimirror.core.AccountStatus;
import com.uimirror.core.meta.BloodGroup;
import com.uimirror.core.meta.Currencies;
import com.uimirror.core.meta.Locales;
import com.uimirror.core.meta.TimeZones;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.DOB;
import com.uimirror.core.user.Gender;
import com.uimirror.core.user.RelationShipStatus;
import com.uimirror.core.user.UserDBFields;
import com.uimirror.core.user.DOB.DOBBuilder;

/**
 * @author Jay
 */
public class BasicInfoTest {
	
	private BasicInfo info;
	private BasicInfo info1;
	
	private Map<String, Object> im;
	private Map<String, Object> im1;
	private Map<String, Object> meta;
	

	@Before
	public void setUp(){
		DOB dob=new DOB.DOBBuilder("1988-03-12").addFormat("MMM dd uuuu").addLocale("en_US").build();
		meta = new WeakHashMap<String, Object>();
		meta.put(LOCALE, "en_UK");
		info = new BasicInfo.BasicInfoBuilder(null).
				addEmail("jayaramimca@gmail.com").
				addFirstName("Jayaram").addGender(Gender.MALE).
				addLastName("Pradhan").addState(AccountState.NEW).addBloodGroup(BloodGroup.APOSITIVE)
				.addSnapLoc("test.jpg").addStatus(AccountStatus.ACTIVE).addTimeZone(TimeZones.DEFAULT).addLocale(Locales.ENGLISH).addCurrency(Currencies.RUPEE)
				.addCoverSnapLoc("text.jpg").addRelationShipStatus(RelationShipStatus.NONE)
				.addDOB(dob)
				.build();
		im = new WeakHashMap<String, Object>();
//		im.put(ID, getId());
		im.put(UserDBFields.FIRST_NAME, "Jayaram");
		im.put(UserDBFields.LAST_NAME, "Pradhan");
		im.put(UserDBFields.EMAIL, "jayaramimca@gmail.com");
		im.put(UserDBFields.GENDER, Gender.MALE.getGender());
		im.put(UserDBFields.TIME_ZONE, TimeZones.DEFAULT.getTimeZone().getID());
		im.put(UserDBFields.ACCOUNT_STATUS, AccountStatus.ACTIVE.getStatus());
		im.put(UserDBFields.ACCOUNT_STATE, AccountState.NEW.getState());
		im.put(UserDBFields.BLOOD_GROUP, BloodGroup.APOSITIVE.getGroup());
		im.put(UserDBFields.RELATION_SHIP_STATUS, RelationShipStatus.NONE.getStatus());
		im.put(UserDBFields.SNAP_LOC, "test.jpg");
		im.put(UserDBFields.COVER_SNAP_LOC, "text.jpg");
		im.put(UserDBFields.LOCALE, Locales.ENGLISH.getLocale());
		im.put(UserDBFields.CURRENCY, Currencies.RUPEE.getCurrency());
		im.put(UserDBFields.DATE_OF_BIRTH, dob.toMap());
	}
	
	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(BasicInfo.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

	@Test
	public void serializeTest(){
		assertThat(info.writeToMap()).isEqualTo(im);
		im.put(ID, "123");
		assertThat(info.updateId("123").writeToMap()).isEqualTo(im);
		im.remove(ID);
	}
	
	@Test
	public void deSerializeTest(){
		im.put(META_INFO, meta);
		assertThat(info.readFromMap(im)).isEqualTo(info);
		im.put(ID, "123");
		assertThat(info.readFromMap(im)).isEqualTo(info.updateId("123"));
		im.remove(ID);
	}
}
