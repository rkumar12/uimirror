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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.ExceptionMapper;

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
	
	@AfterThrowing(pointcut="execution(* *(..)) && @annotation(mapException)", throwing="ex")
	public void mapperAfterThrowing(MapException mapException, Throwable ex) throws Throwable{
		Class<?> clazz = mapException.by();
		Constructor<?> ctor;
		try {
			ctor = clazz.getConstructor();
			ExceptionMapper mapper = (ExceptionMapper) ctor.newInstance();
			throw mapper.mapIt(ex);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.error("[MEDEIUM-EXCEPTION]- Exception weving encountring some problem, original excepion was {}", ex);
		} 
		throw ex;
	}

}
