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
package com.uimirror.auth.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import com.uimirror.core.Constants;
import com.uimirror.core.transformer.GsonResponseTransformer;

/**
 * Defines the common authentication controller behavior which is common 
 * for user as well different client
 * @author Jay
 */
public abstract class CommonAuthenticationController extends GsonResponseTransformer implements AuthenticationController{

	@Override
	public Response unAuthorizedResponse() {
		Map<String, String> rs = new LinkedHashMap<String, String>();
		rs.put(Constants.ERROR, "Not Authorized");
		return buildUnauthorizedResponse(rs);
	}
	
	/**
	 * <p>Helper to build the final unauthorized response</p>
	 * @param res
	 * @return
	 */
	private Response buildUnauthorizedResponse(Map<String, ? extends Object> res){
		return Response.status(Response.Status.UNAUTHORIZED).entity(doTransform(res)).build();
	}

}
