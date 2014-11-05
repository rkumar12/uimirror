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
package com.uimirror.location.latlong.stepdef;


import org.junit.Assert;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

import com.uimirror.location.StartApp;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * @author Jay
 */
@WebAppConfiguration
@SpringApplicationConfiguration(classes = StartApp.class)
public class LocationByLongLatStepDef {
	
	private String latitude;
	private String longitude;
	private String expanded;
	private ResponseEntity<String> entity;
//	@InjectMocks
//	private @Autowired Processor<String, DefaultLocation> locationSearchById;
//	private String locationId;
//	@Mock
//	private LocationStore persistedLocationMongoStore;
//	private RecordNotFoundException e;
	
//	@Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
	
	@Given("^I made a rest call with \"(.*?)\", \"(.*?)\" as GET paramter$")
	public void i_made_a_rest_call_with_as_GET_paramter(String latitude, String longitude) throws Throwable {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Given("^I gave \"(.*?)\" option in the GET parameter$")
	public void i_gave_option_in_the_GET_parameter(String expanded) throws Throwable {
		this.expanded = expanded;
	}

	@Given("^press submit$")
	public void press_submit() throws Throwable {
		String url = "http://localhost:8080/uim/location/lookup/lat="+ latitude+"&lon="+longitude;
		if(StringUtils.hasText(expanded))
			url += "&expanded"+expanded;
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(url, String.class);
		this.entity = entity;
	}

	@Then("^I got response as \"(.*?)\"$")
	public void i_got_response_as(String responseCode) throws Throwable {
		Assert.assertEquals(responseCode, entity.getStatusCode());
	}

	

}
