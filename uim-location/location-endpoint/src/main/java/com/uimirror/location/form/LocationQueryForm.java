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

import com.uimirror.core.BooleanUtil;
import com.uimirror.core.GeoLongLat;
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
	@QueryParam(LocationQueryVariables.LONGITUDE)
	private String longitude;
	@QueryParam(LocationQueryVariables.LATITUDE)
	private String latitude;
	
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

	public String getLongitude() {
		return longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	/**
	 * Gets the longitude and latitude of the given query,
	 * if no valid details present then <code>null</code>
	 * @return
	 */
	public GeoLongLat getLongLatQuery(){
		GeoLongLat geo = null;
		try{
			geo = new GeoLongLat.GeoLongLatBuilder(null).updateLongitude(Double.parseDouble(longitude)).updateLatitude(Double.parseDouble(latitude)).build();
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

}
