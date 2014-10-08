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

import com.uimirror.auth.bean.Authentication;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;
import com.uimirror.core.service.ValidatorService;

/**
 * Extracts the required details and form the {@link Authentication} object 
 * using {@link FormBasedAuthentication}
 * 
 * {@link AuthConstants#extractAuthParam(BasicAuthenticationForm)} will extract the
 * {@link Authentication} object performing {@link ValidatorService#validate(Object)}
 * 
 * @author Jay
 */
public abstract class DefaultAuthParamextractor extends DefaultHeaderValidator implements AuthConstants{

	
	/* (non-Javadoc)
	 * @see com.uimirror.core.ValidatorService#doMatch(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean doMatch(Object src, Object des) {
		//Really Nothing to implement
		return Boolean.FALSE;
	}

}
