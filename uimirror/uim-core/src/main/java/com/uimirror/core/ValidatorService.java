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
 * Common Validation Service which client needs to give implementation for its need
 * @author Jay
 */
public interface ValidatorService {

	/**
	 * Specifies the contract that a implementer needs to validate the current context 
	 * by initializing the source via its constructor.
	 * @param src
	 * @return
	 */
	boolean validate(Object src);
	
	/**
	 * <p>This will validate and match with source and destination</p>
	 * @param src
	 * @param des
	 * @return
	 */
	boolean doMatch(Object src, Object des);
}
