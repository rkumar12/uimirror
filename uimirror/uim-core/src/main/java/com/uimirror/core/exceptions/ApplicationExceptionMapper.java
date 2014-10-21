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
package com.uimirror.core.exceptions;

import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.InternalException;

/**
 * This translate Mongo exception to application specific
 * @author Jay
 */
public class ApplicationExceptionMapper implements ExceptionMapper{

	public static final String NAME = "AEM";
	/* (non-Javadoc)
	 * @see com.uimirror.core.ExceptionMapper#mapIt(java.lang.Object)
	 */
	@Override
	public Throwable mapIt(Throwable exceptionToMap) {
		if(isSkipable(exceptionToMap))
			return exceptionToMap;
		if(isInvalidInput(exceptionToMap))
			return translateToInvalidInput();
		if(!isSkipable(exceptionToMap))
			return translateToOthers();
		return exceptionToMap;
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	private boolean isInvalidInput(Throwable e){
		return e instanceof IllegalArgumentException;
	}
	
	private ApplicationException translateToInvalidInput(){
		return new com.uimirror.core.rest.extra.IllegalArgumentException();
	}
	
	private ApplicationException translateToOthers(){
		return new InternalException();
	}
	
	private boolean isSkipable(Throwable e){
		return e instanceof ApplicationException;
	}
	
}
