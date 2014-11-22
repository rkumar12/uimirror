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
package com.uimirror.account.endpoint;

/**
 * This contains all the URL path for the account end points.
 * @author Jay
 */
public class ClientEndPointConstant {
	private ClientEndPointConstant(){
		//NOP prevent insatance
	}
	public static final String HOME = "/";
	public static final String CLIENT = HOME+"client";
	public static final String CREATE = "/create";
}
