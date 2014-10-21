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
 * A common validation {@link Exception} of type {@link RuntimeException}
 * to show error fields along with a message
 * @author damart1
 *
 */
public class ValidationException extends RuntimeException{
	
	private static final long serialVersionUID = 3327973402795061025L;

	private String[] fileds;
	private int errorCode;
	public ValidationException(String[] fileds, String message, int errorCode) {
		super(message);
		this.fileds = fileds;
		this.errorCode = errorCode;
	}
	public ValidationException(String[] fileds, String message) {
		super(message);
		this.fileds = fileds;
	}
	public ValidationException(String message) {
		super(message);
	}
	public String[] getFileds() {
		return fileds;
	}
	public int getErrorCode() {
		return errorCode;
	}
	

}
