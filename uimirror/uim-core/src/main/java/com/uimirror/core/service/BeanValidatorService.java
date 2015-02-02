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
package com.uimirror.core.service;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Validates a bean by choosing if the populated details
 * in the bean are correct or not.
 * @author Jay
 */
public interface BeanValidatorService {

	@ApiModelProperty(value="valid", hidden=true)
	boolean isValid();
}
