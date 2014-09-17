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
package com.uimirror.core.dao;

import java.util.Map;

/**
 * Mongo Serialization helper
 * @author Jay
 */
public interface MongoSerializer {

	/**
	 * <p>Defines contract how a object class while saving will be serialized</p>
	 * @param src
	 * @return
	 */
	Map<String, Object> toMap(Object src);
}
