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
package com.uimirror.location.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.store.LocationStore;

/**
 * This will get the location details from the DB based on the location already saved.
 * 
 * @author Jay
 */
public class LocationSearchById implements Processor<String, DefaultLocation>{
	
	protected static Logger LOG = LoggerFactory.getLogger(LocationSearchById.class);
	private @Autowired LocationStore persistedLocationMongoStore;

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public DefaultLocation invoke(String locId) throws ApplicationException {
		LOG.debug("[SINGLE]-Reteriving the stored location details");
		DefaultLocation loc = persistedLocationMongoStore.getByLocationId(locId);
		return loc;
	}

}
