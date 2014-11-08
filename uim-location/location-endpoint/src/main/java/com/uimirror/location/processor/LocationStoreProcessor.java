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
import com.uimirror.core.util.thread.BackgroundProcessor;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.store.CityStore;
import com.uimirror.location.store.CountryStore;
import com.uimirror.location.store.LocalityStore;
import com.uimirror.location.store.LocationStore;
import com.uimirror.location.store.StateStore;

/**
 * This will store all the component of the location and return the complete 
 * details
 * @author Jay
 */
public class LocationStoreProcessor implements Processor<DefaultLocation, DefaultLocation>{
	
	protected static Logger LOG = LoggerFactory.getLogger(LocationStoreProcessor.class);
	private @Autowired CountryStore persistedCountryMongoStore;
	private @Autowired StateStore persistedStateMongoStore;
	private @Autowired CityStore persistedCityMongoStore;
	private @Autowired LocalityStore persistedLocalityMongoStore;
	private @Autowired LocationStore persistedLocationMongoStore;
	private @Autowired BackgroundProcessorFactory<DefaultLocation, DefaultLocation> backgroundProcessorFactory;

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public DefaultLocation invoke(DefaultLocation loc) throws ApplicationException {
		LOG.info("[START]- Storing the missing location for the cordinate.");
//		Country savedCountry = storeCountry(loc.getCountry());
//		State savedState = storeState(loc.getState());
//		City savedCity = storeCity(loc.getCity());
//		Locality savedLocality = storeLocality(loc.getLocality());
//		//Create Instance to store
//		DefaultLocation interMidetoryLoc = loc.getUpdatedInstance(savedCountry, savedState, savedCity, savedLocality); 
		DefaultLocation interMidetoryLoc = store(loc); 
		DefaultLocation savedLoc = persistedLocationMongoStore.store(interMidetoryLoc);
		LOG.info("[END]- Storing the missing location for the cordinate.");
		//Finally return with updated value
		return savedLoc.getUpdatedInstance(interMidetoryLoc.getCountry(), interMidetoryLoc.getState(), interMidetoryLoc.getCity(), interMidetoryLoc.getLocality());
	}
	
	private DefaultLocation store(DefaultLocation loc){
		BackgroundProcessor<DefaultLocation, DefaultLocation> bg = backgroundProcessorFactory.getProcessor(LocationStoreBackGroundProcessor.NAME);
		bg.invoke(loc);
		DefaultLocation intLoc = bg.getResult();
		DefaultLocation location = loc.getUpdatedInstance(intLoc.getCountry(), intLoc.getState(), intLoc.getCity(), intLoc.getLocality());
		return location;
	}

}
