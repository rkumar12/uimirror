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
package com.uimirror.account.conf;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Configures the servlet container for the context uri path
 * @author Jayaram
 */
@Configuration
public class WebAppInitializer extends SpringBootServletInitializer {

	@Override  
    public void onStartup(ServletContext servletContext) throws ServletException {  
        super.onStartup(servletContext);  
    } 

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebAppInitializer.class);
    }

    @Bean
    public ServletContainer dispatcherServlet() {
        return new ServletContainer();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
        Map<String,String> params = new HashMap<String,String>();
        params.put("javax.ws.rs.Application","com.uimirror.account.conf.JerssyApplicationInitializer");
        //params.put("jersey.config.server.provider.classnames","com.uimirror.api.filter.SecurityContextFilter,com.uimirror.challenge.config.filter.UimCORSFilter, com.uimirror.api.filter.PoweredByResponseFilter");
        registration.setInitParameters(params);
        registration.addUrlMappings("/*");
        return registration;
    }
    
}