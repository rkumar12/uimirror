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
package com.uimirror.auth.user.processor;

import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.auth.bean.form.LoginSessionForm;
import com.uimirror.core.Processor;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;
import com.uimirror.core.rest.extra.ApplicationException;


/**
 * @author Jay
 */
public class LoginSessionProcessor implements Processor<LoginSessionForm>{

	private @Autowired Processor<AuthenticatedHeaderForm> accessTokenExtraProcessor;
	
	/**
	 * 
	 */
	public LoginSessionProcessor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.uimirror.auth.controller.Processor#invoke(java.lang.Object)
	 */
	@Override
	public Object invoke(LoginSessionForm param) throws ApplicationException {
		// TODO Auto-generated method stub
		accessTokenExtraProcessor.invoke(param);
		return null;
	}

}