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
package com.uimirror.reach.controller;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.reach.web.core.user.MockUser;

/**
 * @author Jay
 */
@RestController
@RequestMapping("/user")
public class UserDummyResgistrationController {

	protected static final String USER = "user";
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public @ResponseBody String register(@RequestBody final MultiValueMap<String, String > formVars) {
		try{
			
			DefaultUser user = transform(formVars);
			MockUser.createUser(user);
			return "{\"token\":\"+"+user.getProfileId()+"+\"}";
		}catch(IllegalArgumentException e){
			return "{\"error\":\"email exists\"}";
		}
		
	}
	
	private DefaultUser transform(MultiValueMap<String, String > form){
		String firstName = form.getFirst("first_name");
		String lastName = form.getFirst("last_name");
		String email = form.getFirst("email");
		String dob = form.getFirst("dob");
		String gender = form.getFirst("gender");
		if(MockUser.isEmailExists(email)){
			throw new IllegalArgumentException();
		}
		BasicInfo info = new BasicInfo.BasicInfoBuilder(MockUser.getNextId()).
				addEmail(email).addFirstName(firstName).addLastName(lastName).addGender(gender).
				addState(AccountState.ENABLED).addStatus(AccountStatus.ACTIVE).build();
		return new DefaultUser.DefaultUserBuilder(MockUser.getNextId()).addBasicInfo(info).build();
	}
	
}
