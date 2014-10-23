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
package com.uimirror.account.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.account.client.bean.Client;
import com.uimirror.account.client.validator.CreateClientAccountValidator;
import com.uimirror.account.user.processor.UserRegistrationValidationService;
import com.uimirror.core.service.ValidatorService;

/**
 * Contains the bean definition for all the user and client process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfValidator {

  @Bean
  public ValidatorService<Client> createClientAccountValidator() {
    return new CreateClientAccountValidator();
  }

  @Bean
  public ValidatorService<String> userRegistrationValidationService() {
	  return new UserRegistrationValidationService();
  }

}
