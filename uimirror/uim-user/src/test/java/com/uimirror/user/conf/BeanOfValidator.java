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
package com.uimirror.user.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.core.service.ValidatorService;
import com.uimirror.user.processor.UserRegistrationValidationService;
import com.uimirror.user.store.AccountTokenStore;
import com.uimirror.user.store.DefaultUserStore;
import com.uimirror.user.store.UserBasicInfoStore;

/**
 * Contains the bean definition for all the user and client process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfValidator {

  @Bean
  public ValidatorService<String> userRegistrationValidationService(AccountTokenStore persistedAccountTokenMongoStore,
		  DefaultUserStore persistedDefaultUserMongoStore, UserBasicInfoStore persistedUserBasicInfoMongoStore) {
	  
	  UserRegistrationValidationService urvs = new UserRegistrationValidationService();
	  urvs.setPersistedAccountTokenMongoStore(persistedAccountTokenMongoStore);
	  urvs.setPersistedDefaultUserMongoStore(persistedDefaultUserMongoStore);
	  urvs.setPersistedUserBasicInfoMongoStore(persistedUserBasicInfoMongoStore);
	  return urvs;
  }

}
