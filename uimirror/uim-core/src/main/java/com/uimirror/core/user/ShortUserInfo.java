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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.core.Constants;
import com.uimirror.core.bean.Gender;

/**
 * This has Short user Info for the user such as name, email, gender
 * This has been deprecated in favor of {@link BasicInfo}
 * @author Jay
 */
@Deprecated
public class ShortUserInfo {

	private String profileId;
	private String firstName;
	private String lastName;
	private String name;
	private String email;
	private Gender gender;
	
	/**
	 * @param profileId
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param gender
	 */
	public ShortUserInfo(String profileId, String firstName, String lastName,
			String email, Gender gender) {
		super();
		this.profileId = profileId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}
	/**
	 * @param profileId
	 * @param name
	 * @param email
	 * @param gender
	 */
	public ShortUserInfo(String profileId, String name, String email, Gender gender) {
		super();
		this.profileId = profileId;
		this.name = name;
		this.email = email;
		this.gender = gender;
	}
	
	/**
	 * Creates the map by using profile id and name
	 * @return
	 */
	public Map<String, Object> getNameIdMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>(5);
		map.put(UserDBFields.ID, getProfileId());
		map.put(UserDBFields.NAME, getName());
		return map;
	}
	
	/**
	 * Initialize object state from the given map
	 * @param raw
	 * @return
	 */
	public static ShortUserInfo initFromMap(Map<String, Object> raw){
		String gender = (String)raw.get(UserDBFields.GENDER);
		Gender g = StringUtils.hasText(gender) ? Gender.getEnum(gender) : null;  
		return new ShortUserInfo((String)raw.get(UserDBFields.ID), (String)raw.get(UserDBFields.NAME), (String)raw.get(UserDBFields.EMAIL), g);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		if(StringUtils.hasText(name))
			return name;
		if(StringUtils.hasText(lastName) && StringUtils.hasText(firstName))
			return this.firstName + Constants.SINGLE_SPACE+ this.lastName;
		else if(StringUtils.hasText(firstName))
			return this.firstName;
		return this.lastName;
	}
	public String getEmail() {
		return email;
	}

	public Gender getGender() {
		return gender;
	}

	public String getProfileId() {
		return profileId;
	}

}
