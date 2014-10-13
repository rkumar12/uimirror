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

/**
 * <p>Common Class for the bean based Document Mapping</p>
 * <p>Each class needs to extend this class to obtain the flexibility.</p>
 * @author Jay
 */
public abstract class BeanBasedDocument<T> extends MongoDocumentSerializer<T> implements Serializable{
	
	private static final long serialVersionUID = 1661966237018687368L;
	
	private String id;
	public BeanBasedDocument(){
		//NOP
	}
	public BeanBasedDocument(String id) {
		this.id = id;
	}
	
	public BeanBasedDocument(Map<String, Object> map){
		initFromMap(map);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
