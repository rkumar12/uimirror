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
package com.uimirror.challenge.controller;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
/**
 * @author Jayaram
 *
 */
@Path("/")
@Component
@RolesAllowed({ "ADMIN"})
public class ChallengeController {

	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@JSONP(queryParam="cb", callback="callback")
	@RolesAllowed("ADMIN")
	public String getHello() {
		return new Gson().toJson("Hello JsonP");
	}
}
