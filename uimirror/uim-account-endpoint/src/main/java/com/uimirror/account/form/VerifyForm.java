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

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.form.AuthenticatedHeaderForm;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Converts the {@link FormParam} provided in the POST request for registering a user.
 * 
 * Screen will be directly pushed to the client from the uimirror or supportive applications
 * 
 * @author Jay
 */
public final class VerifyForm extends AuthenticatedHeaderForm implements BeanValidatorService {

  private static final long serialVersionUID = -1215523730014366150L;

  @FormParam(VerifyConstants.SOURCE)
  private String source;

  @FormParam(VerifyConstants.CODE)
  private String code;

  /*
   * (non-Javadoc)
   * 
   * @see com.uimirror.core.service.BeanValidatorService#isValid()
   */
  @Override
  public boolean isValid() {
    validate();
    return Boolean.TRUE;
  }

  private void validate() {
    if (!VerifySource.MAIL.getSource().equalsIgnoreCase(this.source)
        && !VerifySource.WEB.getSource().equalsIgnoreCase(this.source)) {
    	throw new IllegalArgumentException("Invalid Source");
    }
    if (StringUtils.isEmpty(getToken())) 
      informIllegalArgument("Invalid Token");
    
    if (VerifySource.WEB.getSource().equalsIgnoreCase(this.source) && !StringUtils.hasText(getCode())) 
    	informIllegalArgument("Invalid Code");
    
  }

  private void informIllegalArgument(String msg) {
    throw new IllegalArgumentException(msg);
  }

  public VerifySource getSource() {
    return VerifySource.getEnum(source);
  }

  public String getCode() {
    return code;
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
