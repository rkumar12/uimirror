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
package com.uimirror.ws.api.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A response filter that will put the Powered By in every call.
 * @author Jayaram
 *
 */
public class PoweredByResponseFilter implements ContainerResponseFilter{
    
    protected static final Logger LOG = LoggerFactory.getLogger(PoweredByResponseFilter.class);
    private static final String X_POWERED_BY = "X-Powered-By";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CHAR_SET = ";charset=UTF-8";
    private static final String UIMIRROR = "UIMirror";

    public PoweredByResponseFilter() {
    }

	@Override
	public void filter(ContainerRequestContext cRequest, ContainerResponseContext cResponse) throws IOException {
		LOG.info("[START]-Adding the response body details to send back to the caller");
		cResponse.getHeaders().putSingle(X_POWERED_BY, UIMIRROR);
		//Append other details if present else don't
		MediaType contentType = cResponse.getMediaType();
		if (contentType != null){
			System.out.println(contentType.getParameters());
			cResponse.getHeaders().putSingle(CONTENT_TYPE, contentType.toString() + CHAR_SET);
		}
		LOG.info("[END]-Adding the response body details to send back to the caller");
	}


}