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
import org.springframework.util.StringUtils;

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

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public DefaultLocation invoke(DefaultLocation loc) throws ApplicationException {
		LOG.info("[START]- Storing the missing location for the cordinate.");
		Country savedCountry = storeCountry(loc.getCountry());
		State savedState = storeState(loc.getState());
		City savedCity = storeCity(loc.getCity());
		Locality savedLocality = storeLocality(loc.getLocality());
		//Create Instance to store
		DefaultLocation interMidetoryLoc =createLocationInstance(loc, savedCountry, savedState, savedCity, savedLocality); 
		DefaultLocation savedLoc = persistedLocationMongoStore.store(interMidetoryLoc);
		LOG.info("[END]- Storing the missing location for the cordinate.");
		//Finally return with updated value
		return createLocationInstance(savedLoc, savedCountry, savedState, savedCity, savedLocality);
	}

	/**
	 * Store the {@link Country} if not present.
	 * 
	 * @param country
	 * @return
	 */
	private Country storeCountry(Country country){
		if(country == null || (!StringUtils.hasText(country.getName()) && !StringUtils.hasText(country.getShortName()))){
			return null;
		}
		Country savedCountry = getCountry(country);
		if(savedCountry == null)
			savedCountry = persistedCountryMongoStore.store(country);
		return savedCountry;
	}
	
	/**
	 * Retrieves the {@link Country} based on the country name/ short name
	 * @param country
	 * @return
	 */
	private Country getCountry(Country country){
		Country savedCountry = null;
		if(StringUtils.hasText(country.getName())){
			try{
				savedCountry = persistedCountryMongoStore.getByName(country.getName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- Country not found");
			}
		}else if(StringUtils.hasText(country.getShortName())){
			try{
				savedCountry = persistedCountryMongoStore.getByShortName(country.getShortName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- Country not found");
			}
		}
		return savedCountry;
	}
	
	/**
	 * First tries to find the state, if not found save it.
	 * @param state
	 * @return
	 */
	private State storeState(State state){
		if(state == null || (!StringUtils.hasText(state.getName()) && !StringUtils.hasText(state.getShortName()))){
			return null;
		}
		State savedState = getState(state);
		if(savedState == null)
			savedState = persistedStateMongoStore.store(state);
		return savedState;
	}
	
	/**
	 * Get the saved state by name or short name.
	 * @param state
	 * @return
	 */
	private State getState(State state){
		State savedState = null;
		if(StringUtils.hasText(state.getName())){
			try{
				savedState = persistedStateMongoStore.getByName(state.getName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- State not found");
			}
		}else if(StringUtils.hasText(state.getShortName())){
			try{
				savedState = persistedStateMongoStore.getByShortName(state.getShortName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- State not found");
			}
		}
		return savedState;
	}
	
	/**
	 * Stores the city if not found.
	 * @param city
	 * @return
	 */
	private City storeCity(City city){
		if(city == null || (!StringUtils.hasText(city.getName()) && !StringUtils.hasText(city.getShortName()))){
			return null;
		}
		City savedCity = getCity(city);
		if(savedCity == null)
			savedCity = persistedCityMongoStore.store(city);
		return savedCity;
	}
	
	/**
	 * Gets the city from the store.
	 * @param city
	 * @return
	 */
	private City getCity(City city){
		City savedCity = null;
		if(StringUtils.hasText(city.getName())){
			try{
				savedCity = persistedCityMongoStore.getByName(city.getName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- City not found");
			}
		}else if(StringUtils.hasText(city.getShortName())){
			try{
				savedCity = persistedCityMongoStore.getByShortName(city.getShortName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- City not found");
			}
		}
		return savedCity;
	}
	
	/**
	 * Stores the locality details.
	 * @param locality
	 * @return
	 */
	private Locality storeLocality(Locality locality){
		if(locality == null || (!StringUtils.hasText(locality.getName()) && !StringUtils.hasText(locality.getShortName())))
			return null;
		Locality savedLocality = getLocality(locality);
		if(savedLocality == null)
			savedLocality = persistedLocalityMongoStore.store(locality);
		return savedLocality;
		
	}
	
	/**
	 * Gets the locality from the saved details.
	 * @param locality
	 * @return
	 */
	private Locality getLocality(Locality locality){
		Locality savedLocality = null;
		if(StringUtils.hasText(locality.getName())){
			try{
				savedLocality = persistedLocalityMongoStore.getByName(locality.getName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- Locality not found");
			}
		}else if(StringUtils.hasText(locality.getShortName())){
			try{
				savedLocality = persistedLocalityMongoStore.getByShortName(locality.getShortName());
			}catch(RecordNotFoundException e){
				LOG.warn("[MINOR]- Locality not found");
			}
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
