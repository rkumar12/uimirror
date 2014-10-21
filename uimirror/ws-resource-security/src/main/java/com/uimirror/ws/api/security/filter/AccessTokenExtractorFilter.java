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
//package com.uimirror.ws.api.security.filter;
//
//import java.io.IOException;
//
//import javax.annotation.Priority;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.uimirror.core.Priorities;
//import com.uimirror.ws.api.security.ouath.BearerTokenExtractor;
//import com.uimirror.ws.api.security.ouath.TokenExtractor;
//import com.uimirror.ws.api.security.ouath.UIMirrorSecurity;
//
///**
// * <p>This is the common resource filter to populate the access token details.</p>
// * <p>This intercepts the header and took the bearer token, decode it and populate the token details</p>
// * <p>if token details are not valid then it will send invalid response directly from here.</p>
// * @author Jay
// */
//@Priority(Priorities.TOKENEXTRACTOR)
//public class AccessTokenExtractorFilter implements ContainerRequestFilter{
//	
//	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenExtractorFilter.class);
//
//	/* (non-Javadoc)
//	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
//	 */
//	@Override
//	public void filter(ContainerRequestContext request) throws IOException {
//		LOG.debug("[AUTH-START]-Request Getting Intercepted");
//		TokenExtractor extractor = new BearerTokenExtractor();
//		request.setSecurityContext(new UIMirrorSecurity(extractor.extract(request)));
//		LOG.debug("[AUTH-END]-Request Getting Intercepted");
//	}
//
//}
