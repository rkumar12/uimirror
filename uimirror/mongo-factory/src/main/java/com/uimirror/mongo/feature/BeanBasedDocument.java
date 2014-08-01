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
package com.uimirror.mongo.feature;

import java.io.Serializable;

import org.bson.BasicBSONObject;

import com.mongodb.DBObject;

/**
 * <p>Common Class for the bean based Document Mapping</p>
 * <p>Each class needs to extend this class to obtain the flexibility.</p>
 * @author Jay
 */
public class BeanBasedDocument extends BasicBSONObject implements Serializable, DBObject{
	
	private static final long serialVersionUID = 1661966237018687368L;
	
	public BeanBasedDocument(int size){
		super(size);
	}
	
	public BeanBasedDocument(){
	}

	@Override
	public void markAsPartialObject() {
		_isPartialObject = Boolean.TRUE;
	}

	@Override
	public boolean isPartialObject() {
		return _isPartialObject;
	}

	protected boolean _isPartialObject;

}
