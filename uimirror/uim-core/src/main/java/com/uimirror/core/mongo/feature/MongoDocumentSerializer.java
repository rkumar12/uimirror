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
package com.uimirror.core.mongo.feature;

import java.util.Map;

import com.uimirror.core.util.BeanToMap;

/**
 * <p>Default dto bean serialization and de-serialization</p>
 * @author Jay
 */
public abstract class MongoDocumentSerializer {

	/**
	 * <p>Defines contract how a object class while saving will be serialized</p>
	 * This gives a default implementation of object getting converted to {@link Map}
	 * @return
	 */
	public Map<String, Object> toMap(){
		return BeanToMap.toMap(this);
	}
	/**
	 * Defines contract, from the source object , value needs to be initialized.
	 * @param src
	 * @return the object which can be de-serialized
	 */
	public abstract Object initFromMap(Map<String, Object> src); 

}
