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
package com.uimirror.ws.api.security;

import javax.ws.rs.container.ContainerRequestContext;

import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;

/**
 * A Marker interface that every processor needs to be implemented which will be used to populate 
 * the security context.
 * @author Jay
 */
public interface AccessTokenProcessor extends Processor<ContainerRequestContext, AccessToken>{

}
