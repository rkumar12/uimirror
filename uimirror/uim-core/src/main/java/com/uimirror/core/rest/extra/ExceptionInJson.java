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
package com.uimirror.core.rest.extra;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.owlike.genson.Genson;
import com.uimirror.core.Constants;

/**
 * Constructs the response with appropriate response code for the application exception
 * that needs to be transmitted to the user.
 * 
 * @author Jay
 */
public abstract class ExceptionInJson extends ApplicationException{

	private static final long serialVersionUID = 7633918125632783005L;

	/**
	 * <p>Initialize the exception response to be part of the response</p>
	 * @param status
	 * @param msg
	 */
	public ExceptionInJson(Status status, Object msg) {
		super(responseBuilder(status, msg));
	}
	/**
	 * <p>Builds invalid Response message</p>
	 * @return
	 */
	private static Response responseBuilder(Status status, Object msg) {
		Map<String, Object> rs = new LinkedHashMap<String, Object>();
		rs.put(Constants.ERROR, msg);
		return buildResponse(status, rs);
	}
	
	/**
	 * <p>Helper to build the final response with status code</p>
	 * @param res
	 * @return
	 */
	private static Response buildResponse(Status status, Map<String, ? extends Object> res){
		return Response.status(status).entity(new Genson().serialize(res)).build();
	}

}
