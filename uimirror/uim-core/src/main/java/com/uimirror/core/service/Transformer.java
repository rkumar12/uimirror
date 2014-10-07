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
package com.uimirror.core.service;

/**
 * A common transformer contract that defines 
 * all the bean or object needs to be transformed from 
 * one type to other depends on the parameter specified
 * 
 * @author Jay
 */
public interface Transformer<P,R> {

	/**
	 * While defining the implementation for the transformer,
	 * make sure it should do the basic operation from the source type 
	 * to destination type conversion
	 * @param src
	 * @return
	 */
	R transform(P src);
}
