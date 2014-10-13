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

import org.springframework.util.CollectionUtils;

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
	 * @throws IllegalStateException 
	 */
	public Map<String, Object> toMap() throws IllegalStateException{
		return BeanToMap.toMap(this);
	}
	/**
	 * Defines contract, from the source object , value needs to be initialized.
	 * @param src
	 */
	public abstract Object initFromMap(Map<String, Object> src); 
	
	/**
	 * Validates the incoming source to initialize
	 * @param src
	 */
	protected void validateSource(Map<String, Object> src){
		if(CollectionUtils.isEmpty(src))
			throw new IllegalArgumentException("Initialization Source can't be empty");
	}

}
