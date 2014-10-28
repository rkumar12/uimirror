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
package com.uimirror.account.auth.user.transformer;

import org.springframework.util.Assert;

import com.uimirror.account.auth.user.form.AuthorizeClientAuthenticationForm;
import com.uimirror.core.service.TransformerService;
import com.uimirror.sso.client.ClientAuthorizationAuthentication;

/**
 * A transformer implementation which will transform the {@linkplain AuthorizeClientAuthenticationForm}
 * to {@linkplain ClientAuthorizationAuthentication}
 * @author Jay
 */
public class ClientAutorizeFormToAuthTransformer implements TransformerService<AuthorizeClientAuthenticationForm, ClientAuthorizationAuthentication>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public ClientAuthorizationAuthentication transform(AuthorizeClientAuthenticationForm src) {
		Assert.notNull(src, "Source Can't be empty");
		//Validate the form
		src.isValid();
		return new ClientAuthorizationAuthentication(src.getToken(),src.getScope(), src.getApproval(), src.getIp(), src.getUserAgent());
	}

}
