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
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.ws.api.audit.annotations.ClientAudit;
import com.uimirror.ws.api.audit.annotations.ProfileExecution;

/**
 * <p>This will enable the monitoring of the annotated method with {@link ProfileExecution}</p>
 * @author Jay
 */
@Aspect
public class ClientAuditAspect {
	protected static final Logger LOG = LoggerFactory.getLogger(ClientAuditAspect.class);

	@Around("execution(* *(..)) && @annotation(clientAudit)")
	public Object applyStopWatch(ProceedingJoinPoint joinPoint, ClientAudit clientAudit) throws Throwable {
		Signature signature = joinPoint.getSignature();
		//Do auditing
        try {
            return joinPoint.proceed();
        } finally {
        }
	}

}
