/**
 * 
 */
package com.uimirror.ws.api.audit.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import com.uimirror.ws.api.audit.Priorities;

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

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.DynamicFeature#configure(javax.ws.rs.container.ResourceInfo, javax.ws.rs.core.FeatureContext)
	 */
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		// TODO Auto-generated method stub

	}
	
	@Priority(Priorities.CLIENT_AUDIT_REQUEST)//It should be just after the authentication and authorization
	private static class AuditRequestFilter implements ContainerRequestFilter{

		@Override
		public void filter(ContainerRequestContext requestContext) throws IOException {
			// TODO Auto-generated method stub
		}
		
	}
	
	@Priority(Priorities.CLIENT_AUDIT_RESPONSE)//It should be just before any of the response filter to commit the response 
	private static class AuditResponseFilter implements ContainerResponseFilter{

		@Override
		public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
			
		}
		
	}

}
