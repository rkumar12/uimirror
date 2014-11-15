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
 * Common Validation Service which client needs to give implementation for its need
 * @author Jay
 */
public interface ValidatorService<P> {

	/**
	 * Specifies the contract that a implementer needs to validate the current context 
	 * by initializing the source via its constructor.
	 * @param src which needs to be validated
	 * @return <code>true</code> if valid else <code>false</code>
	 */
	boolean validate(P src);
	
}
