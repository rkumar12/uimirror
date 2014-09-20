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

import com.owlike.genson.Genson;

/**
 * Transforms the source object into json string
 * 
 * @author Jay
 */
public class JsonResponseTransFormer implements ResponseTransFormer<String>{

	public JsonResponseTransFormer() {
	}

	@Override
	public String doTransForm(Object src) {
		return new Genson().serialize(src);
	}

}
