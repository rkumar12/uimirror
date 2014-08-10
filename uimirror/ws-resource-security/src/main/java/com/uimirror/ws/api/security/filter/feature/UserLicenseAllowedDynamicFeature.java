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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.server.model.AnnotatedMethod;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.ws.api.security.Priorities;
import com.uimirror.ws.api.security.annotation.UserLicensesAllowed;
import com.uimirror.ws.api.security.ouath.UIMSecurityContext;
import com.uimirror.ws.api.security.service.PrincipalService;
import com.uimirror.ws.api.security.util.SecurityResponseUtil;

/**
 * A {@link DynamicFeature} supporting the {@code UserLicensesAllowed},
 * 
 * on resource methods and sub-resource methods.
 * <p>
 * The {@link UIMSecurityContext} is utilized, using the
 * {@link UIMSecurityContext#isUserHasLicense(String)} method,
 * to ascertain if the user is in one
 * of the license declared in by a {@code UserLicensesAllowed}. If a user is in none of
 * the declared roles then a 401 (UnAuthorized) response is returned.
 * 
 * @author Jay
 */
public class UserLicenseAllowedDynamicFeature implements DynamicFeature{
	//No other way to bind this so binding via Autowired
	@Autowired
	private PrincipalService principalService;
	@Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext configuration) {
        AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());

        // UserLicensesAllowed on the method takes precedence
        UserLicensesAllowed ul = am.getAnnotation(UserLicensesAllowed.class);
        if (ul != null) {
            configuration.register(new LicenseAllowedRequestFilter(ul.value(), principalService));
            return;
        }

        // UserLicensesAllowed on the class takes precedence 
        ul = resourceInfo.getResourceClass().getAnnotation(UserLicensesAllowed.class);
        if (ul != null) {
            configuration.register(new LicenseAllowedRequestFilter(ul.value(), principalService));
        }
    }
	@Priority(Priorities.USER_LICENSE_AUTHORIZATION) // license Authorization filter - should go after any authentication filters
    private static class LicenseAllowedRequestFilter implements ContainerRequestFilter {
        private final String[] licenseAllowed;
        private final PrincipalService principalService;

        LicenseAllowedRequestFilter(String[] licenseAllowed, PrincipalService principalService) {
            this.licenseAllowed = (licenseAllowed != null) ? licenseAllowed : new String[] {};
            this.principalService = principalService;
        }

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
        	principalService.updateUserInSecurityConext(requestContext);
        	UIMSecurityContext uimSecurityContext = (UIMSecurityContext)requestContext.getSecurityContext();
        	for (String license : licenseAllowed) {
        		if (uimSecurityContext.isUserHasLicense(license)) {
        			return;
        		}
        	}
        	//if no role matches then throw this
        	throw new WebApplicationException(SecurityResponseUtil.buildUserNotHasLicenseResponse());
        }
    }

}
