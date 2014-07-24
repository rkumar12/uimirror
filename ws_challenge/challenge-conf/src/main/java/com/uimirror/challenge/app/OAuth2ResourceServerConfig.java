/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uimirror.challenge.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.vote.ScopeVoter;

/**
 * @author Rob Winch
 * 
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter{
	//TODO write logic for token store
	@Value("${application.id:challenge}")
	private String RESOURCE_ID;

	@Bean
	public OAuth2AuthenticationEntryPoint ouath2EntryPoint(){
		OAuth2AuthenticationEntryPoint entery = new OAuth2AuthenticationEntryPoint();
		entery.setRealmName(RESOURCE_ID);
		return entery;
	}
	
	@Bean
	public OAuth2AccessDeniedHandler ouath2AccessDeniedHandler(){
		return new OAuth2AccessDeniedHandler();
	}
	
	@Bean
	public UnanimousBased accessDecissionManager(){
		@SuppressWarnings("rawtypes")
		List<AccessDecisionVoter> voters = new ArrayList<AccessDecisionVoter>(3);
		voters.add(new ScopeVoter());
		voters.add(new RoleVoter());
		voters.add(new AuthenticatedVoter());
		return new UnanimousBased(voters);
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
		resources.tokenServices(null);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
		.and()
		.exceptionHandling().authenticationEntryPoint(ouath2EntryPoint())
		.accessDeniedHandler(ouath2AccessDeniedHandler())
		.and()
		.requestMatchers().antMatchers("/photos/**", "/oauth/users/**", "/oauth/clients/**","/me")
		.and()
		.anonymous().disable()
		.authorizeRequests()
		.antMatchers("/me").access("#oauth2.hasScope('read')")
		.antMatchers("/photos").access("#oauth2.hasScope('read')")
		.antMatchers("/photos/trusted/**").access("#oauth2.hasScope('trust')")
		.antMatchers("/photos/user/**").access("#oauth2.hasScope('trust')")
		.antMatchers("/photos/**").access("#oauth2.hasScope('read')")
		.regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
		.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
		.regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
		.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
		.regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
		.access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')")
		.accessDecisionManager(accessDecissionManager());
		// @formatter:on
	}

}
