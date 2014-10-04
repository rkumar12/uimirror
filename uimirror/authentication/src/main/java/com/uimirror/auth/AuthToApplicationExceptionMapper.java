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
package com.uimirror.auth;

import com.uimirror.core.ExceptionMapper;
import com.uimirror.core.auth.AuthenticationException;
import com.uimirror.core.auth.BadCredentialsException;
import com.uimirror.core.auth.DisabledException;
import com.uimirror.core.auth.LockedException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.rest.extra.InternalException;
import com.uimirror.core.rest.extra.UnAuthorizedException;

/**
 * This translate authentication DB exception to 
 * application specific exception {@link AuthenticationException}
 * @author Jay
 */
public class AuthToApplicationExceptionMapper implements ExceptionMapper{

	public static final String NAME = "AUTHTOAPPEM";
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.ExceptionMapper#mapIt(java.lang.Object)
	 */
	@Override
	public Throwable mapIt(Throwable exceptionToMap) {
		if(isNoValidInput(exceptionToMap))
			return translateInvalidInput();
		
		if(isInvalidCredential(exceptionToMap))
			return translateToInvalidCredential();
		
		if(isDisabled(exceptionToMap))
			return translateToDisabled();
		
		if(isBlocked(exceptionToMap))
			return translateToBlocked();
		
		if(isInternal(exceptionToMap))
			return translateToInternal();
		
		return translateToInternal();
	}
	
	private boolean isNoValidInput(Throwable e){
		return e instanceof java.lang.IllegalArgumentException; 
	}
	
	private ApplicationException translateInvalidInput(){
		return new IllegalArgumentException();
	}
	
	private boolean isInvalidCredential(Throwable e){
		return e instanceof BadCredentialsException; 
	}
	
	private ApplicationException translateToInvalidCredential(){
		return new UnAuthorizedException();
	}
	
	public boolean isDisabled(Throwable e){
		return e instanceof DisabledException;
	}
	
	private ApplicationException translateToDisabled(){
		return new UnAuthorizedException("Account Disabled");
	}
	
	public boolean isBlocked(Throwable e){
		return e instanceof LockedException;
	}
	
	private ApplicationException translateToBlocked(){
		return new UnAuthorizedException("Account Blocked");
	}
	
	private boolean isInternal(Throwable e){
		return e instanceof com.uimirror.core.auth.InternalException; 
	}
	
	private ApplicationException translateToInternal(){
		return new InternalException();
	}
}
