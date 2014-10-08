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

import org.springframework.util.StringUtils;

import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;
import com.uimirror.core.extra.DefaultValidator;

/**
 * Validates the common header parameters passed into this.
 * 
 * @author Jay
 */
public class DefaultHeaderValidator extends DefaultValidator{
	
	/**
	 * Checks for the not null, ip and userAgent for the passed object.
	 * @param src
	 * @return
	 */
	public boolean headerValidator(BasicAuthenticationForm src){
		//Not Null
		boolean valid = Boolean.TRUE;
		valid = isNotNull(src);
		//Must have IP
		if(!valid && !StringUtils.hasText(src.getIp()))
			valid =  Boolean.FALSE;
		//Must have user Agent
		if(!valid && !StringUtils.hasText(src.getUserAgent()))
			valid = Boolean.FALSE;
		return valid;
	}

}
