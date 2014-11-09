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
package com.uimirror.ws.auth.form;

import javax.ws.rs.FormParam;

import org.springframework.util.StringUtils;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the screen locked login screen.
 * 
 * @author Jay
 */
public class ScreenLockAuthenticationForm extends AuthenticatedHeaderForm implements BeanValidatorService{

	private static final long serialVersionUID = -1268777827570961853L;

	@FormParam(AuthConstants.PASSWORD)
	private String password;

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "ScreenLockAuthenticationForm [password=" + password + "]";
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		super.isValid();
		validate();
		return Boolean.TRUE;
	}
	
	private void validate(){
		if(!StringUtils.hasText(getPassword()))
			informIllegalArgument("Password should be present");
	}
	
	private void informIllegalArgument(String msg){
		throw new IllegalArgumentException(msg);
	}
	
}
