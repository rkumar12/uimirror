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

/**
 * @author Jay
 */
@Deprecated
public interface ResponseTransFormerFactory {
	@SuppressWarnings("rawtypes")
	ResponseTransFormer getTransFormer(String name);
}
