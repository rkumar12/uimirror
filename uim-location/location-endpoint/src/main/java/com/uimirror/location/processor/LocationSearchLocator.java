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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.uimirror.core.GeoLongLat;
import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.form.LocationQueryForm;

/**
 * This will be main processor to divide the task, whether to get it via longitude/latitude or location ID
 * @author Jay
 */
public class LocationSearchLocator implements Processor<LocationQueryForm, String>{
	private static final Logger LOG = LoggerFactory.getLogger(LocationSearchLocator.class);
	private @Autowired Processor<GeoLongLat, DefaultLocation> locationSearchByLatLong;
	private @Autowired Processor<String, DefaultLocation> locationSearchById;
	private @Autowired Processor<DefaultLocation, DefaultLocation> locationStoreProcessor;
	private @Autowired Processor<DefaultLocation, DefaultLocation> locationCollateProcessor;
	private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;
	

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public String invoke(LocationQueryForm form) throws ApplicationException {
		LOG.info("[START]- Getting the location details based on the provided query");
		Map<String, Object> res = null;
		DefaultLocation loc = getLocation(form);
		loc = storeIfRequired(loc);
		if(form.isExpanded()){
			//Check If collate required
			loc = getCollateLoc(loc);
			res = loc.getExpandedLoc();
		}else{
			res = loc.getShortLoc();
		}
		LOG.info("[END]- Getting the location details based on the provided query");
		return jsonResponseTransFormer.doTransForm(res);
	}

	/**
	 * This will get the {@link DefaultLocation} from either of the one source.
	 * @param form
	 * @return
	 */
	private DefaultLocation getLocation(LocationQueryForm form) {
		DefaultLocation loc = null;
		if(StringUtils.hasText(form.getLocationId()))
			loc = locationSearchById.invoke(form.getLocationId());
		else
			loc = locationSearchByLatLong.invoke(form.getLongLatQuery());
		return loc;
	}
	
	/**
	 * Location was retrieved from the google, so store it.
	 * @param loc
	 * @return
	 */
	private DefaultLocation storeIfRequired(DefaultLocation loc) {
		if(loc.getLocationId() == null){
			LOG.info("[SINGLE]- Location was not found in store and reterived from google, so saved it for further reference");
			loc = locationStoreProcessor.invoke(loc);
		}
		return loc;
	}
	
	/**
	 * Gets the collate location if required
	 * @param loc
	 * @return
	 */
	private DefaultLocation getCollateLoc(DefaultLocation loc){
		if(loc.isCollateRequired()){
			loc = locationCollateProcessor.invoke(loc);
		}
		return loc;
	}

}
