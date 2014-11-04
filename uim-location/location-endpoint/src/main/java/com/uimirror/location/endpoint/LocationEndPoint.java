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
package com.uimirror.location.endpoint;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.location.form.LocationQueryForm;

/**
 * <p>Location endpoint to handle all the query
 * regarding to the location endpoints such as get location by
 * location id, by location name, find by longitude and latitude etc.
 * </p>
 * @author Jay
 */
@Path(LocationEndPointConstant.HOME)
@Singleton
public class LocationEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(LocationEndPoint.class);
	private @Autowired Processor<LocationQueryForm, String> locationSearchLocator;
	
	public LocationEndPoint() {
		//NOP
	}
	
	/**
	 * This will process the location search based on the location and parameter specified 
	 * GET https://location.uimirror.com/location/123/?expanded=true
	 *	
	 * in case of successful location search it will respond with location details
	 * 
	 * response {
	 *	"name":"Marathahali, Bangalore",
	 *  "country" : {"_id" : "1","shortname" : "IN", "name" : "India", "code" :"91" },
	 *  "type"    : "street",
	 *  "cord"    : {"_id" : "1", "lat"   : "-31.876546", "long"    : "0.12345"}
	 *	}
	 * @param form
	 * @return
	 */
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	public Object getLocationById(@BeanParam LocationQueryForm form){
		LOG.info("[ENTRY]- Received request to search for the location by location id.");
		String res = locationSearchLocator.invoke(form);
		LOG.info("[EXIT]- Received request to search for the location by location id.");
		return res;
	}
	/**
	 * This will process the location search based on the location and parameter specified 
	 * GET https://location.uimirror.com/location?lon=2.1234&lat=-5.345&expanded=true
	 *	
	 * in case of successful location search it will respond with location details
	 * 
	 * response {
	 *	"name":"Marathahali, Bangalore",
	 *  "country" : {"_id" : "1","shortname" : "IN", "name" : "India", "code" :"91" },
	 *  "type"    : "street",
	 *  "cord"    : {"_id" : "1", "lat"   : "-31.876546", "long"    : "0.12345"}
	 *	}
	 * @param form
	 * @return
	 */
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Path(LocationEndPointConstant.LOOKUP)
	public Object getLocationBylatLong(@BeanParam LocationQueryForm form){
		LOG.info("[ENTRY]- Received request to search for the location.");
		String res = locationSearchLocator.invoke(form);
		LOG.info("[EXIT]- Received request to search for the location.");
		return res;
	}
	
}
