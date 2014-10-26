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
package com.uimirror.rtp.reaching.api.endpoint.shop;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * End point for the various user operation for the different category.
 * 
 * @author Jay
 */
@Path(RegisterShopEndPointConstants.SHOP+RegisterShopEndPointConstants.SHOP_CATEGORY)
@Singleton
public class ShopCategoryEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(ShopCategoryEndPoint.class);
	
	public ShopCategoryEndPoint() {
		//NOP
	}
	
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Object getAll(){
		LOG.info("[ENTRY]- Received requst for Shop types.");
		//Object response = registerShopProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for Shop types.");
		return "Hello";
	}

	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	public Object getSubCategoryId(@QueryParam("id") String categoryId, @QueryParam("level") String level){
		LOG.info("[ENTRY]- Received requst for getting sub shop types.");
		//Object response = registerShopProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for getting sub shop types.");
		return "Hello";
	}
	
}
