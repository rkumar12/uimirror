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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;

import com.uimirror.account.auth.client.form.ClientAPIForm;
import com.uimirror.account.auth.user.Password;
import com.uimirror.core.Constants;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.MessageUtil;
import com.uimirror.core.util.StringRegexUtil;

/**
 * Converts the {@link FormParam} provided in the POST request for 
 * registering a user.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications
 * 
 * @author Jay
 */
public final class RegisterForm extends ClientAPIForm implements BeanValidatorService{

	private static final long serialVersionUID = -1215523730014366150L;
	
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
		return StringUtils.capitalize(firstName);
	}

	public String getLastName() {
		return StringUtils.capitalize(lastName);
	}
	
	public String getName(){
		return getFirstName()+Constants.SINGLE_SPACE+getLastName();
	}

	public String getEmail() {
		return email;
	}

	public Password getPassword() {
		return new Password(password, null).getEncrypted();
	}

	public Gender getGender() {
		return Gender.getEnum(gender);
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		validate();
		return Boolean.TRUE;
	}
	
	/**
	 * Validates the fields for null values, validation for invalid names, 
	 * invalid email, validation for dob format and validation for age limit.
	 */
	private void validate(){
		List<String> fields = new ArrayList<String>();
		String ageLimitMessage = null;
		String errorMessage = null;
		//matches the Name first
		if(!StringUtils.hasText(getFirstName()) || !StringRegexUtil.isValidName(getFirstName()))
			fields.add(RegisterConstants.FIRST_NAME);
		//TODO last name logic should be if first name present and last name not then don't do anything
		if(!StringUtils.hasText(getLastName()))
			fields.add(RegisterConstants.LAST_NAME);
		//EMail Match
		if(!StringUtils.hasText(getEmail()) || ! isAValidEmail(getEmail()))
			fields.add(RegisterConstants.EMAIl);
		//Gender match
		if(!StringUtils.hasText(gender) || getGender() == null)
			fields.add(RegisterConstants.GENDER);
		//DOB
		if(!StringUtils.hasText(getDateOfBirth()) || !DateTimeUtil.isAValidDate(getDateOfBirth()))
			fields.add(RegisterConstants.DATE_OF_BIRTH);
			//Check Age limit
		else if(!DateTimeUtil.isAgeAboveEighteen(getDateOfBirth()))
			ageLimitMessage=MessageUtil.getAgeLimitMessage();
		if(!StringUtils.hasText(password))
			fields.add(RegisterConstants.PASSWORD);
		else if(!StringRegexUtil.isPasswordFollowingThePolicy(password))
			fields.add(RegisterConstants.PASSWORD);
				
		if(fields.size() > 0 ){
			Map<String, Object> errors = new LinkedHashMap<String, Object>(9);
			errors.put(Constants.FIELDS, fields);
			errorMessage = MessageUtil.getErrorMessage(fields);
			if(ageLimitMessage != null)
				errorMessage+= ageLimitMessage;
			if(fields.contains(RegisterConstants.PASSWORD))
				errorMessage+= "Password must be 6 Charecter long with out any space.";
			errors.put(Constants.MESSAGE, errorMessage);
			informIllegalArgument(errors);
		}
	}
	
	
	/**
	 * Validates an email
	 * @param email
	 * @return boolean
	 */
	private boolean isAValidEmail(String email) {
		EmailValidator emailValidator=EmailValidator.getInstance(true);
		  return emailValidator.isValid(email) ;
	}
	
	/**
	 * Throws the exception map object
	 * @param msg
	 */
	private void informIllegalArgument(Map<String, Object> msg){
		throw new IllegalArgumentException(msg);
	}

	@Override
	public String toString() {
		return "RegisterForm [firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=[*******], gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}

}
