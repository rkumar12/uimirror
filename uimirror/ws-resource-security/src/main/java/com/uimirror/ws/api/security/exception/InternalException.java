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
package com.uimirror.ws.api.security.exception;

import com.uimirror.core.ErrorCodes;

/**
 * Thrown if an authentication request is rejected because of some internal error. 
 * Makes no assertion as to whether or not the credentials were valid.
 * 
 * @author Jay
 */
public class InternalException extends AccountStatusException{

	private static final long serialVersionUID = 5345459135563382789L;

	/**
	 * This might because of account details are not accessible to validate because of some reason.
	 */
	public InternalException() {
		super(ErrorCodes._500, "Internal error, while authenticating user");
	}

}
