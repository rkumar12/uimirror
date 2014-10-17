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

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.springframework.util.CollectionUtils;

/**
 * <p>This is a utility implementation of {@link ExecutorService},
 * where other API using this service will submit a task which has callable implementation
 * and forget about the result</p>
 * <p>This creates the number of the thread pool required by the client and 
 * Appropriately manage to create/assign the pool.</p>
 * 
 * @author Jay
 * @since 0.1
 */
//TODO why callable here it can be runnable
public class SubmitAndForgetAdapter extends ExecutorServiceAbstractAdapter{

	public SubmitAndForgetAdapter(int numberOfThraed) {
		super(getExecutorService(numberOfThraed));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.api.threadutil.ExecutorServiceAbstractAdapter#submitTask(java.util.List)
	 */
	@Override
	public void submitTask(List<? extends Callable<Object>> callabels) throws IllegalArgumentException {
		if(CollectionUtils.isEmpty(callabels)){
    		claimResource();
    		throw new IllegalArgumentException("No Job is there to execute");
    	}
		callabels.forEach(callabel -> executorService.submit(callabel));
	}

	/**
	 * Submit the Runnable tasks to execute in background.
	 *  
	 * @param runabels
	 * @throws IllegalArgumentException
	 */
	@Override
	public void submitTasks(List<? extends Runnable> runabels) throws IllegalArgumentException {
		if(CollectionUtils.isEmpty(runabels)){
    		claimResource();
    		throw new IllegalArgumentException("No Job is there to execute");
    	}
		runabels.forEach(callabel -> executorService.submit(callabel));
	}

	/* (non-Javadoc)
	 * @see com.uimirror.api.threadutil.ExecutorServiceAbstractAdapter#getResults()
	 */
	@Override
	public Object[] getResults() throws InterruptedException, ExecutionException {
		return null;
	}
	
}
