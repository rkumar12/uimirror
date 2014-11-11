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

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.uimirror.core.util.BeanToMap;

/**
 * <p>Common Class for the bean based Document Mapping</p>
 * <p>Each class needs to extend this class to obtain the flexibility.</p>
 * @author Jay
 */
public abstract class AbstractBeanBasedDocument<T> implements MongoDocumentSerializer<T>, Serializable{
	
	private static final long serialVersionUID = 1661966237018687368L;
	
	/**
	 *Id Of the Document 
	 */
	private String id;
	
	public AbstractBeanBasedDocument(){
		//NOP
	}
	
	/**
	 * WHen Id of the document is known.
	 * @param id document id
	 */
	public AbstractBeanBasedDocument(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#isValidSource(java.util.Map)
	 */
	@Override
	public boolean isValidSource(Map<String, Object> src){
		if(CollectionUtils.isEmpty(src))
			throw new IllegalArgumentException("Initialization Source can't be empty");
		return Boolean.TRUE;
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#writeToMap()
	 */
	@Override
	public Map<String, Object> writeToMap() throws IllegalStateException{
		return BeanToMap.toMap(this);
	}
	
	/** 
	 * When implemented class has not implemented this, will through this.
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#updateId(java.lang.String)
	 */
	@Override
	public T updateId(String id){
		//will throw this exception when required bean doesn't implement this
		throw new IllegalAccessError("Can't update id into the document");
	}
	
	@Override
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
