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
package com.uimirror.account.auth.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.uimirror.core.mail.EmailBeanInitializr;
import com.uimirror.sso.conf.SSOBeanInitializer;

/**
 * Initialize or configures the service bean getting used for this application
 * @author Jay
 */
@Configuration
@Import({BeanOfExceptionIntitializer.class, BeanOfAuthProcessor.class
	, BeanOfAuthManagers.class, BeanOfAuthProviders.class
	, BeanOfTransformers.class, EmailBeanInitializr.class
	, BeanOfBackGroundProcessor.class, BeanOfSchedulers.class, SSOBeanInitializer.class})
public class AuthBeanIntitializer {

}
