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
package com.uimirror.core.extra;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.uimirror.core.exceptions.ExceptionMapper;

/**
 * Specifies in the method level
 * when there will be any exception happened, this will translate 
 * those into business specific logic
 * 
 * @author Jay
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface MapException {

	/**
	 * An instance alias name of {@link ExceptionMapper} which will be used to map the exception
	 * @return the class name that will be used
	 */
	String use();
}
