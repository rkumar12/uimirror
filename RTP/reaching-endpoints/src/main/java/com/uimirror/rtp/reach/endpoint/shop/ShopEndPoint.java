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
package com.uimirror.rtp.reach.endpoint.shop;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.ws.api.security.annotation.PreAuthorize;

/**
 * End point for the various user operation for the shop registration.
 * 
 * @author Jay
 */
@Path(RegisterShopEndPointConstants.SHOP)
@Singleton
public class ShopEndPoint{

	private static Logger LOG = LoggerFactory.getLogger(ShopEndPoint.class);
	//private @Autowired Processor<ShopRegisterForm, String> registerShopProcessor;
	
	
	public ShopEndPoint() {
		//NOP
	}
	
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(RegisterShopEndPointConstants.REGISTER)
	@PreAuthorize
	public Object create(){
		LOG.info("[ENTRY]- Received requst for Shop Registration.");
		//Object response = registerShopProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for Shop Registration.");
		return "Hello";
	}

	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@PreAuthorize
	public Object getById(@QueryParam("id") String shopID){
		LOG.info("[ENTRY]- Received requst for Reteriving shop by ID.");
		//Object response = registerShopProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for Reteriving shop by ID.");
		return "Hello";
	}

	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@PreAuthorize
	public Object getAllManaged(){
		LOG.info("[ENTRY]- Received requst for Reteriving All Shop.");
		//Object response = registerShopProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for Reteriving All Shop.");
		return "Hello";
	}

	@DELETE
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@PreAuthorize
	public Object deleteById(){
		LOG.info("[ENTRY]- Received requst for Reteriving All Shop.");
		//Object response = registerShopProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for Reteriving All Shop.");
		return "Hello";
	}

	@PUT
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@PreAuthorize
	public Object updateById(){
		LOG.info("[ENTRY]- Received requst for Reteriving All Shop.");
		//Object response = registerShopProcessor.invoke(form);
		LOG.info("[EXIT]- Received requst for Reteriving All Shop.");
		return "Hello";
	}
	
	
}
