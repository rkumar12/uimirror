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

import static com.uimirror.core.job.JobConstants.COMPLETED_ON;
import static com.uimirror.core.job.JobConstants.MESSAGE;
import static com.uimirror.core.job.JobConstants.STARTED_ON;
import static com.uimirror.core.job.JobConstants.STATUS;
import static com.uimirror.core.mongo.BasicMongoOperators.NATURAL_SORT;
import static com.uimirror.core.mongo.BasicMongoOperators.SET;
import static com.uimirror.core.mongo.feature.BasicDBFields.ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.core.job.SimpleJob;
import com.uimirror.core.job.Status;
import com.uimirror.core.util.DateTimeUtil;

/**
 * This will be the Simple Job store In Mongo DB implementations
 * 
 * @author Jay
 */
public class PersistedSimpleJobMongoStore extends AbstractMongoStore<SimpleJob> implements SimpleJobStore{
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection colection
	 * @param seqCollection collection name
	 * @param seqName sequence name
	 */
	public PersistedSimpleJobMongoStore(DBCollection collection, DBCollection seqCollection, String seqName){
		super(collection, seqCollection, seqName, SimpleJob.class);
	}
	
	/**
	 * Assign/ Create collection from the given {@link DBCollection}, 
	 * in case of no sequence required
	 * @param collection collection
	 */
	public PersistedSimpleJobMongoStore(DBCollection collection){
		super(collection, SimpleJob.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.job.store.SimpleJobStore#findById(java.lang.String)
	 */
	@Override
	public SimpleJob findById(String jobId) throws DBException {
		return getById(jobId);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.job.store.SimpleJobStore#findMostRecentJob()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SimpleJob findMostRecentJob() throws DBException {
		String currentSequence = getCurrentSequence();
		SimpleJob job = null;
		if(currentSequence != null){
			job = getById(currentSequence);
		}else{
			DBObject dbObj = new BasicDBObject(NATURAL_SORT, -1);
			DBCursor sort = getCollection().find().limit(1).sort(dbObj);
			DBObject result = sort.one();
			if(result != null)
				job = SimpleJob.deSerailize(result.toMap());
		}
		return job;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		DBObject obj = new BasicDBObject(STARTED_ON, 1);
		getCollection().createIndex(obj);
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.job.store.SimpleJobStore#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String jobId) throws DBException {
		super.deleteById(jobId);
		
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.job.store.SimpleJobStore#findOrphanJobs()
	 */
	@Override
	public List<String> findOrphanJobs() throws DBException {
		DBObject sortQuery = new BasicDBObject(STARTED_ON, -1);
		DBCursor cursour = getCollection().find().sort(sortQuery);
		List<String> orphans = new ArrayList<String>(5);
		cursour.forEach((obj)->{
			orphans.add((String)obj.get(ID));
		});
		orphans.remove(0);
		return orphans;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.job.store.SimpleJobStore#markAsComplete(java.lang.String)
	 */
	@Override
	public void markAsComplete(String jobId) throws DBException {
		Map<String, Object> cmpMap = completedTimeMap();
		cmpMap.put(STATUS, Status.COMPLETE.getStatus());
		Map<String, Object> setMap = new WeakHashMap<String, Object>();
		setMap.put(SET, cmpMap);
		updateById(jobId, setMap);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.job.store.SimpleJobStore#markAsFailed(java.lang.String, java.lang.String)
	 */
	@Override
	public void markAsFailed(String jobId, String errorMessage) throws DBException {
		Map<String, Object> cmpMap = completedTimeMap();
		cmpMap.put(STATUS, Status.FAILED.getStatus());
		if(StringUtils.hasText(errorMessage))
			cmpMap.put(MESSAGE, errorMessage);
		Map<String, Object> setMap = new WeakHashMap<String, Object>();
		setMap.put(SET, cmpMap);
		updateById(jobId, setMap);
		
	}
	
	private Map<String, Object> completedTimeMap(){
		Map<String, Object> map = new WeakHashMap<String, Object>();
		map.put(COMPLETED_ON, DateTimeUtil.getCurrentSystemUTCEpoch());
		return map;
	}
	
}
