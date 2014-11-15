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

import com.uimirror.account.form.RegisterForm;
import com.uimirror.account.form.VerifyForm;
import com.uimirror.account.processor.CreateUserProcessor;
import com.uimirror.account.processor.DefaultAccessTokenProcessor;
import com.uimirror.account.processor.UserRegistrationProcessor;
import com.uimirror.account.processor.VerifyActivateUserAccountProcessor;
import com.uimirror.core.Processor;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.ws.api.security.AccessTokenProcessor;

/**
 * Contains the bean definition for all the authentication controller process
 * 
 * @author Jay
 */
@Configuration
public class BeanOfProcessor {

  @Bean
  public Processor<VerifyForm, String> verifyActivateUserAccountProcessor() {
    return new VerifyActivateUserAccountProcessor();
  }
  
  @Bean
  public AccessTokenProcessor accessTokenProcessor(){
	  return new DefaultAccessTokenProcessor();
  }

  @Bean
  public Processor<RegisterForm, DefaultUser> createUserProcessor(){
	  return new CreateUserProcessor();
  }

  @Bean
  public Processor<RegisterForm, String> userRegistrationProcessor(){
	  return new UserRegistrationProcessor();
  }

}
