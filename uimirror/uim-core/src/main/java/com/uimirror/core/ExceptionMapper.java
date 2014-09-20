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
package com.uimirror.core;

/**
 * General exception strategy which descibes the {@link Throwable} into 
 * to application logic specific.
 * Note, the class which is not handling or not falling into the specific exception category should
 * throw the exception as it is
 * 
 * @author Jay
 */
public interface ExceptionMapper {

	/**
	 * Contracts define the exception to which it needs to be translated.
	 * 
	 * @param exceptionToMap
	 * @return
	 */
	Throwable mapIt(Throwable exceptionToMap);
}
