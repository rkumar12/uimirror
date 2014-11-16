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
package com.uimirror.ws.account.user.stepdef;


import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import com.uimirror.account.StartApp;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * @author Jay
 */

@WebAppConfiguration
@SpringApplicationConfiguration(classes = StartApp.class)
public class LocationSerachByIdStepDef {
	private String locationId;
	private String expanded;
	private ResponseEntity<String> entity;
	@Before
	public void init(){
	}

	@Given("^I have an apikey \"(.*?)\"$")
	public void i_have_an_apikey(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I enter \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\"$")
	public void i_enter(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I click on accept policy$")
	public void i_click_on_accept_policy() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I click on signup$")
	public void i_click_on_signup() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I got response code \"(.*?)\"$")
	public void i_got_response_code(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
}
