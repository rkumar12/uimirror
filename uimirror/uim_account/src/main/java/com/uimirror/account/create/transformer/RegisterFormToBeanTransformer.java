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
package com.uimirror.account.create.transformer;

import org.springframework.util.Assert;

import com.uimirror.account.user.form.RegisterForm;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.user.bean.BasicUserInfo;

/**
 * @author Jay
 */
//TODO update java doc
public class RegisterFormToBeanTransformer implements TransformerService<RegisterForm, Object>{


	
	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 * Transforms the RegisterForm object to BasicUserDetails
	 */
	@Override
	public Object transform(RegisterForm src) {
		Assert.notNull(src, "Source Can't be empty");
		/*Validate the form fields*/
		src.isValid();
		//TODO: check the accountstatus ,accountstate fields and details
		//
		
		
		return null;
	}

}
