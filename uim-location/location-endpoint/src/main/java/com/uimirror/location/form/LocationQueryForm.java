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
package com.uimirror.location.form;

import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.uimirror.core.BooleanUtil;
import com.uimirror.core.GeoLongLat;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.ws.api.security.AuthenticatedPrincipal;

/**
 * Holds all the location query that are expect to come from the caller side.
 * It has the caller how made request, if the caller is passed from the security filter then allowed to 
 * perform the operation he is looking at.
 * 
 * @author Jay
 */
public class LocationQueryForm extends AuthenticatedPrincipal{

	private static final long serialVersionUID = -8392332379796658783L;
	private static final Logger LOG = LoggerFactory.getLogger(LocationQueryForm.class);
	//These things required to query by the location name
	@QueryParam(LocationQueryVariables.QUERY)
	private String locationName;
	@QueryParam(LocationQueryVariables.EXACT)
	private String exactSearchFlag;
	@QueryParam(LocationQueryVariables.LIMIT)
	private String limit;
	
	//by longitude and latitude
	@QueryParam(LocationQueryVariables.LOCATION)
	private String location;
	
	//By Location LOCATION_ID
	@QueryParam(LocationQueryVariables.LOCATION_ID)
	private String locationId;
	
	//Says if caller want the country, state, locality level information or only location
	@QueryParam(LocationQueryVariables.EXPANDED)
	private String expanded;

	public String getLocationName() {
		return locationName;
	}

	public boolean getExactSearchFlag() {
		return BooleanUtil.parseBoolean(exactSearchFlag);
	}

	public String getLimit() {
		return limit;
	}

	public String getLocation() {
		return location;
	}

	/**
	 * Gets the longitude and latitude of the given query,
	 * if no valid details present then <code>null</code>
	 * Expects Longitude,latitude
	 * @return
	 */
	public GeoLongLat getLongLatQuery(){
		
		GeoLongLat geo = null;
		//Validate the provided numbers first
		if(!StringUtils.hasText(location)){
			throw new IllegalArgumentException();
		}
		String[] locs = location.split(",");
		if(locs.length <= 0){
			throw new IllegalArgumentException();
		}
		try{
			geo = new GeoLongLat.GeoLongLatBuilder(null).updateLongitude(Double.parseDouble(locs[0])).updateLatitude(Double.parseDouble(locs[1])).build();
		}catch(NumberFormatException e){
			LOG.warn("Parsing Longitude and latitude are invalid.");
		}
		return geo;
	}

	public String getLocationId() {
		return locationId;
	}
	
	public boolean isExpanded(){
		return BooleanUtil.parseBoolean(expanded);
	}

	@Override
	public String toString() {
		return "LocationQueryForm [limit=" + limit + ", location(Longitude,Latitude)=" + location
				+ ", locationId=" + locationId + ", expanded=" + expanded + "]";
	}

}
