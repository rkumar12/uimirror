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
package com.uimirror.rtp.reach.endpoint;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;

import org.springframework.util.StringUtils;

import com.uimirror.core.Constants;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.rtp.shop.form.RegisterShopConstants;
import com.uimirror.ws.api.security.AuthenticatedPrincipal;

/**
 * Form bean for the Shop registration by the user
 * @author Jay
 */
public class ShopRegisterForm extends AuthenticatedPrincipal implements BeanValidatorService {

	private static final long serialVersionUID = -1215523730014366150L;

	@FormParam(RegisterShopConstants.SHOP_NAME)
	private String shopName;

	@FormParam(RegisterShopConstants.LATITUDE)
	private String latitude;

	@FormParam(RegisterShopConstants.LONGITUDE)
	private String longitude;

	@FormParam(RegisterShopConstants.SHOP_CONTACT_NUMBER)
	private String shopContactNumber;

	public String getShopName() {
		return shopName;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getShopContactNumber() {
		return shopContactNumber;
	}

	@Override
	public boolean isValid() {
		validate();
		return Boolean.TRUE;
	}

	private void validate() {
		Set<String> fields = new HashSet<String>();
		Map<String, Object> errors = new LinkedHashMap<String, Object>(7);
		if (!StringUtils.hasText(getLatitude()))
			fields.add(RegisterShopConstants.LATITUDE);
		if (!StringUtils.hasText(getLongitude()))
			fields.add(RegisterShopConstants.LONGITUDE);
		if (!StringUtils.hasText(getShopName()))
			fields.add(RegisterShopConstants.SHOP_NAME);
		if (!StringUtils.hasText(getShopContactNumber()))
			fields.add(RegisterShopConstants.SHOP_CONTACT_NUMBER);
		
		if (fields.size() > 0) {
			errors.put(Constants.FIELDS, fields);
			errors.put(Constants.MESSAGE, "Invalid Input");
			informIllegalArgument(errors);
		}
	}

	/**
	 * Throws the exception map object
	 * 
	 * @param msg
	 */
	private void informIllegalArgument(Map<String, Object> msg) {
		throw new IllegalArgumentException(msg);
	}

}
