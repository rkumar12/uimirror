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

import com.uimirror.core.BooleanUtil;
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

	public String getLocationId() {
		return locationId;
	}
	
	public boolean isExpanded(){
		return BooleanUtil.parseBoolean(expanded);
	}

}
