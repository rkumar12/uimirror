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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.uimirror.core.dao.RecordNotFoundException;
import com.uimirror.core.util.thread.AbstractBackgroundProcessor;
import com.uimirror.location.City;
import com.uimirror.location.Country;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.DefaultLocation.LocationBuilder;
import com.uimirror.location.Locality;
import com.uimirror.location.State;
import com.uimirror.location.store.CityStore;
import com.uimirror.location.store.CountryStore;
import com.uimirror.location.store.LocalityStore;
import com.uimirror.location.store.StateStore;

/**
 * @author Jay
 */
public class LocationStoreBackGroundProcessor extends AbstractBackgroundProcessor<DefaultLocation, DefaultLocation>{

	protected static final Logger LOG = LoggerFactory.getLogger(LocationStoreBackGroundProcessor.class);
	public static final String NAME = "LSP";
	private @Autowired CountryStore persistedCountryMongoStore;
	private @Autowired StateStore persistedStateMongoStore;
	private @Autowired CityStore persistedCityMongoStore;
	private @Autowired LocalityStore persistedLocalityMongoStore;
	
	public LocationStoreBackGroundProcessor() {
		super(4, Boolean.TRUE);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.BackgroundProcessor#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(DefaultLocation param) {
		LOG.debug("[START]- Storing the location Componets.");
		getAdaptor().submitTask(createJobs(param.getCountry(), param.getState(), param.getCity(), param.getLocality()));
		LOG.debug("[END]- Storing the location Componets.");
	}
	
	/**
	 * Creates the Job List
	 * @param country
	 * @return
	 */
	private List<Callable<Object>> createJobs(Country country, State state, City city, Locality locality){
		List<Callable<Object>> backgrounds = new ArrayList<Callable<Object>>();
		backgrounds.add(new SaveCountryInBackGround(country));
		backgrounds.add(new SaveStateInBackGround(state));
		backgrounds.add(new SaveCityInBackGround(city));
		backgrounds.add(new SaveLocalityInBackGround(locality));
		return backgrounds;
	}
	
	/**
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class SaveCountryInBackGround implements Callable<Object>{
		
		private final Country country;
		public SaveCountryInBackGround(Country country){
			this.country = country;
		}

		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Country call() throws Exception {
			return storeCountry(country);
		}
	}
	
	/**
	 * Store the {@link Country} if not present.
	 * 
	 * @param country to be persisted
	 * @return persisted {@link Country}
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
	 * @param country to find
	 * @return persisted {@link Country}
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
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class SaveStateInBackGround implements Callable<Object>{
		
		private final State state;
		public SaveStateInBackGround(State state){
			this.state = state;
		}

		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public State call() throws Exception {
			return storeState(state);
		}
	}
	
	/**
	 * First tries to find the locality, if not found save it.
	 * @param locality to be persisted
	 * @return persisted {@link State}
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
	 * Get the saved locality by name or short name.
	 * @param locality
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
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class SaveCityInBackGround implements Callable<Object>{
		
		private final City city;
		public SaveCityInBackGround(City city){
			this.city = city;
		}

		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public City call() throws Exception {
			return storeCity(city);
		}
	}
	
	/**
	 * Stores the locality if not found.
	 * @param locality
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
	 * Gets the locality from the store.
	 * @param locality
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
	 * From a task that can be submitted for background process
	 * @author Jay
	 */
	private class SaveLocalityInBackGround implements Callable<Object>{
		
		private final Locality locality;
		public SaveLocalityInBackGround(Locality locality){
			this.locality = locality;
		}

		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Locality call() throws Exception {
			return storeLocality(locality);
		}
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

	/* (non-Javadoc)
	 * @see com.uimirror.core.util.thread.BackgroundProcessor#getResult()
	 */
	@Override
	public DefaultLocation getResult() throws IllegalThreadStateException {
		Object[] results = getResults();
		Country country = null;
		State state = null;
		City city = null;
		Locality locality = null;
		for(int i = 0; i < 4; i++){
			Object obj = results[i];
			if(obj instanceof Country)
				country = (Country) obj;
			if(obj instanceof State)
				state = (State) obj;
			if(obj instanceof City)
				city = (City) obj;
			if(obj instanceof Locality)
				locality = (Locality) obj;
		}
		LocationBuilder build =  new DefaultLocation.LocationBuilder("");
		if(country != null)
			build.updateCountry(country);
		if(state != null)
			build.updateState(state);
		if(city != null)
			build.updateCity(city);
		if(locality != null)
			build.updateLocality(locality);
		return build.build();
	}

	

}
