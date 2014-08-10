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
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.server.model.AnnotatedMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.exception.AccessTokenException;
import com.uimirror.ws.api.security.ouath.UIMPrincipal;
import com.uimirror.ws.api.security.ouath.UIMSecurityContext;
import com.uimirror.ws.api.security.ouath.UIMirrorSecurity;
import com.uimirror.ws.api.security.service.AccessTokenService;

/**
 * A {@link DynamicFeature} supporting the {@code javax.annotation.security.RolesAllowed},
 * {@code javax.annotation.security.PermitAll} and {@code javax.annotation.security.DenyAll}
 * on resource methods and sub-resource methods.
 * <p>
 * The {@link javax.ws.rs.core.SecurityContext} is utilized, using the
 * {@link javax.ws.rs.core.SecurityContext#isUserInRole(String) } method,
 * to ascertain if the user is in one
 * of the roles declared in by a {@code &#64;RolesAllowed}. If a user is in none of
 * the declared roles then a 403 (Forbidden) response is returned.
 * <p>
 * If the {@code &#64;DenyAll} annotation is declared then a 403 (Forbidden) response
 * is returned.
 * <p>
 * If the {@code &#64;PermitAll} annotation is declared and is not overridden then
 * this filter will not be applied.
 * <p>This class has been customized to load the token details from cache and process</p>
 *
 * @author Jay
 */
public class RolesAllowedDynamicFeature implements DynamicFeature{
	//Doing Autowering as there was no other options to replicate this
	@Autowired
	private AccessTokenService accessTokenService;
	@Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext configuration) {
        AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());

        // DenyAll on the method take precedence over RolesAllowed and PermitAll
        if (am.isAnnotationPresent(DenyAll.class)) {
            configuration.register(new RolesAllowedRequestFilter(accessTokenService));
            return;
        }

        // RolesAllowed on the method takes precedence over PermitAll
        RolesAllowed ra = am.getAnnotation(RolesAllowed.class);
        if (ra != null) {
            configuration.register(new RolesAllowedRequestFilter(ra.value(), accessTokenService));
            return;
        }

        // PermitAll takes precedence over RolesAllowed on the class
        if (am.isAnnotationPresent(PermitAll.class)) {
            // Do nothing.
            return;
        }

        // DenyAll can't be attached to classes

        // RolesAllowed on the class takes precedence over PermitAll
        ra = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
        if (ra != null) {
            configuration.register(new RolesAllowedRequestFilter(ra.value(), accessTokenService));
        }
    }
	
	@Priority(Priorities.AUTHORIZATION) // authorization filter - should go after any authentication filters
    private static class RolesAllowedRequestFilter implements ContainerRequestFilter {
        private final boolean denyAll;
        private final String[] rolesAllowed;
        private final AccessTokenService accessTokenService;
        protected static final Logger LOG = LoggerFactory.getLogger(RolesAllowedRequestFilter.class);

        RolesAllowedRequestFilter(AccessTokenService accessTokenService) {
            this.denyAll = true;
            this.rolesAllowed = null;
            this.accessTokenService = accessTokenService;
        }

        RolesAllowedRequestFilter(String[] rolesAllowed, AccessTokenService accessTokenService) {
            this.denyAll = false;
            this.rolesAllowed = (rolesAllowed != null) ? rolesAllowed : new String[] {};
            this.accessTokenService = accessTokenService;
        }

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
        	if(denyAll){
        		throw new ForbiddenException();
        	}
        	//Check if the security context is a pre-authenticated
        	Principal principal = requestContext.getSecurityContext().getUserPrincipal();
        	UIMSecurityContext uimSecurityContext = null;
        	if(principal instanceof UIMPrincipal){
        		//User is already populated so no need to do population
        		LOG.info("Wrong Check");
        	}else{
        		try{
        			AccessToken token = accessTokenService.getAccessTokenByTokenId(principal.getName());
        			uimSecurityContext = new UIMirrorSecurity(principal, token);
        			requestContext.setSecurityContext(uimSecurityContext);
        			
        		}catch(AccessTokenException ae){
        			LOG.error("[SECURITY-ERROR]- No Tokean Found!!!!");
        			ResponseBuilder builder = null;
        	        Map<String, String> res = new LinkedHashMap<String, String>(2);
        	        res.put("error", "invalid_token");
        	        builder = Response.status(Response.Status.BAD_REQUEST).entity(new Gson().toJson(res));
        	        throw new WebApplicationException(builder.build());
        			//throw new ForbiddenException();
        		}
        	}
        	for (String role : rolesAllowed) {
        		if (uimSecurityContext.isUserInRole(role)) {
        			return;
        		}
        	}
        }
    }

}
