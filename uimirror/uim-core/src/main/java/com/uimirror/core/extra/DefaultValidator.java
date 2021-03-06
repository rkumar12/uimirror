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
package com.uimirror.core.extra;


/**
 * Defines Validation for the not null of the object
 * @author Jay
 */
public abstract class DefaultValidator{

	/**
	 * Checks for the not null of the passed object
	 * @param src to be validated
	 * @return <code>true</code> if the provided object is not null
	 */
	public boolean isNotNull(Object src){
		return null == src ? Boolean.FALSE : Boolean.TRUE;
	}


}
