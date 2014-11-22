package com.uimirror.api.security.feature;
///*******************************************************************************
// * Copyright (c) 2014 Uimirror.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Uimirror license
// * which accompanies this distribution, and is available at
// * http://www.uimirror.com/legal
// *
// * Contributors:
// * Uimirror Team
// *******************************************************************************/
//package com.uimirror.api.security.feature;
//
//import java.io.IOException;
//
//import javax.annotation.Priority;
//import javax.annotation.security.DenyAll;
//import javax.annotation.security.PermitAll;
//import javax.annotation.security.RolesAllowed;
//import javax.ws.rs.ForbiddenException;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.DynamicFeature;
//import javax.ws.rs.container.ResourceInfo;
//import javax.ws.rs.core.FeatureContext;
//
//import org.glassfish.jersey.server.model.AnnotatedMethod;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.uimirror.core.Priorities;
//import com.uimirror.api.security.ouath.UIMSecurityContext;
//import com.uimirror.api.security.service.PrincipalService;
//import com.uimirror.api.security.util.SecurityResponseUtil;
//
///**
// * A {@link DynamicFeature} supporting the {@code javax.annotation.security.RolesAllowed},
// * {@code javax.annotation.security.PermitAll} and {@code javax.annotation.security.DenyAll}
// * on resource methods and sub-resource methods.
// * <p>
// * The {@link javax.ws.rs.core.SecurityContext} is utilized, using the
// * {@link javax.ws.rs.core.SecurityContext#isUserInRole(String) } method,
// * to ascertain if the user is in one
// * of the roles declared in by a {@code &#64;RolesAllowed}. If a user is in none of
// * the declared roles then a 403 (Forbidden) response is returned.
// * <p>
// * If the {@code &#64;DenyAll} annotation is declared then a 403 (Forbidden) response
// * is returned.
// * <p>
// * If the {@code &#64;PermitAll} annotation is declared and is not overridden then
// * this filter will not be applied.
// * <p>This class has been customized to load the token details from cache and process</p>
// *
// * @author Jay
// */
//public class RolesAllowedDynamicFeature implements DynamicFeature{
//	//Doing Autowering as there was no other options to bind this
//	@Autowired
//	private PrincipalService principalService;
//	@Override
//    public void configure(final ResourceInfo resourceInfo, final FeatureContext configuration) {
//        AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());
//
//        // DenyAll on the method take precedence over RolesAllowed and PermitAll
//        if (am.isAnnotationPresent(DenyAll.class)) {
//            configuration.register(new RolesAllowedRequestFilter(principalService));
//            return;
//        }
//
//        // RolesAllowed on the method takes precedence over PermitAll
//        RolesAllowed ra = am.getAnnotation(RolesAllowed.class);
//        if (ra != null) {
//            configuration.register(new RolesAllowedRequestFilter(ra.value(), principalService));
//            return;
//        }
//
//        // PermitAll takes precedence over RolesAllowed on the class
//        if (am.isAnnotationPresent(PermitAll.class)) {
//            // Do nothing.
//            return;
//        }
//
//        // DenyAll can't be attached to classes
//
//        // RolesAllowed on the class takes precedence over PermitAll
//        ra = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
//        if (ra != null) {
//            configuration.register(new RolesAllowedRequestFilter(ra.value(), principalService));
//        }
//    }
//	
//	@Priority(Priorities.AUTHORIZATION) // authorization filter - should go after any authentication filters
//    private static class RolesAllowedRequestFilter implements ContainerRequestFilter {
//        private final boolean denyAll;
//        private final String[] rolesAllowed;
//        private final PrincipalService principalService;
//
//        RolesAllowedRequestFilter(PrincipalService principalService) {
//            this.denyAll = true;
//            this.rolesAllowed = null;
//            this.principalService = principalService;
//        }
//
//        RolesAllowedRequestFilter(String[] rolesAllowed, PrincipalService principalService) {
//            this.denyAll = false;
//            this.rolesAllowed = (rolesAllowed != null) ? rolesAllowed : new String[] {};
//            this.principalService = principalService;
//        }
//
//        @Override
//        public void filter(ContainerRequestContext requestContext) throws IOException {
//        	if(denyAll){
//        		throw new ForbiddenException();
//        	}
//        	principalService.updateUserInSecurityConext(requestContext);
//        	UIMSecurityContext uimSecurityContext = (UIMSecurityContext)requestContext.getSecurityContext();
//        	for (String role : rolesAllowed) {
//        		if (uimSecurityContext.isUserInRole(role)) {
//        			return;
//        		}
//        	}
//        	//if no role matches then throw this
//        	throw new WebApplicationException(SecurityResponseUtil.buildUserNotInRoleResponse());
//        }
//    }
//
//}
