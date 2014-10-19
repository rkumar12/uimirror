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
package com.uimirror.core.mail;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.uimirror.core.Constants;
import com.uimirror.core.crypto.PBEWithMD5AndDESCrypto;

/**
 * Initialize or configures the email template resolvers
 * @author Jay
 */
@Configuration
public class EmailBeanInitializr {

	@Bean
	public JavaMailSender mailSender() throws GeneralSecurityException, IOException{
		JavaMailSenderImpl jMailSenderImpl = new JavaMailSenderImpl();
		jMailSenderImpl.setHost("smtp.gmail.com");
		jMailSenderImpl.setPort(587);
		jMailSenderImpl.setProtocol("smtp");
		jMailSenderImpl.setUsername("support@xdevloperz.com");
		jMailSenderImpl.setPassword(new PBEWithMD5AndDESCrypto().decrypt("_6N7PBP2eGwGt6fcmx-0txlBVjAC7oLo"));
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.quitwait", false);
		jMailSenderImpl.setJavaMailProperties(props);
		return jMailSenderImpl;
	}

	//<!-- THYMELEAF: Template Resolver for email templates -->
	@Bean
	public ITemplateResolver emailTemplateResolver(){
		ClassLoaderTemplateResolver clTemplateResolver = new ClassLoaderTemplateResolver();
		clTemplateResolver.setPrefix("mail/");
		clTemplateResolver.setTemplateMode("HTML5");
		clTemplateResolver.setCharacterEncoding(Constants.UTF_8);
		clTemplateResolver.setOrder(1);
		clTemplateResolver.setCacheable(Boolean.TRUE);
		return clTemplateResolver;
	}
	
	//<!-- THYMELEAF: Template Engine (Spring4-specific version) -->
	@Bean
	@Autowired
	public SpringTemplateEngine templateEngine(ITemplateResolver emailTemplateResolver){
		SpringTemplateEngine sEngine = new SpringTemplateEngine();
		sEngine.setTemplateResolver(emailTemplateResolver);
		return sEngine;
	}
	
}
