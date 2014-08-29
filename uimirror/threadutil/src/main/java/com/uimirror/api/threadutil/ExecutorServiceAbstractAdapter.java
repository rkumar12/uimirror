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
package com.uimirror.api.threadutil;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Abstarct utility to make use of the API of the {@link ExecutorService} simple,
 * Any class implementing this should give the proper implementation of all the lifecycles</p>
 * @author Jay
 */
public abstract class ExecutorServiceAbstractAdapter {
	
	protected final ExecutorService executorService;
	protected ExecutorCompletionService<Object> completionService;

	public ExecutorServiceAbstractAdapter(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	public abstract void submitTask(List<? extends Callable<Object>> callabels) throws IllegalArgumentException;
	
	public abstract Object[] getResults() throws InterruptedException, ExecutionException;
	
	public void claimResource() {
		// always reclaim resources
		executorService.shutdown();
	}
	
	protected static ExecutorService getExecutorService(int poolCount){
		if(poolCount <= 0)
			throw new IllegalArgumentException("A thread pool can't be created as number pool is insufficent");
		return Executors.newFixedThreadPool(poolCount);
	}

}
