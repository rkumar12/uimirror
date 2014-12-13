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
package com.uimirror.reach.web.core.shop;

import java.util.Set;

import com.uimirror.core.GeoLongLat;
import com.uimirror.reach.web.core.RetailProduct;

/**
 * @author Jay
 */
public class Shop {

	private String shopId;
	private String name;
	private String image;
	private RetailProduct productType;
	//should have an shop category like shop, chained or main branch
	private String details;
	private String address;
	private String contactNo;
	private GeoLongLat longLat;
	private String shopOwnerId;
	private Set<String> admins;
	//Shop type should be there to show for the multi store, dress etc.
	private boolean verified;
	private int numberOfPromo;
	private boolean onGoingPromo;
	
	

}
