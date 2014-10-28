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
package com.uimirror.sso.exception;

import com.uimirror.core.ErrorCodes;

/**
 * @author Jay
 */
public class LockedException extends AccountStatusException{

	private static final long serialVersionUID = -2474116394867889066L;

	public LockedException() {
		super(ErrorCodes._423, "Account has been locked temporarly");
	}

}
