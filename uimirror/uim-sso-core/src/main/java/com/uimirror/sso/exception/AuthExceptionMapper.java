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
package com.uimirror.sso.exception;

import com.uimirror.core.exceptions.ExceptionMapper;
import com.uimirror.core.exceptions.db.DBException;
import com.uimirror.core.exceptions.db.RecordNotFoundException;

/**
 * This translate authentication DB exception to 
 * application specific exception {@link AuthenticationException}
 * @author Jay
 */
public class AuthExceptionMapper implements ExceptionMapper{

	public static final String NAME = "AUTHEM";
	/* (non-Javadoc)
	 * @see com.uimirror.core.ExceptionMapper#mapIt(java.lang.Object)
	 */
	@Override
	public Throwable mapIt(Throwable exceptionToMap) {
		if(isNoRecord(exceptionToMap))
			return translateNoRecord();
		
		if(isInternal(exceptionToMap))
			return translateInternal();
		
		return exceptionToMap;
	}
	
	private boolean isNoRecord(Throwable e){
		return e instanceof RecordNotFoundException; 
	}
	
	private AuthenticationException translateNoRecord(){
		return new BadCredentialsException();
	}
	
	private boolean isInternal(Throwable e){
		return e instanceof DBException && !(e instanceof RecordNotFoundException); 
	}
	
	private AuthenticationException translateInternal(){
		return new InternalException();
	}
}
