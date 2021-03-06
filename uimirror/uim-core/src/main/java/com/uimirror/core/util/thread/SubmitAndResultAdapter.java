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
import java.util.concurrent.ExecutorCompletionService;

import org.springframework.util.CollectionUtils;

/**
 * @author Jay
 */
public class SubmitAndResultAdapter extends ExecutorServiceAbstractAdapter{

	private final int POOL_COUNT; 
	public SubmitAndResultAdapter(int numberOfThraed) {
		super(getExecutorService(numberOfThraed));
		completionService = new ExecutorCompletionService<Object>(this.executorService);
		this.POOL_COUNT = numberOfThraed;
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
		try{
			callabels.forEach(callabel -> completionService.submit(callabel));
		}catch(Exception e){
			//TODO if job get rejected then do someting
			//TODO handel the rejected job senaion, test it via singelton, after one sucessful execution, close the resource and try
		}
	}

	/* (non-Javadoc)
	 * @see com.uimirror.api.threadutil.ExecutorServiceAbstractAdapter#getResults()
	 */
	@Override
	public Object[] getResults() throws InterruptedException, ExecutionException {
		Object[] results = new Object[POOL_COUNT];
		for (int i = 0; i < POOL_COUNT; i++) {
			results[i] = completionService.take().get();
		}
		claimResource();
		return results;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.ExecutorServiceAbstractAdapter#submitTasks(java.util.List)
	 */
	@Override
	public void submitTasks(List<? extends Runnable> runabels)
			throws IllegalArgumentException {
		throw new IllegalArgumentException("No Supported");
	}
}
