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
package com.uimirror.reach.conf;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
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
@ComponentScan(basePackages= {"com.uimirror.reach.controller"})
public class DispatcherConfig extends WebMvcConfigurerAdapter{
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("beforelogin/startapp");
		registry.addViewController("/login").setViewName("beforelogin/startapp");
		registry.addViewController("/verify").setViewName("beforelogin/verify");
		registry.addViewController("/home").setViewName("dashboard");
		configureTemplateUrl(registry);
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
		 registry.addResourceHandler("/image/**/*").addResourceLocations("classpath:/image/");
		 registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(0);
	 }
	 
	 private void configureTemplateUrl(ViewControllerRegistry registry) {
		 registry.addViewController("/template/resetpassword").setViewName("template/beforelogin/reg_lgn_agrement::resetpassword");
		 registry.addViewController("/template/register").setViewName("template/beforelogin/reg_lgn_agrement::registerForm");
		 registry.addViewController("/template/register/agreement").setViewName("template/beforelogin/reg_lgn_agrement::registerAgrement");
		 registry.addViewController("/template/notification").setViewName("template/uim-noti::notiTemp");
		 registry.addViewController("/template/changeEmail").setViewName("template/beforelogin/change_email::changeEmail");
	 }
	
	
}