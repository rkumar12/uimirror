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
package com.uimirror.account.auth.user.bean.form;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import com.uimirror.account.auth.user.Approval;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.bean.form.AuthenticatedHeaderForm;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Converts the {@link FormParam} provided in the POST request for the
 * authentication purpose from the two factor authentication form.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications or from the client
 * 
 * @author Jay
 */
public final class AuthorizeClientAuthenticationForm extends AuthenticatedHeaderForm implements BeanValidatorService {

	private static final long serialVersionUID = -1215523730014366150L;
	
	@QueryParam(AuthConstants.SCOPE)
	private String scope;
	@QueryParam(AuthConstants.APPROVAL)
	private String approval;

	public Scope getScope() {
		if(scope != null)
			return Scope.getEnum(scope);
		return null;
	}

	public Approval getApproval() {
		if(approval != null)
			return Approval.getEnum(approval);
		return null;
	}

	@Override
	public String toString() {
		return "AuthorizeClientAuthenticationForm [scope=" + scope
				+ ", approval=" + approval + "]";
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
		if(getScope() == null)
			informIllegalArgument("Scope of this approval should present");
		if(getApproval() == null)
			informIllegalArgument("Approval Flag Should Present");
	}
	
	private void informIllegalArgument(String msg){
		throw new IllegalArgumentException(msg);
	}
}
