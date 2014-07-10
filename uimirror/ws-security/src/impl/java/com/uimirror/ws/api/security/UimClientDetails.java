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
package com.uimirror.ws.api.security;

import java.io.Serializable;

/**
 * <p>Bean to hold all the client details of uimirror
 * <p>Class has been marked as immutable </p>
 * <code>{@link ClientDetails}</code>
 * @author Jayaram
 */
public final class UimClientDetails extends ClientDetails implements Serializable{


	private static final long serialVersionUID = 5424883781797485757L;

	/**
	 * @param id
	 * @param name
	 * @param applicationUrl
	 * @param timezone
	 * @param loacle
	 * @param currency
	 */
	public UimClientDetails(long id, String name, String applicationUrl,
			String timezone, String loacle, String currency) {
		super(id, name, applicationUrl, timezone, loacle, currency);
	}

	/**
	 * @param id
	 * @param name
	 * @param applicationUrl
	 */
	public UimClientDetails(long id, String name, String applicationUrl) {
		super(id, name, applicationUrl);
	}
	
	
	

	
}
