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

import javax.ws.rs.container.ContainerRequestContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uimirror.account.auth.token.processor.AccessTokenProcessor;
import com.uimirror.account.auth.user.processor.VerifyActivateUserAccountProcessor;
import com.uimirror.account.user.form.VerifyForm;
import com.uimirror.account.user.processor.CreateUserProcessor;
import com.uimirror.account.user.processor.UserRegistrationProcessor;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.user.DefaultUser;

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
  public Processor<ContainerRequestContext, AccessToken> accessTokenProcessor(){
	  return new AccessTokenProcessor();
  }

  @Bean
  public Processor<com.uimirror.account.user.form.RegisterForm, DefaultUser> createUserProcessor(){
	  return new CreateUserProcessor();
  }

  @Bean
  public Processor<com.uimirror.account.user.form.RegisterForm, String> userRegistrationProcessor(){
	  return new UserRegistrationProcessor();
  }

}
