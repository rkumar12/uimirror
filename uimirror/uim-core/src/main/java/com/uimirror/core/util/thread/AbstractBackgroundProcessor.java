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
package com.uimirror.core.util.thread;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Bridge between the service end point and application service response 
 * @author Jay
 */
public abstract class AbstractBackgroundProcessor<P, R> implements BackgroundProcessor<P, R>{

	private final ExecutorServiceAbstractAdapter adapter;
	protected static final Logger LOG = LoggerFactory.getLogger(AbstractBackgroundProcessor.class);
	
	public AbstractBackgroundProcessor(int size, boolean requiredResult){
		adapter = requiredResult ? new SubmitAndResultAdapter(size) : new SubmitAndForgetAdapter(size);
	}
	
	public AbstractBackgroundProcessor(int size){
		adapter = new SubmitAndForgetAdapter(size);
	}
	
	/**
	 * Gives the current initialized thread adaptors
	 * @return
	 */
	protected ExecutorServiceAbstractAdapter getAdaptor(){
		return adapter;
	}
	
	/**
	 * This will get the result of the submitted tasks
	 * @return
	 */
	protected Object[] getResults() throws IllegalThreadStateException{
		if(adapter instanceof SubmitAndForgetAdapter)
			throw new IllegalThreadStateException("type of background process doesn't support");
		else{
			try {
				return adapter.getResults();
			} catch (InterruptedException | ExecutionException e) {
				LOG.error("[BACKGROUND-TASK_ERROR] Something went wrong while getting the submited task result {}", e);
				return null;
			}
		}
	}

}
