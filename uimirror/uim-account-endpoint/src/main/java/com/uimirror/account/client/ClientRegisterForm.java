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
package com.uimirror.account.client;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Constants;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.util.web.WebUtil;
import com.uimirror.ouath.client.form.ClientRegisterConstants;
import com.uimirror.sso.AuthenticatedPrincipal;

/**
 * Converts the {@link FormParam} provided in the POST request for 
 * registering a client.
 * 
 * Screen will be directly pushed to the client from the uimirror or 
 * supportive applications
 * 
 * @author Jay
 */
public final class ClientRegisterForm extends AuthenticatedPrincipal implements BeanValidatorService{

	private static final long serialVersionUID = -1215523730014366150L;
	
	@FormParam(ClientRegisterConstants.NAME)
	private String name;
	
	@FormParam(ClientRegisterConstants.REDIRECT_URL)
	private String redirectURL;
	
	@FormParam(ClientRegisterConstants.APP_URL)
	private String appURL;
	
	@FormParam(ClientRegisterConstants.IMAGE)
	private String image;

	public String getName() {
		return name;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public String getAppURL() {
		return WebUtil.getURLDomain(appURL);
	}

	public String getImage() {
		return image;
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
		Set<String> fields = new HashSet<String>();
		Map<String, Object> errors = new LinkedHashMap<String, Object>(5);
		if(!StringUtils.hasText(getName()))
			fields.add(ClientRegisterConstants.NAME);
		if(!StringUtils.hasText(getRedirectURL()))
			fields.add(ClientRegisterConstants.REDIRECT_URL);
		if(!StringUtils.hasText(getAppURL()))
			fields.add(ClientRegisterConstants.APP_URL);
		if(!WebUtil.isValidAppAndRedirectURL(getAppURL(), getRedirectURL())){
			fields.add(ClientRegisterConstants.APP_URL);
			fields.add(ClientRegisterConstants.REDIRECT_URL);
		}
		if(fields.size() > 0){
			errors.put(Constants.FIELDS, fields);
			errors.put(Constants.MESSAGE, "Invalid Input");
			informIllegalArgument(errors);
		}
	}
	
	private void informIllegalArgument(Map<String, Object> errors){
		throw new IllegalArgumentException(errors);
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
