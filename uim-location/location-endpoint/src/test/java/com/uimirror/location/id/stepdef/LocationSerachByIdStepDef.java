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
package com.uimirror.location.id.stepdef;


import org.junit.Assert;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

import com.uimirror.location.StartApp;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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
	@Given("^I made a rest call with \"(.*?)\" as GET parameter$")
	public void i_made_a_rest_call_with_as_GET_parameter(String locationId) throws Throwable {
		this.locationId = locationId;
	}

	@When("^I hit the url$")
	public void i_hit_the_url() throws Throwable {
		String url = "http://localhost:8080/uim/location/";
		if(StringUtils.hasText(expanded))
			url += "?id="+locationId+"&expanded="+expanded;
		else
			url += "?id="+locationId;
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity( url, String.class);
		this.entity = entity;
	}
	
	@Given("^I gave \"(.*?)\" option in the GET parameter$")
	public void i_give_option_in_the_GET_parameter(String expanded) throws Throwable {
		this.expanded = expanded;
	}

	@Then("^I got response as \"(.*?)\"$")
	public void i_got_response_as(String responseCode) throws Throwable {
		Assert.assertEquals(responseCode, entity.getStatusCode());
	}

}
