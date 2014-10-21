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
package com.uimirror.ws.api.security.filter.feature;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.server.model.AnnotatedMethod;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Priorities;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.rest.extra.UnAuthorizedException;
import com.uimirror.ws.api.security.annotation.PreAuthorize;

/**
 * A {@link DynamicFeature} supporting the {@link PreAuthorize},
 * <p>This class has been customized to load the token details from cache and process</p>
 *
 * @author Jay
 */
public class PreAuthorizeDynamicFeature implements DynamicFeature{
	//Doing Autowering as there was no other options to bind this
	private @Autowired Processor<ContainerRequestContext, AccessToken> accessTokenProcessor;
	
	@Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext configuration) {
        AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());

        // PreAuthorize on the method take precedence
        if (am.isAnnotationPresent(PreAuthorize.class)) {
            configuration.register(new PreAuthorizeRequestFilter(accessTokenProcessor));
            return;
        }
    }
	
	@Priority(Priorities.TOKENVALIDATION) // authorization filter - should go after any authentication filters
    private static class PreAuthorizeRequestFilter implements ContainerRequestFilter {

		private Processor<ContainerRequestContext, AccessToken> accessTokenProcessor;

        PreAuthorizeRequestFilter(Processor<ContainerRequestContext, AccessToken> accessTokenProcessor) {
            this.accessTokenProcessor = accessTokenProcessor;
        }

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
        	//update and return the authenticated token
        	AccessToken token = accessTokenProcessor.invoke(requestContext);
        	if(token == null)
        		throw new UnAuthorizedException();
        }
    }

}
