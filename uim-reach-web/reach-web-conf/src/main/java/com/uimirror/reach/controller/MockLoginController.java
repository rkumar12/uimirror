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

import com.uimirror.core.user.DefaultUser;
import com.uimirror.reach.web.core.user.MockUser;

/**
 * @author Jay
 */
@RestController
@RequestMapping("/")
public class MockLoginController {

	protected static final String USER = "user";
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value="/login1")
	public @ResponseBody String login(@RequestBody final MultiValueMap<String, String > formVars) {
		try{
			
			String  id = getProfileId(formVars);
			if(id == null){
				return "{\"error\":\"Invalid Login\"}";
			}
			return "{\"token\":\""+id+"\"}";
		}catch(IllegalArgumentException e){
			return "{\"error\":\"email exists\"}";
		}
	}
	
	private String getProfileId(MultiValueMap<String, String > form){
		String email = form.getFirst("uid");
		return MockUser.getProfileIdByEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value="/login/cookie")
	public @ResponseBody String loginViaCookie(@RequestBody final MultiValueMap<String, String > formVars) {
		try{
			String token = formVars.getFirst("token");
			DefaultUser user = MockUser.getUser(token);
			if(user == null){
				return "{\"error\":\"Invalid Login\"}";
			}
			return "{\"token\":\"+"+user.getUserInfo().getProfileId()+"+\"}";
		}catch(IllegalArgumentException e){
			return "{\"error\":\"email exists\"}";
		}
	}

}
