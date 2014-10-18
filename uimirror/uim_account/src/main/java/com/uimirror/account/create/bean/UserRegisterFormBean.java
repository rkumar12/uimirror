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
package com.uimirror.account.create.bean;

import javax.ws.rs.FormParam;

import com.uimirror.account.create.RegisterConstants;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.bean.form.ClientMetaForm;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Populates the parameters passed as request for registering a user
 * @author Jay
 */
public class UserRegisterFormBean extends ClientMetaForm  implements BeanValidatorService{

	private static final long serialVersionUID = 4050333181389188686L;

	public UserRegisterFormBean() {
	}
	
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

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		//Not null of each field
		//Date of birth is in proper format
		//Check for the proper format of names, validate email etc
		//make use Appache validator
		//Make sure age is more than 18
		//send exception message in case of validation failure
		return false;
	}

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
	
	

}
