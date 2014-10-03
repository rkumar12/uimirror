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

import com.mongodb.BulkWriteException;
import com.mongodb.CommandFailureException;
import com.mongodb.MongoCursorNotFoundException;
import com.mongodb.MongoException;
import com.uimirror.core.ExceptionMapper;

/**
 * This translate Mongo exception to application specific
 * @author Jay
 */
public class MongoExceptionMapper implements ExceptionMapper{

	public static final String NAME = "MONGOEM";
	/* (non-Javadoc)
	 * @see com.uimirror.core.ExceptionMapper#mapIt(java.lang.Object)
	 */
	@Override
	public Throwable mapIt(Throwable exceptionToMap) {
		if(isBulkWrite(exceptionToMap))
			return translateBulkWrite();
		if(isCommandFailure(exceptionToMap))
			return translateCommandFailure();
		if(isCursorNotFound(exceptionToMap))
			return translateNotFound();
		if(exceptionToMap instanceof MongoException)
			return translateInternal();
		return exceptionToMap;
	}
	
	/**
	 * Checks if it is a {@link BulkWriteException}
	 * 
	 * @param e
	 * @return
	 */
	private boolean isBulkWrite(Throwable e){
		return e instanceof BulkWriteException;
	}
	
	private DBException translateBulkWrite(){
		return new BatchWriteException();
	}
	
	private boolean isCommandFailure(Throwable e){
		return e instanceof CommandFailureException;
	}
	
	private DBException translateCommandFailure(){
		return new SyntaxException();
	}
	
	private boolean isCursorNotFound(Throwable e){
		return e instanceof MongoCursorNotFoundException;
	}
	
	private DBException translateNotFound(){
		return new RecordNotFoundException();
	}
	
	private DBException translateInternal(){
		return new NoConnectionException();
	}
}
