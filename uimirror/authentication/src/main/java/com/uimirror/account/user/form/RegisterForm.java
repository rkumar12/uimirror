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
package com.uimirror.account.user.form;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.form.ClientMetaForm;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Converts the {@link FormParam} provided in the POST request for 
 * registering a user.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications
 * 
 * @author Jay
 */
public final class RegisterForm extends ClientMetaForm implements BeanValidatorService{

	private static final long serialVersionUID = -1215523730014366150L;

	@QueryParam(AuthConstants.CLIENT_ID)
	private String clientId;
	
	@FormParam(RegisterConstants.FIRST_NAME)
	private String firstName;
	
	@FormParam(RegisterConstants.LAST_NAME)
	private String lastName;
	
	@FormParam(RegisterConstants.EMAIl)
	private String email;
	
	@FormParam(RegisterConstants.PASSWORD)
	private String password;
	
	@FormParam(RegisterConstants.GENDER)
	private String gender;
	
	@FormParam(RegisterConstants.DATE_OF_BIRTH)
	private String dateOfBirth;
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Gender getGender() {
		return Gender.getEnum(gender);
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getClientId() {
		return clientId;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		validate();
		return Boolean.TRUE;
	}
	
	private void validate(){
//		if(!StringUtils.hasText(getPassword()))
//			informIllegalArgument("Password should be present");
//		if(!StringUtils.hasText(getUserId()))
//			informIllegalArgument("User Id Should present");
	}
	
	private void informIllegalArgument(String msg){
		throw new IllegalArgumentException(msg);
	}

	@Override
	public String toString() {
		return "RegisterForm [clientId=" + clientId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=[*******], gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	

}
