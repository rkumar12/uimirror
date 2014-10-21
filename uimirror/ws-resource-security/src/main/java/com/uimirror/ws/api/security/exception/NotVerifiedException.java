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
 * Thrown if an authentication request is rejected because the account is newly created and not yet verified.
 * 
 * @author Jay
 */
public class NotVerifiedException extends AccountStatusException{

	private static final long serialVersionUID = -7623018440294937442L;

	/**
	 * This might because of account has been disabled due to some reason.
	 */
	public NotVerifiedException() {
		super(ErrorCodes._428, "Account has not yet verified");
	}

}
