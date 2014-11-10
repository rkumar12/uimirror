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
package com.uimirror.core.user;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.uimirror.core.service.BeanValidatorService;

/**
 * User's account logs statement such as when account created,
 * when modified, if any history needs to maintain then can be done here
 * @author Jay
 */
public class MetaInfo implements BeanValidatorService {
	
	private String locale;
	private String currency;
	private String timeZone;
	
	//Don't Use this until it has specific requirement
	public MetaInfo() {
		super();
	}
	
	public MetaInfo(Map<String, Object> details) {
		//super(details);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
