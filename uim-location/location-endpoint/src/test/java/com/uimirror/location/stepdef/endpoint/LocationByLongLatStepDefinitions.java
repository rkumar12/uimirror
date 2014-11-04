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
package com.uimirror.location.stepdef.endpoint;


import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.uimirror.core.Processor;
import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.conf.AppConfig;
import com.uimirror.location.conf.BeanIntitializer;
import com.uimirror.location.store.LocationStore;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Jay
 */
@ContextConfiguration(classes = {BeanIntitializer.class, AppConfig.class})
public class LocationByLongLatStepDefinitions {
	
	@InjectMocks
	private @Autowired Processor<String, DefaultLocation> locationSearchById;
	private String locationId;
	@Mock
	private LocationStore persistedLocationMongoStore;
	private RecordNotFoundException e;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	

}
