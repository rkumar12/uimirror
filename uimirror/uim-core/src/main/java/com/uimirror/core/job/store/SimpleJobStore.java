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
package com.uimirror.core.job.store;

import java.util.List;

import com.uimirror.core.dao.DBException;
import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.job.SimpleJob;

/**
 * A basic repo for the SimpleJob 
 * that deals with store and retrieve of the client
 * @author Jay
 */
public interface SimpleJobStore {

	/**
	 * Stores the JOb details into the store
	 * @param job which needs to be saved
	 * @return updated job with ID
	 * @throws DBException in case of any exception
	 */
	SimpleJob store(SimpleJob job) throws DBException;
	
	/**
	 * Finds the SimpleJob, based on the job ID
	 * @param jobId for which {@link SimpleJob} needs to be searched
	 * @return found SimpleJob, else {@link RecordNotFoundException}
	 * @throws DBException in case of any exception
	 */
	SimpleJob findById(String jobId) throws DBException;
	
	/**
	 * Finds the most recent job, that has been intiated
	 * @return found SimpleJob
	 * @throws DBException in case of any exception
	 */
	SimpleJob findMostRecentJob() throws DBException;
	
	/**
	 * Delete the given job ID details
	 * @param jobId who needs to deleted
	 * @throws DBException in case of any exception
	 */
	void deleteById(String jobId) throws DBException;
	
	/**
	 * Finds the Jobs which has been created before 45 minutes and status is completed.
	 * @return List of the job
	 * @throws DBException in case of any exception
	 */
	List<String> findOrphanJobs() throws DBException;
	
	/**
	 * Marks the job status as completed.
	 * @param jobId for which update will happen
	 * @throws DBException in case of any exception
	 */
	void markAsComplete(String jobId) throws DBException;
	
	/**
	 * Marks the job status as failed with the error message.
	 * @param jobId for which update will happen
	 * @param errorMessage message that will be updated
	 * @throws DBException in case of any exception
	 */
	void markAsFailed(String jobId, String errorMessage) throws DBException;
	
}
