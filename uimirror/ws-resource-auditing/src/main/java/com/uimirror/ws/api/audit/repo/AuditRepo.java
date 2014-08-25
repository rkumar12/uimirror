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
package com.uimirror.ws.api.audit.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.mongodb.MongoException;
import com.uimirror.ws.api.audit.Audit;

/**
 * @author Jay
 * TODO update the documentation
 */
public interface AuditRepo {
	
	public static final String AUDIT_DB = "uim_audit";
	public static final String CLIENT_COLLECTION = "client";
	
	/**
	 * <p>This will create a new Audit </p>
	 * @param audit to be stored
	 * @throws IllegalArgumentException in case audit is null or not valid
	 * @throws MongoException
	 */
	public void insert(Audit audit) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>Finds a Audit by the ID</p>
	 * @param id
	 * @return {@code Audit} instance
	 * @throws IllegalArgumentException in case id is not valid
	 * @throws MongoException
	 */
	public Audit findById(String id) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>Finds all the Audit</p>
	 * @return
	 * @throws MongoException
	 */
	public List<Audit> findAll() throws MongoException;
	
	/**
	 * <p>Finds A list of Audit after a time frame</p>
	 * @param time
	 * @return
	 * @throws IllegalArgumentException in case id and datetime is invalid
	 * @throws MongoException
	 */
	public List<Audit> findByAfterDate(LocalDateTime time) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>Finds list of the audit for the specified time range</p>
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws IllegalArgumentException if times are not valid
	 * @throws MongoException
	 */
	public List<Audit> findByDateRange(LocalDateTime fromTime, LocalDateTime toTime) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>Finds the {@code Audit} list by Client Id</p>
	 * @param clientId
	 * @return
	 * @throws IllegalArgumentException if clientId Is not valid
	 * @throws MongoException
	 */
	public List<Audit> findByClientId(String clientId) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>Finds By Client Id and AFter the time specified</p>
	 * @param clientId
	 * @param time
	 * @return
	 * @throws IllegalArgumentException if client id and time are not valid
	 * @throws MongoException
	 */
	public List<Audit> findByClientIdAndAfterDate(String clientId, LocalDateTime time) throws IllegalArgumentException, MongoException;
	
	/**
	 * <p>Find by client ID and time range</p>
	 * @param clientId
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws IllegalArgumentException if clientId and timeRange are not valid
	 * @throws MongoException
	 */
	public List<Audit> findByClientIdAndTimeRange(String clientId, LocalDate fromTime, LocalDate toTime) throws IllegalArgumentException, MongoException;

	/**
	 * <p>Number Of Audit after a date</p>
	 * @param time
	 * @return
	 * @throws IllegalArgumentException if time is not valid
	 * @throws MongoException
	 */
	public int countByAfterDate(LocalDateTime time) throws IllegalArgumentException, MongoException;
	
	public int countByDateRange(LocalDateTime fromTime, LocalDateTime toTime) throws IllegalArgumentException, MongoException;
	
	public int countByClientId(String clientId) throws IllegalArgumentException, MongoException;
	
	public int countByClientIdAndAfterdate(String clientId, LocalDate time) throws IllegalArgumentException, MongoException;
	
	public int countByClientIdAndDateRange(String clientId, LocalDateTime fromTime, LocalDateTime toTime) throws IllegalArgumentException, MongoException;
	
	public int countSuccessResponseByClientId(String clientId) throws IllegalArgumentException, MongoException;
	
	public int countSuccessResponseByClientDateRange(String clientId, LocalDateTime fromTime, LocalDateTime toTime) throws IllegalArgumentException, MongoException;
	
	public int countSuccessResponseByClientIdAndAfterDate(String clientId, LocalDateTime time) throws IllegalArgumentException, MongoException;
	
	public int countAll() throws MongoException;
	
	public void deleteById(String id) throws IllegalArgumentException, MongoException;
	
	public void deleteAll() throws MongoException;
	
	public void updateStatusById(String id, String status)throws IllegalArgumentException, MongoException;

}
