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
package com.uimirror.ws.api.audit.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.server.model.AnnotatedMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.api.threadutil.SubmitAndForgetAdapter;
import com.uimirror.ws.api.audit.Audit;
import com.uimirror.ws.api.audit.Priorities;
import com.uimirror.ws.api.audit.annotations.ClientAudit;
import com.uimirror.ws.api.audit.callable.AuditItCallable;
import com.uimirror.ws.api.audit.service.AuditService;

/**
 * A {@link DynamicFeature} supporting the {@code ClientAudit},
 * on resource methods and sub-resource methods.
 * <p>
 * The will audit the client call, like client meta, token info with a unique ID.
 * while returning response, it will update the response with same ID to mark the status.
 * TODO update the documentation part latter
 *
 * @author Jay
 */
public class AuditDynamicFeature implements DynamicFeature {

	//No other way to bind this so binding via Autowired
	private AuditService auditService;
	
	/* (non-Javadoc)
	 * @see javax.ws.rs.container.DynamicFeature#configure(javax.ws.rs.container.ResourceInfo, javax.ws.rs.core.FeatureContext)
	 */
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());
		// ClientAudit on the method takes precedence
        ClientAudit cla = am.getAnnotation(ClientAudit.class);
        if (cla != null) {
        	context.register(new AuditResponseFilter(auditService));
            return;
        }

        // ClientAudit on the class takes precedence 
        cla = resourceInfo.getResourceClass().getAnnotation(ClientAudit.class);
        if (cla != null) {
        	context.register(new AuditResponseFilter(auditService));
        }

	}
	
	@Priority(Priorities.CLIENT_AUDIT_RESPONSE)//It should be just before any of the response filter to commit the response 
	private static class AuditResponseFilter implements ContainerResponseFilter{

		private final AuditService auditService;
		protected static final Logger LOG = LoggerFactory.getLogger(AuditResponseFilter.class);
		public AuditResponseFilter(AuditService auditService) {
			this.auditService = auditService;
		}
		
		@Override
		public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
			LOG.info("[RS-FILTER-START]- Auditing Started");
			SubmitAndForgetAdapter sf = new SubmitAndForgetAdapter(1);
			//TODO make the appropriate createAuditObject()
			sf.submitTask(createAuditJob(createAuditObject()));
			LOG.info("[RS-FILTER-END]- Auditing Started");
		}
		
		/**
		 * <p>This helps to create a audit object to be saved</p>
		 * @return
		 *///TODO complete this input as well the output
		private Audit createAuditObject(){
			return null;
		}
		
		private List<Callable<Object>> createAuditJob(Audit audit){
			List<Callable<Object>> jobs = new ArrayList<Callable<Object>>(1);
			jobs.add(new AuditItCallable(auditService, audit));
			return jobs;
		}
		
	}

}
