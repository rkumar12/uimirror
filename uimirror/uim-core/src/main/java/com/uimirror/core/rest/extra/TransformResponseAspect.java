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
package com.uimirror.core.rest.extra;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Any method that annote with {@link TransformResponse} will be waved
 * and corresponding transformer will try to intercept the response and 
 * return with appropriate return type.
 * 
 * @author Jay
 */
@Aspect
public class TransformResponseAspect {

	private static final Logger LOG = LoggerFactory.getLogger(TransformResponseAspect.class);
	private @Autowired ResponseTransFormerFactory responseTransFormerFactory;

	@SuppressWarnings("rawtypes")
	@AfterReturning(pointcut="execution(* *(..)) && @annotation(transformResponse)", returning="retVal")
	public void mapperAfterThrowing(TransformResponse transformResponse, Object retVal){
		String name = transformResponse.by();
		ResponseTransFormer transformer = responseTransFormerFactory.getTransFormer(name);
		if(transformer == null)
			LOG.error("[HIGH-EXCEPTION]- Response Transformer weving encountring some problem.");
		retVal =  transformer.doTransForm(retVal);
	}

}
