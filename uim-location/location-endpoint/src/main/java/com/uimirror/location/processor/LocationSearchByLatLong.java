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

import com.uimirror.core.GeoLongLat;
import com.uimirror.core.Processor;
import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.core.GeocodeResponse;
import com.uimirror.location.core.components.GeocodeStatus;
import com.uimirror.location.core.components.LocationNotFoundException;
import com.uimirror.location.google.v3.GoogleGeocoder;
import com.uimirror.location.store.LocationStore;

/**
 * This will get the location details from the DB based on the location already saved.
 *  or from the google v3 map API.
 * @author Jay
 */
public class LocationSearchByLatLong implements Processor<GeoLongLat, DefaultLocation>{
	
	protected static Logger LOG = LoggerFactory.getLogger(LocationSearchByLatLong.class);
	private @Autowired LocationStore persistedLocationMongoStore;
	private @Autowired GoogleGeocoder googleGeocoder;
	private @Autowired TransformerService<GeocodeResponse, DefaultLocation> googleResponseToLocationTransformer;
	private @Autowired Processor<DefaultLocation, DefaultLocation> locationStoreProcessor;

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public DefaultLocation invoke(GeoLongLat loc) throws ApplicationException {
		LOG.debug("[SINGLE]-Reteriving the stored location details");
		DefaultLocation location = getLocationByCord(loc.getLongitude(), loc.getLatitude());
		return location;
	}
	
	/**
	 * Finds the location based on the coordinates.
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	private DefaultLocation getLocationByCord(double longitude, double latitude){
		DefaultLocation location = null;
		try{
			location = persistedLocationMongoStore.getByCord(longitude, latitude);
		}catch(RecordNotFoundException e){
			LOG.warn("[MINOR]-Location Cordinates are not found in the store");
		}
		if(location == null){
			location = getFromGoogleV3(longitude, latitude);
		}
		return location;
	}
	
	/**
	 * Get Location from the google V3
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	private DefaultLocation getFromGoogleV3(double longitude, double latitude){
		GeocodeResponse lookupAddress = googleGeocoder.lookupAddress(latitude, longitude);
		DefaultLocation loc = null;
		if(GeocodeStatus.OK == lookupAddress.getGeocodeStatus())
			loc = googleResponseToLocationTransformer.transform(lookupAddress);
		else
			throw new LocationNotFoundException();
		return loc;
	}
	
	

}
