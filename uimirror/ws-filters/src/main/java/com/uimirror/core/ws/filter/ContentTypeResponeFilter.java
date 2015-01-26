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
package com.uimirror.core.ws.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A response filter that will put the content type to the response body.
 * Any module wants to set the diffrent content type can extend this and override the {@link #addContentTypeHeader(ContainerResponseContext)}
 * 
 * @author Jayaram
 *
 */
public class ContentTypeResponeFilter implements ContainerResponseFilter {

	protected static final Logger LOG = LoggerFactory.getLogger(ContentTypeResponeFilter.class);
	private static final String CONTENT_TYPE = "Content-Type";
    private static final String CHAR_SET = ";charset=UTF-8";
    
	public ContentTypeResponeFilter() {
		//NOP
	}

	
	@Override
	public final void filter(ContainerRequestContext cRequest,
			ContainerResponseContext cResponse) throws IOException {
		LOG.info("[START]-Adding the response content type details to send back to the caller");
		if (cResponse.getMediaType() != null){
			addContentTypeHeader(cResponse);
			LOG.info("[END]-Adding the response content type details to send back to the caller");
		}
	}
	
	/**
	 * Set the content type and character sets
	 * @param cResponse where content type will set
	 */
	public void addContentTypeHeader(ContainerResponseContext cResponse){
		cResponse.getHeaders().putSingle(CONTENT_TYPE, cResponse.getMediaType() + CHAR_SET);
		
	}

}
