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

import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Jay
 */
@RunWith(Cucumber.class)
@CucumberOptions( glue = {"com.uimirror.location.latlong.stepdef"}, features="classpath:location_by_long_lat.feature")
//TODO
@Ignore("Untill cucumber-jvm didn't come up with the release to support spring boot")
public class RunLatLongFeatureTests {

}