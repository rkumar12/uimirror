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
 * General exception strategy which descibes the {@link Exception} into 
 * to application logic specific.
 * {@link P} specifies the Exception which needs to be translated
 * {@link R} Specifies the base exception to be returned. 
 * @author Jay
 */
public interface ExceptionMapper<P, R> {

	/**
	 * Contracts define the exception to which it needs to be translated.
	 * 
	 * @param exceptionToMap
	 * @return
	 */
	R mapIt(P exceptionToMap);
}
