///*******************************************************************************
// * Copyright (c) 2014 Uimirror.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Uimirror license
// * which accompanies this distribution, and is available at
// * http://www.uimirror.com/legal
// *
// * Contributors:
// * Uimirror Team
// *******************************************************************************/
//package com.uimirror.ws.api.security.util;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.ws.rs.core.Response;
//
//import com.owlike.genson.Genson;
//
///**
// * @author Jay
// */
//public final class SecurityResponseUtil {
//
//	protected static final String _ERROR = "error";
//	protected static final String _INVALID_TOKEN = "invalid_token";
//	protected static final String _INVALID_CLIENT = "invalid_client";
//	protected static final String _INVALID_USER = "invalid_user";
//	protected static final String _UNAUTHORIZED_USER = "user don't have sufficent permission";
//	protected static final String _UNAUTHORIZED_USER_LICENSE = "user don't have license";
//	protected static final String _UNAUTHORIZED_CLIENT_LICENSE = "client don't have license";
//	
//	/**
//	 * <p>This will build the token invalid response</p>
//	 * <p>Sets the response code as 400, bad request</p>
//	 * @return {@link Response}
//	 */
//	public static final Response buildTokenInvalidResponse(){
//        Map<String, String> res = new LinkedHashMap<String, String>(2);
//        res.put(_ERROR, _INVALID_TOKEN);
//        return buildBadResponse(res);
//	}
//	
//	/**
//	 * <p>This will build the client invalid response</p>
//	 * <p>Sets the response code as 400, bad request</p>
//	 * @return {@link Response}
//	 */
//	public static final Response buildClientInvalidResponse(){
//		Map<String, String> res = new LinkedHashMap<String, String>(2);
//        res.put(_ERROR, _INVALID_CLIENT);
//        return buildBadResponse(res);
//	}
//	/**
//	 * <p>This will build the user invalid response</p>
//	 * <p>Sets the response code as 400, bad request</p>
//	 * @return {@link Response}
//	 */
//	public static final Response buildUserInvalidResponse(){
//		Map<String, String> res = new LinkedHashMap<String, String>(2);
//        res.put(_ERROR, _INVALID_USER);
//        return buildBadResponse(res);
//	}
//	
//	/**
//	 * <p>This will build the user unauthorized response</p>
//	 * <p>Sets the response code as 401, unauthorized request</p>
//	 * @return {@link Response}
//	 */
//	public static final Response buildUserNotInRoleResponse(){
//		Map<String, String> res = new LinkedHashMap<String, String>(2);
//        res.put(_ERROR, _UNAUTHORIZED_USER);
//        return buildUnauthorizedResponse(res);
//	}
//	
//	/**
//	 * <p>This will build the user unauthorized license response </p>
//	 * <p>Sets the response code as 401, unauthorized request</p>
//	 * @return {@link Response}
//	 */
//	public static final Response buildUserNotHasLicenseResponse(){
//		Map<String, String> res = new LinkedHashMap<String, String>(2);
//        res.put(_ERROR, _UNAUTHORIZED_USER_LICENSE);
//        return buildUnauthorizedResponse(res);
//	}
//	
//	/**
//	 * <p>This will build the client unauthorized license response </p>
//	 * <p>Sets the response code as 401, unauthorized request</p>
//	 * @return {@link Response}
//	 */
//	public static final Response buildClientNotHasLicenseResponse(){
//		Map<String, String> res = new LinkedHashMap<String, String>(2);
//        res.put(_ERROR, _UNAUTHORIZED_CLIENT_LICENSE);
//        return buildUnauthorizedResponse(res);
//	}
//	
//	/**
//	 * <p>Helper to build the final bad response</p>
//	 * @param res
//	 * @return
//	 */
//	private static Response buildBadResponse(Map<String, ? extends Object> res){
//		return Response.status(Response.Status.BAD_REQUEST).entity(new Genson().serialize(res)).build();
//	}
//	
//	/**
//	 * <p>Helper to build the final unauthorized response</p>
//	 * @param res
//	 * @return
//	 */
//	private static Response buildUnauthorizedResponse(Map<String, ? extends Object> res){
//		return Response.status(Response.Status.UNAUTHORIZED).entity(new Genson().serialize(res)).build();
//	}
//
//}
