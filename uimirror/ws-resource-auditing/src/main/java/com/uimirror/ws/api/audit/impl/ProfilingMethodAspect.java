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
package com.uimirror.ws.api.audit.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.uimirror.core.util.StopWatchUtil;
import com.uimirror.ws.api.audit.annotations.ProfileExecution;

/**
 * <p>This will enable the monitoring of the annotated method with {@link ProfileExecution}</p>
 * @author Jay
 */
@Aspect
public class ProfilingMethodAspect {
	protected static final Logger LOG = LoggerFactory.getLogger(ProfilingMethodAspect.class);

	@Around("execution(* *(..)) && @annotation(profileExecution)")
	public Object applyStopWatch(ProceedingJoinPoint joinPoint, ProfileExecution profileExecution) throws Throwable {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		final StopWatch stopWatch = new StopWatch(methodName);
        stopWatch.start(methodName);

        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            LOG.debug(StopWatchUtil.formatStopSwatchSummery(methodName, stopWatch));
        }
	}

}
