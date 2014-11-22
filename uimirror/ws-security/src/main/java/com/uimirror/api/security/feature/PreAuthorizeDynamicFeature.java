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
package com.uimirror.api.security.feature;

import static com.uimirror.core.auth.AuthConstants.AUTHORIZATION_TOKEN;
import static com.uimirror.core.auth.AuthConstants.TOKEN_ENCRYPTION_STARTEGY;
import static com.uimirror.core.auth.SecurityConstants.BEARER;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.server.model.AnnotatedMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.uimirror.core.Priorities;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Token;
import com.uimirror.core.rest.extra.UnAuthorizedException;
import com.uimirror.sso.core.PreAuthorize;
import com.uimirror.sso.processor.AccessTokenProcessor;

/**
 * A {@link DynamicFeature} supporting the {@link PreAuthorize},
 * <p>This class has been customized to load the token details from cache and process</p>
 *
 * @author Jay
 */
public class PreAuthorizeDynamicFeature implements DynamicFeature{

	//Autowering as there was no other options to bind this
	private @Autowired AccessTokenProcessor accessTokenProcessor;
	private static final Logger LOG = LoggerFactory.getLogger(PreAuthorizeDynamicFeature.class);
	
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

		private AccessTokenProcessor accessTokenProcessor;
		private static final String DFLT_SPACE = " ";

        PreAuthorizeRequestFilter(AccessTokenProcessor accessTokenProcessor) {
            this.accessTokenProcessor = accessTokenProcessor;
        }

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
        	//update and return the authenticated token
        	AccessToken token = null;
        	try{
        		token = accessTokenProcessor.invoke(requestContext);
        	}catch(Exception e){
        		LOG.error("[SECURITY-CRITICAL]- Something went wrong while getting the token {}",e);
        	}
        	if(token == null)
        		throw new UnAuthorizedException();
        	putBackNewToken(requestContext, token);
        }
        
        /**
         * Put Back The Authorization token into the header.
         * @param requestContext request context container
         * @param accessToken token object
         */
        public void putBackNewToken(ContainerRequestContext requestContext, AccessToken accessToken){
        	Token token = accessToken.getToken();
        	String accessKey = BEARER+DFLT_SPACE+token.getToken();
        	String pharse = token.getParaphrase();
        	requestContext.getHeaders().remove(AUTHORIZATION_TOKEN);
        	requestContext.getHeaders().add(AUTHORIZATION_TOKEN, accessKey);
        	if(StringUtils.hasText(pharse))
        		requestContext.getHeaders().add(TOKEN_ENCRYPTION_STARTEGY, pharse);
        }
    }

}
