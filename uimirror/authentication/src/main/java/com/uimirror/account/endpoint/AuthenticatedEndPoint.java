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
package com.uimirror.account.endpoint;

import java.io.Serializable;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.form.ClientMetaForm;

/**
 * Contains the Basic Authenticated Security context place holder
 * which will be very much handy for the authentications and owner details
 * @author Jay
 */
public class AuthenticatedEndPoint extends ClientMetaForm implements Serializable{

	private static final long serialVersionUID = -7775430991569118841L;

	private @Context SecurityContext securityContext;

	public AuthenticatedEndPoint() {
		//NOP
	}
	
	/**
	 * Gets the {@link AccessToken} if present else null
	 * @return
	 */
	public AccessToken getToken(){
		AccessToken token = (AccessToken)securityContext.getUserPrincipal();
		return token;
	}
	
	/**
	 * Gets the owner of the {@link AccessToken} if present else null
	 * @return
	 */
	public String getOwner(){
		AccessToken token = getToken();
		return token == null ? null : token.getOwner(); 
	}
	
	/**
	 * Gets the client of the {@link AccessToken} if present else null
	 * @return
	 */
	public String getClient(){
		AccessToken token = getToken();
		return token == null ? null : token.getClient(); 
	}
	
	/**
	 * Gets the scope of the {@link AccessToken} if present else null
	 * 
	 * @return
	 */
	public Scope getScope(){
		AccessToken token = getToken();
		return token == null ? null : token.getScope(); 
	}

}