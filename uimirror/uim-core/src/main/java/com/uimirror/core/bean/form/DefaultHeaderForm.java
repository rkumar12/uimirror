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

import java.io.Serializable;

import javax.ws.rs.FormParam;

import com.uimirror.core.auth.bean.form.BasicAuthenticationForm;

/**
 * Converts the {@link FormParam} provided in the request for the
 * authentication purpose.
 * 
 * @author Jay
 */
public abstract class DefaultHeaderForm extends ClientMetaForm implements Serializable, BasicAuthenticationForm{

	private static final long serialVersionUID = -1215523730014366150L;
	
	@Override
	public String getIp() {
		return super.getIp();
	}

	@Override
	public String getUserAgent() {
		return super.getUserAgent();
	}
	
}
