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
package com.uimirror.account.form;

import static com.uimirror.account.form.RegisterConstants.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;

import com.uimirror.account.core.Password;
import com.uimirror.core.Constants;
import com.uimirror.core.bean.Gender;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.MessageUtil;
import com.uimirror.core.util.StringRegexUtil;
import com.uimirror.sso.form.ClientAPIForm;

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
	
	@FormParam(FIRST_NAME)
	private String firstName;
	
	@FormParam(LAST_NAME)
	private String lastName;
	
	@FormParam(EMAIl)
	private String email;
	
	@FormParam(PASSWORD)
	private String password;
	
	@FormParam(GENDER)
	private String gender;
	
	@FormParam(DATE_OF_BIRTH)
	private String dateOfBirth;
	
	@QueryParam(LANGUAGE)
	private String language;
	
	@QueryParam(COUNTRY_CODE)
	private String countryCode;
	
	@QueryParam(TIMEZONE)
	private String timeZone;
	
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

	public String getLanguage() {
		return language;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getTimeZone() {
		return timeZone;
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
		//Check Meta Info before starting
		if(!StringUtils.hasText(getTimeZone()))
			fields.add(TIMEZONE);
		//matches the Name first
		if(!StringUtils.hasText(getFirstName()) || !StringRegexUtil.isValidName(getFirstName()))
			fields.add(FIRST_NAME);
		/*If last name is null then checks for the first name.*/
		if(StringUtils.hasText(getLastName())){
			if(!StringRegexUtil.isValidName(getLastName()))
					fields.add(LAST_NAME);
		}else{
			if(!fields.contains(FIRST_NAME)){
				fields.add(LAST_NAME);
			}
		}
		//EMail Match
		if(!StringUtils.hasText(getEmail()) || ! isAValidEmail(getEmail()))
			fields.add(EMAIl);
		//Gender match
		if(!StringUtils.hasText(gender) || getGender() == null)
			fields.add(GENDER);
		//DOB
		if(!StringUtils.hasText(getDateOfBirth()) || !DateTimeUtil.isAValidDate(getDateOfBirth()))
			fields.add(DATE_OF_BIRTH);
			//Check Age limit
		else if(!DateTimeUtil.isAgeAboveEighteen(getDateOfBirth(), null))
			ageLimitMessage=MessageUtil.getAgeLimitMessage();
		if(!StringUtils.hasText(password))
			fields.add(PASSWORD);
		else if(!StringRegexUtil.isPasswordFollowingThePolicy(password))
			fields.add(PASSWORD);
				
		if(fields.size() > 0 ){
			Map<String, Object> errors = new LinkedHashMap<String, Object>(9);
			errors.put(Constants.FIELDS, fields);
			errorMessage = MessageUtil.getErrorMessage(fields);
			if(ageLimitMessage != null)
				errorMessage+= ageLimitMessage;
			if(fields.contains(PASSWORD))
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
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}

}
