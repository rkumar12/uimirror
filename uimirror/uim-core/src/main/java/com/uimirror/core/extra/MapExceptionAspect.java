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
package com.uimirror.core.extra;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.ExceptionMapper;
import com.uimirror.core.ExceptionMapperFactory;

/**
 * Any method that annote with {@link MapException} will be waved
 * and corresponding transformer will try to intercept the exception and 
 * return with appropriate business logic.
 * 
 * @author Jay
 */
@Aspect
public class MapExceptionAspect {

	private static final Logger LOG = LoggerFactory.getLogger(MapExceptionAspect.class);
	private @Autowired ExceptionMapperFactory exceptionMapperFactory;
	
	@AfterThrowing(pointcut="execution(* *(..)) && @annotation(mapException)", throwing="ex")
	public void mapperAfterThrowing(MapException mapException, Throwable ex) throws Throwable{
		String name = mapException.use();
		ExceptionMapper mapper = exceptionMapperFactory.getMapper(name);
		if(mapper != null){
			throw mapper.mapIt(ex);
		}
		LOG.error("[URGENT-EXCEPTION]- Exception weving encountring some problem, original excepion was {}", ex);
		throw ex;
	}
}
