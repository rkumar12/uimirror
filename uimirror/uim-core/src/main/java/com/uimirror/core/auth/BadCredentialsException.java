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

import com.uimirror.core.ErrorCodes;

/**
 * @author Jay
 */
public class BadCredentialsException extends AccountStatusException{

	private static final long serialVersionUID = 551191787946467768L;

	public BadCredentialsException() {
		super(ErrorCodes._401, "Credentials are not valid");
	}

}
