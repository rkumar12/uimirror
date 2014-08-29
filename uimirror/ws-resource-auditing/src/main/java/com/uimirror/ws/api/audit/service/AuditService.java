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
package com.uimirror.ws.api.audit.service;

import com.uimirror.ws.api.audit.Audit;
import com.uimirror.ws.api.audit.exception.AuditException;

/**
 * <p>Service to handle all the process related to auditing process</p>
 * @author Jay
 * @since 1.0
 */
public interface AuditService {

	/**
	 * <p>This will create a new Audit record</p>
	 * @param audit
	 * @throws AuditException in case any thing wrong happened
	 */
	public void auditIt(final Audit audit) throws AuditException;
	
	/**
	 * <p>This will get the audit details by id</p>
	 * @param id
	 * @return 
	 * @throws AuditException
	 */
	public Audit getAuditTrailById(String id) throws AuditException;
}
