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
package com.uimirror.ws.api.audit.common;

/**
 * <p>This contains the constant for the audit fields</p>
 * @author Jayaram
 */
public interface AuditFieldConstants {

	public static final String _GTE = "$gte";
	//Common Fields
	public static final String _ID = "_id";
	
	public static final String _CLIENT_ID = "client";
	public static final String _RESOURCE = "resource";
	public static final String _STATUS = "status";
	public static final String _RQ_TIME = "rq_time";
	public static final String _RS_TIME = "rs_time";
}
