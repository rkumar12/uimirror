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
//package com.uimirror.ws.api.security.service;
//
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.container.ContainerRequestContext;
//
///**
// * @author Jay
// */
//public interface PrincipalService {
//
//	/**
//	 * <p>This will populate the associated user to the access token</p>
//	 * @param request
//	 * @throws WebApplicationException in case it didn't meet any criteria
//	 */
//	public void updateUserInSecurityConext(ContainerRequestContext request) throws WebApplicationException;
//	
//	/**
//	 * <p>This will populate the associated client to the access token</p>
//	 * @param request
//	 * @throws WebApplicationException in case it didn't meet any criteria
//	 */
//	public void updateClientInSecurityConext(ContainerRequestContext request) throws WebApplicationException;
//	
//	/**
//	 * <p>This will populate the security context and set to the request back</p>
//	 * <p>First Checks if the principal is valid, if not it tries to load from cache</p>
//	 * <p>It only loads the access token to avoid eager loading of other dependant objects</p>
//	 * @param request
//	 * @throws WebApplicationException in case it didn't meet any criteria
//	 */
//	public void populateTokenOnlySecurityConext(ContainerRequestContext request) throws WebApplicationException;
//}
