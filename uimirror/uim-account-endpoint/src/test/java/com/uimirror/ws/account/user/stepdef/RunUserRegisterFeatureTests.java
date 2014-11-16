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

import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Jay
 */
@RunWith(Cucumber.class)
@CucumberOptions( glue = {"com.uimirror.ws.account.user.stepdef"}, features="classpath:sign_up_user.feature")
@Ignore("Update this once cucumber has spring boot support")
//TODO
public class RunUserRegisterFeatureTests {

}
