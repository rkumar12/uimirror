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

import com.uimirror.core.user.DefaultUser;
import com.uimirror.reach.web.core.ReachHomeViewSettings;
import com.uimirror.reach.web.core.user.MockUser;

/**
 * @author Jay
 */
@Controller
@RequestMapping("/")
public class HomeController {

	protected static final String USER = "user";
	@RequestMapping(method = RequestMethod.GET)
	public String home(@CookieValue(value = TOKEN_COOKIE, defaultValue = DFLT_TOKEN_COOKIE) String token, ModelMap model) {
		//Originally a filter should process for the token cooie else redirect to the login page
		if(DFLT_TOKEN_COOKIE.equals(token))
			return "redirect: login";
		//First resolve the profile id;
		String profileId = token;
		DefaultUser user = MockUser.getUser(profileId);
		ReachHomeViewSettings setings = ReachHomeViewSettings.getSettings(profileId);
		model.addAttribute(ReachHomeViewSettings.NAME , setings);
		model.addAttribute(USER , user.getUserInfo());
		return "app";
	}
	
}
