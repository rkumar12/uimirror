/*******************************************************************************
 * Copyright (c) 2012 Uimirror.com.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Uimirror License v1.0
 * which accompanies this distribution, and is available at
 * http://www.legal.uimirror.com/code/policy
 *
 * Contributors:
 * Uimirror. - initial API and implementation
 *******************************************************************************/
package com.uimirror.oauth2.conf;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.uimirror.core.Constants;

/**
 * <p>Configures the servlet container for the context uri path
 * @author Jayaram
 */
@Configuration
@EnableWebMvc
@Import({ThymeleafConfig.class})
public class DispatcherConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/greeting").setViewName("greeting");
	}
	
	@Bean
	 public LocaleChangeInterceptor localeChangeInterceptor(){
	     LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
	     localeChangeInterceptor.setParamName("lang");
	     return localeChangeInterceptor;
	 }
	 
	 @Bean(name = "localeResolver")
	 public LocaleResolver sessionLocaleResolver(){
		 CookieLocaleResolver localeResolver=new CookieLocaleResolver();
		 localeResolver.setDefaultLocale(new Locale("en"));
		 localeResolver.setCookieName("uim_locale");
		 localeResolver.setCookieMaxAge(36000);
	     return localeResolver;
	 }  
	  
	 public void addInterceptors(InterceptorRegistry registry) {
	     registry.addInterceptor(localeChangeInterceptor());
	 }
	  
	 @Bean
	 public MessageSource messageSource() {
		 ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		 messageSource.setBasename("classpath:locale/messages");
		 messageSource.setDefaultEncoding(Constants.UTF_8);
		 return messageSource;
	 }
	 
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/static/**/*").addResourceLocations("classpath:/assets/");
	        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(0);
	    }
	
	
	
}