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
package com.uimirror.ws.api.audit.exception;

import com.uimirror.core.exceptions.BaseRunTimeException;

/**
 * <p>Base {@link RuntimeException} for the audit processes</p>
 * @author Jay
 */
public class AuditException extends BaseRunTimeException{

	private static final long serialVersionUID = -5142357461868790284L;

	/**
	 * @param errorCode
	 * @param msg
	 * @param cause
	 */
	public AuditException(int errorCode, String msg, Throwable cause) {
		super(errorCode, msg, cause);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public AuditException(int errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param errorCode
	 */
	public AuditException(int errorCode) {
		super(errorCode);
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public AuditException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param msg
	 */
	public AuditException(String msg) {
		super(msg);
	}

}
