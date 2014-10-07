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
package com.uimirror.core.bean.form;

import com.uimirror.core.auth.bean.Token;

/**
 * Form that will be initiated during any incoming call
 * for any request those should have these things.
 * 
 * @author Jay
 */
public interface AuthenticatedRequestParam{
	
	/**
	 * If a request has the token in the header or query parameter it will detect those
	 * without a proper token header/query parameter user will not be allowed for the further actions.
	 * It will apply de-cryption, and return the raw token 
	 * @return
	 */
	public Token getToken();

}
