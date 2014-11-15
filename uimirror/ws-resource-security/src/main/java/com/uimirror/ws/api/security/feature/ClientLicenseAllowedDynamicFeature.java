package com.uimirror.ws.api.security.feature;
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
//package com.uimirror.ws.api.security.feature;
//
//import java.io.IOException;
//
//import javax.annotation.Priority;
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
//import com.uimirror.ws.api.security.annotation.ClientLicensesAllowed;
//import com.uimirror.ws.api.security.ouath.UIMSecurityContext;
//import com.uimirror.ws.api.security.service.PrincipalService;
//import com.uimirror.ws.api.security.util.SecurityResponseUtil;
//
///**
// * A {@link DynamicFeature} supporting the {@code ClientLicensesAllowed},
// * 
// * on resource methods and sub-resource methods.
// * <p>
// * The {@link UIMSecurityContext} is utilized, using the
// * {@link UIMSecurityContext#isClientHasLicense(String)} method,
// * to ascertain if the client is in one
// * of the license declared in by a {@code ClientLicensesAllowed}. If a client is in none of
// * the declared roles then a 401 (UnAuthorized) response is returned.
// * 
// * @author Jay
// */
//public class ClientLicenseAllowedDynamicFeature implements DynamicFeature{
//
//	//No other way to bind this so binding via Autowired
//	@Autowired
//	private PrincipalService principalService;
//	
//	@Override
//    public void configure(final ResourceInfo resourceInfo, final FeatureContext configuration) {
//        AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());
//
//        // ClientLicensesAllowed on the method takes precedence
//        ClientLicensesAllowed ul = am.getAnnotation(ClientLicensesAllowed.class);
//        if (ul != null) {
//            configuration.register(new LicenseAllowedRequestFilter(ul.value(), principalService));
//            return;
//        }
//
//        // ClientLicensesAllowed on the class takes precedence 
//        ul = resourceInfo.getResourceClass().getAnnotation(ClientLicensesAllowed.class);
//        if (ul != null) {
//            configuration.register(new LicenseAllowedRequestFilter(ul.value(), principalService));
//        }
//    }
//	
//	@Priority(Priorities.CLIENT_LICENSE_AUTHORIZATION) // license Authorization filter - should go after any authentication filters
//    private static class LicenseAllowedRequestFilter implements ContainerRequestFilter {
//        private final String[] licenseAllowed;
//        private final PrincipalService principalService;
//
//        LicenseAllowedRequestFilter(String[] licenseAllowed, PrincipalService principalService) {
//            this.licenseAllowed = (licenseAllowed != null) ? licenseAllowed : new String[] {};
//            this.principalService = principalService;
//        }
//
//        @Override
//        public void filter(ContainerRequestContext requestContext) throws IOException {
//        	principalService.updateClientInSecurityConext(requestContext);
//        	UIMSecurityContext uimSecurityContext = (UIMSecurityContext)requestContext.getSecurityContext();
//        	for (String license : licenseAllowed) {
//        		if (uimSecurityContext.isClientHasLicense(license)) {
//        			return;
//        		}
//        	}
//        	//if no role matches then throw this
//        	throw new WebApplicationException(SecurityResponseUtil.buildClientNotHasLicenseResponse());
//        }
//    }
//
//}
