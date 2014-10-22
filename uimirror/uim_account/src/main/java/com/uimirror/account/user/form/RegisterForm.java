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
import java.util.regex.Pattern;

import javax.ws.rs.FormParam;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;

import com.uimirror.account.create.RegisterConstants;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.form.ClientMetaForm;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.MessageUtil;

/**
 * Converts the {@link FormParam} provided in the POST request for 
 * registering a user.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications
 * 
 * @author Jay
 */
/**
 * @author damart1
 *
 */
public final class RegisterForm extends ClientMetaForm implements BeanValidatorService{

	private static final long serialVersionUID = -1215523730014366150L;
	private static final String FIELDS = "fields";
	private static final String MESSAGE = "message";
	private static final String NAME_REGEX_PATTERN = "[a-zA-Z0-9.\\-_]{3,}";

	@FormParam(AuthConstants.CLIENT_ID)
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
	
	
	
	/**
	 * Validates the fields for null values, validation for invalid names, 
	 * invalid email, validation for dob format and validation for age limit.
	 */
	private void validate(){
		List<String> fields = new ArrayList<String>();
		String ageLimitMessage = null;
		String errorMessage = null;
		if(!StringUtils.hasText(getFirstName()) || !isAValidName(getFirstName()))
			fields.add(RegisterConstants.FIRST_NAME);
		
		if(!StringUtils.hasText(getLastName())){
			if(StringUtils.hasText(getFirstName())){
				if(!isAValidName(getLastName())){
					fields.add(RegisterConstants.LAST_NAME);
				}
			}
			
		}
		
		if(!StringUtils.hasText(getEmail()) || !isAValidEmail(getEmail()))
			fields.add(RegisterConstants.EMAIl);
		if(!StringUtils.hasText(getGender().toString()))
			fields.add(RegisterConstants.GENDER);
		if(!StringUtils.hasText(getDateOfBirth()) || !DateTimeUtil.isAValidDate(getDateOfBirth()))
			fields.add(RegisterConstants.DATE_OF_BIRTH);
		else if  (!DateTimeUtil.isAgeAboveEighteen(getDateOfBirth()))
			ageLimitMessage=MessageUtil.getAgeLimitMessage();
				
		if(fields.size() > 0 ){
			Map<String, Object> errors = new LinkedHashMap<String, Object>(9);
			errors.put(FIELDS, fields);
			errorMessage = MessageUtil.getErrorMessage(fields);
			if(ageLimitMessage != null)
				errorMessage+= ageLimitMessage;
			errors.put(MESSAGE, errorMessage);
			informIllegalArgument(errors);
		}
	}
	
	/**
	 * Validates name pattern
	 * @param name
	 * @return boolean
	 */
	private boolean isAValidName(String name){
		 return Pattern.matches(NAME_REGEX_PATTERN, name);
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
		return "RegisterForm [clientId=" + clientId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=[*******], gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	

}
