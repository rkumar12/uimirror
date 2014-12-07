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
import static com.uimirror.reach.controller.WebConstants.DFLT_TOKEN_COOKIE;
import static com.uimirror.reach.controller.WebConstants.TOKEN_COOKIE;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jay
 */
@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping(method = RequestMethod.GET)
	public String home(@CookieValue(value = TOKEN_COOKIE, defaultValue = DFLT_TOKEN_COOKIE) String token, ModelMap model) {
		//Originally a filter should process for the token cooie else redirect to the login page
		if(DFLT_TOKEN_COOKIE.equals(token))
			return "redirect: login";
		//First resolve the profile id;
		String profileId = resolveProfileId(token);
		
		model.addAttribute("message", "Hello Spring MVC Framework!");
		return "app";
	}
	
	/**
	 * Resolves the profile id from the given token.
	 * @param token cookie which was issued to the user.
	 * @return profile id of owner of the token
	 */
	private String resolveProfileId(String token){
		//TODO Dummy Implementation delete this latter
		String profileId;
		switch(token){
		//Basic User /new user
		case "1":
			profileId = "1";
			break;
		case "2":
			profileId = "2";
			break;
		case "3":
			profileId = "3";
			break;
		case "4":
			profileId = "4";
			break;
		case "5":
			profileId = "5";
			break;
		case "6":
			profileId = "6";
			break;
		default:
			profileId = "0";
		}
		return profileId;
	}
}
