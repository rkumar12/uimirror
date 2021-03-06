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
package com.uimirror.account.conf.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * All Configuration details that required for the user account process
 * @author Jay
 */
@Configuration
@Import({BeanOfUserStore.class, BeanOfUserValidator.class
	, BeanOfUserBackGroundProcessor.class, BeanOfUserSchedulers.class})
public class UserBeanInitializer {

}
