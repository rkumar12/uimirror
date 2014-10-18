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

import com.uimirror.account.create.bean.UserRegisterFormBean;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.bean.BasicUserDetails;

/**
 * @author Jay
 */
//TODO update java doc
public class RegisterFormToRegisterTransformer implements TransformerService<UserRegisterFormBean, BasicUserDetails>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public BasicUserDetails transform(UserRegisterFormBean src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		//Update proper object of BasicUserDetails
		return null;
	}

}
