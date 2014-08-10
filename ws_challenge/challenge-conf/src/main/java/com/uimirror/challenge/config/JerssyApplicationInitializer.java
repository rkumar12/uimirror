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
package com.uimirror.challenge.config;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.UriConnegFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.uimirror.challenge.config.filter.PoweredByResponseFilter;
import com.uimirror.ws.api.security.filter.SecurityContextFilter;
import com.uimirror.ws.api.security.filter.feature.RolesAllowedDynamicFeature;

public class JerssyApplicationInitializer extends ResourceConfig{
	protected static final Logger LOG = LoggerFactory.getLogger(JerssyApplicationInitializer.class);
	
	@Value("${application.id:challenge}")
	private String appName;
	
	public JerssyApplicationInitializer(){
		// Register resources and providers using package-scanning.
		packages(true, "com.uimirror.challenge.controller");
		packages(true, "com.uimirror.challenge.config");
		// Register my custom provider - not needed if it's in my.package.
        register(SecurityContextFilter.class);
        register(PoweredByResponseFilter.class);
        register(RolesAllowedDynamicFeature.class);
        register(UriConnegFilter.class);
        property(ServerProperties.LANGUAGE_MAPPINGS, "english : en");
        property(ServerProperties.APPLICATION_NAME, appName);
		// Register an instance of LoggingFilter.
        register(new LoggingFilter());
 
        // Enable Tracing support.
        property(ServerProperties.TRACING, "ALL");
	}
	
}
