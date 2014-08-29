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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoException;
import com.uimirror.ws.api.audit.Audit;
import com.uimirror.ws.api.audit.exception.AuditException;
import com.uimirror.ws.api.audit.exception.ErrorConstant;
import com.uimirror.ws.api.audit.repo.AuditRepo;

/**
 * <p>This will be the implementation of the {@link AuditService} for the audit releated operations</p>
 * @author Jay
 */
public class AuditServiceImpl implements AuditService {

	protected static final Logger LOG = LoggerFactory.getLogger(AuditServiceImpl.class);
	private final AuditRepo auditRepo;
	
	public AuditServiceImpl(AuditRepo auditRepo) {
		this.auditRepo = auditRepo;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.service.AuditService#auditIt(com.uimirror.ws.api.audit.Audit)
	 */
	@Override
	public void auditIt(Audit audit) throws AuditException {
		LOG.info("[START]- Saving a Audit Trail");
		try{
			auditRepo.insert(audit);
		}catch(IllegalArgumentException e){
			LOG.error("[ERROR]- Argument supplied was not correct, {}",e);
			throw new AuditException(ErrorConstant.AUDIT_INPUT_ERROR, "No valid input to save the audit", e);
		}catch(MongoException me){
			LOG.error("[ERROR]- Something was wrong, {}",me);
			throw new AuditException(ErrorConstant.MONGO_ERROR, "Something DB operation got wrong", me);
		}
		LOG.info("[END]- Saving a Audit Trail");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.audit.service.AuditService#getAuditTrailById(java.lang.String)
	 */
	@Override
	public Audit getAuditTrailById(String id) throws AuditException {
		LOG.info("[START]- Retrivng the Audit details by audit ID");
		Audit audit = null;
		try{
			audit = auditRepo.findById(id);
		}catch(IllegalArgumentException e){
			LOG.error("[ERROR]- Argument supplied was not correct, {}",e);
			throw new AuditException(ErrorConstant.AUDIT_INPUT_ERROR, "No valid input to get the audit", e);
		}catch(MongoException me){
			LOG.error("[ERROR]- Something was wrong, {}",me);
			throw new AuditException(ErrorConstant.MONGO_ERROR, "Something DB operation got wrong", me);
		}
		LOG.info("[END]- Retrivng the Audit details by audit ID");
		if(audit == null)
			throw new AuditException(ErrorConstant.AUDIT_NOT_FOUND, "No Audit Details Found");
		return audit;
	}

}
