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
package com.uimirror.core.rest.extra;

import javax.ws.rs.Produces;

/**
 * Common utility which does response transformer for the response entity
 * 
 * @author Jay
 */
public interface ResponseTransFormer<R> {

	/**
	 * <p>According to the {@link Produces} type, implementing class should return
	 * value accordingly</p>
	 * @param src
	 * @return
	 */
	R doTransForm(Object src);
}
