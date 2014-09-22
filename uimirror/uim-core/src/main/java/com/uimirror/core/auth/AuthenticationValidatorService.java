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
package com.uimirror.core.auth;

/**
 * @author Jay
 */
//TODO
public interface AuthenticationValidatorService {

	void validateState() throws DisabledException;
	
	void validateStatus() throws LockedException;
	
	void validateCredential() throws BadCredentialsException;
}
