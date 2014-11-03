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
import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.location.City;
import com.uimirror.location.Country;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.DefaultLocation.LocationBuilder;
import com.uimirror.location.Locality;
import com.uimirror.location.State;
import com.uimirror.location.store.CityStore;
import com.uimirror.location.store.CountryStore;
import com.uimirror.location.store.LocalityStore;
import com.uimirror.location.store.LocationStore;
import com.uimirror.location.store.StateStore;

/**
 * This will collate the location details by collecting the location information.
 * 
 * @author Jay
 */
public class LocationCollateProcessor implements Processor<DefaultLocation, DefaultLocation>{
	
	protected static Logger LOG = LoggerFactory.getLogger(LocationCollateProcessor.class);
	private @Autowired CountryStore persistedCountryMongoStore;
	private @Autowired StateStore persistedStateMongoStore;
	private @Autowired CityStore persistedCityMongoStore;
	private @Autowired LocalityStore persistedLocalityMongoStore;
	private @Autowired LocationStore persistedLocationMongoStore;

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public DefaultLocation invoke(DefaultLocation loc) throws ApplicationException {
		LOG.info("[START]- Collating the location information.");
		Country savedCountry = getCountry(loc.getCountryId());
		State savedState = getState(loc.getStateId());
		City savedCity = getCity(loc.getCityId());
		Locality savedLocality = getLocality(loc.getLocalityId());
		LOG.info("[END]- Collating the location information.");
		//Finally return with updated value
		return createLocationInstance(loc, savedCountry, savedState, savedCity, savedLocality);
	}
	
	/**
	 * Retrieves the {@link Country} based on the country id
	 * @param country_id
	 * @return
	 */
	private Country getCountry(String country_id){
		Country savedCountry = null;
		try{
			if(country_id != null)
				savedCountry = persistedCountryMongoStore.getById(country_id);
		}catch(RecordNotFoundException e){
			LOG.warn("[MINOR]- Country not found");
		}
		return savedCountry;
	}
	
	
	/**
	 * Get the saved state by name or short name.
	 * @param state
	 * @return
	 */
	private State getState(String state_id){
		State savedState = null;
		try{
			if(state_id != null)
				savedState = persistedStateMongoStore.getById(state_id);
		}catch(RecordNotFoundException e){
			LOG.warn("[MINOR]- State not found");
		}
		return savedState;
	}
	
	
	/**
	 * Gets the city from the store.
	 * @param city
	 * @return
	 */
	private City getCity(String city_id){
		City savedCity = null;
		try{
			if(city_id != null)
				savedCity = persistedCityMongoStore.getById(city_id);
		}catch(RecordNotFoundException e){
			LOG.warn("[MINOR]- City not found");
		}
		return savedCity;
	}
	
	
	/**
	 * Gets the locality from the saved details.
	 * @param locality
	 * @return
	 */
	private Locality getLocality(String locality_id){
		Locality savedLocality = null;
		try{
			if(locality_id != null)
				savedLocality = persistedLocalityMongoStore.getById(locality_id);
		}catch(RecordNotFoundException e){
			LOG.warn("[MINOR]- Locality not found");
		}
		return savedLocality;
	}
	
	/**
	 * @param old
	 * @param country
	 * @param state
	 * @param city
	 * @param locality
	 * @return
	 */
	private DefaultLocation createLocationInstance(DefaultLocation old, Country country, State state, City city, Locality locality) {
		LocationBuilder builder = new DefaultLocation.LocationBuilder(old.getLocationId());
		builder.updateLongLat(old.getLocation());
		builder.updateName(old.getName());
		if(country != null)
			builder.updateCountry(old.getCountry());
		if(state != null)
			builder.updateState(state);
		if(city != null)
			builder.updateCity(city);
		if(locality != null)
			builder.updateLocality(locality);
		if(old.getPin() != null)
			builder.updatePin(old.getPin());
		builder.updateLocationType(old.getType());
		return builder.build();
	}
}
