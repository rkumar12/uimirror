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
package com.uimirror.ws.api.audit.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.ws.api.audit.Audit;
import com.uimirror.ws.api.audit.service.AuditService;

/**
 * @author Jay
 */
public class AuditItCallable implements Callable<Object> {

	protected static final Logger LOG = LoggerFactory.getLogger(AuditItCallable.class);
	
	private final AuditService auditService;
	private final Audit audit;
	public AuditItCallable(AuditService auditService, Audit audit) {
		this.auditService = auditService;
		this.audit = audit;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Object call() throws Exception {
		LOG.info("[CALLABLE-THREAD-START]- Auditing the request");
		this.auditService.auditIt(this.audit);
		LOG.info("[CALLABLE-THREAD-END]- Auditing the request");
		return null;
	}

}
