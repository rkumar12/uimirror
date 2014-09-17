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
package com.uimirror.core.transformer;

import com.google.gson.Gson;
import com.uimirror.core.Constants;

/**
 * Provides implementation to convert to the given map {@link Map<String, Object>} 
 * to string format i.e {@link Gson}
 * @author Jay
 */
public class GsonResponseTransformer implements EndPointResponseTransformer<String, Object>{

	/* (non-Javadoc)
	 * @see com.uimirror.core.EndPointResponseTransformer#doTransform(java.lang.Object)
	 */
	@Override
	public String doTransform(Object result) {
		result = result == null ? Constants.EMPTY : result; 
		return new Gson().toJson(result);
	}

}
