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
package com.uimirror.reach.web.core.user;

import java.util.HashMap;
import java.util.Map;

import com.uimirror.core.bean.Gender;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.DefaultUser;

/**
 * @author Jay
 */
public class MockUser {

	private static final Map<String, DefaultUser> DATA;
	private static BasicInfo info;
	private static BasicInfo info1;
	private static BasicInfo info2;
	private static BasicInfo info3;
	private static BasicInfo info4;
	
	private static DefaultUser u1;
	private static DefaultUser u2;
	private static DefaultUser u3;
	private static DefaultUser u4;
	private static DefaultUser u5;
	
	static{
		DATA = new HashMap<String, DefaultUser>();
		intializeUser();
		DATA.put(u1.getProfileId(), u1);
		DATA.put(u2.getProfileId(), u2);
		DATA.put(u3.getProfileId(), u3);
		DATA.put(u4.getProfileId(), u4);
		DATA.put(u5.getProfileId(), u5);
	}
	
	
	private static void intializeUser(){
		intitalizeBasicInfo();
		u1 = new DefaultUser.DefaultUserBuilder("1").addBasicInfo(info).build();
		u2 = new DefaultUser.DefaultUserBuilder("2").addBasicInfo(info1).build();
		u3 = new DefaultUser.DefaultUserBuilder("3").addBasicInfo(info2).build();
		u4 = new DefaultUser.DefaultUserBuilder("4").addBasicInfo(info3).build();
		u5 = new DefaultUser.DefaultUserBuilder("5").addBasicInfo(info4).build();
	}
	
	private static void intitalizeBasicInfo(){
		info = new BasicInfo.BasicInfoBuilder("1").
				addEmail("jayaramimca@gmail.com").addFirstName("Jayaram").
				addGender(Gender.MALE).addLastName("Pradhan").addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).build();
		info1 = new BasicInfo.BasicInfoBuilder("2").
				addEmail("testtest@gmail.com").addFirstName("Test").
				addGender(Gender.FEMALE).addLastName("Pradhan").addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).build();
		info2 = new BasicInfo.BasicInfoBuilder("3").
				addEmail("testtest@gmail.com").addFirstName("Test").
				addGender(Gender.FEMALE).addLastName("Pradhan").addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).build();
		info3 = new BasicInfo.BasicInfoBuilder("4").
				addEmail("testtest@gmail.com").addFirstName("Test").
				addGender(Gender.MALE).addLastName("Pradhan").addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).build();
		info4 = new BasicInfo.BasicInfoBuilder("5").
				addEmail("testtest@gmail.com").addFirstName("Test").
				addGender(Gender.MALE).addLastName("Pradhan").addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).build();
		
	}
	
	public static DefaultUser getUser(String profileId){
		return DATA.get(profileId);
	}

}
