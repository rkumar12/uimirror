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

/**
 * Factory way to initatilize the instance of the bean
 * Exception mapper may have multiple implemention but it should have 
 * some identifier to maintain in the factory 
 * 
 * @author Jay
 */
public interface ExceptionMapperFactory {
	ExceptionMapper getMapper(String name);
}
