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
package com.uimirror.ws.api.security.service;

import java.security.Principal;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.exception.AccessTokenException;
import com.uimirror.ws.api.security.exception.ClientException;
import com.uimirror.ws.api.security.ouath.UIMPrincipal;
import com.uimirror.ws.api.security.ouath.UIMirrorSecurity;
import com.uimirror.ws.api.security.util.SecurityResponseUtil;

/**
 * <p>Implementation of {@link PrincipalService}</p>
 * @author Jay
 */
public class PrincipalServiceImpl implements PrincipalService {
	
	protected static final Logger LOG = LoggerFactory.getLogger(PrincipalServiceImpl.class);
	private final AccessTokenService accessTokenService;

	public PrincipalServiceImpl(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.PrincipalService#populateSecurityConext(javax.ws.rs.container.ContainerRequestContext)
	 */
	//TODO handle user exception as well
	@Override
	public void updateUserInSecurityConext(ContainerRequestContext request) throws WebApplicationException{
		LOG.info("[START]- Updating User In Security context");
		//Validate if the token was eariller validate and it must be else stop him
		validateUimPrincipal(request);
    	UIMPrincipal principal = (UIMPrincipal)request.getSecurityContext().getUserPrincipal();
    	if(principal.getUser() == null){
    		//Populating user into security context
    		//TODO once user exeception over populate that here in catch block
    		request.setSecurityContext(new UIMirrorSecurity(accessTokenService.populateUser((AccessToken)principal)));
    	}
    	LOG.info("[END]- Updating User In Security context");
	}

	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.PrincipalService#populateTokenOnlySecurityConext(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void populateTokenOnlySecurityConext(ContainerRequestContext request) throws WebApplicationException {
		LOG.info("[START]- Populating Access token Security context only");
		if(isTokenLoadinRequired(request)){
			Principal principal = request.getSecurityContext().getUserPrincipal();
			try{
				request.setSecurityContext(new UIMirrorSecurity(principal, accessTokenService.getActiveAccessTokenByTokenId(principal.getName())));
			}catch(AccessTokenException | IllegalArgumentException e){
    			LOG.error("[SECURITY-ERROR]- Something went wrong while getting the access token {}",e);
    	        throw new WebApplicationException(SecurityResponseUtil.buildTokenInvalidResponse());
    		}
		}
		LOG.info("[END]- Populating Access token Security context only");
		
	}
	
	/* (non-Javadoc)
	 * @see com.uimirror.ws.api.security.service.PrincipalService#updateClientInSecurityConext(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void updateClientInSecurityConext(ContainerRequestContext request) throws WebApplicationException {
		LOG.info("[START]- Updating Client In Security context");
		validateUimPrincipal(request);
		UIMPrincipal principal = (UIMPrincipal)request.getSecurityContext().getUserPrincipal();
    	//Populating user into security context
		try{
			request.setSecurityContext(new UIMirrorSecurity(accessTokenService.populateClient((AccessToken)principal)));
		}catch(ClientException | IllegalArgumentException e){
			LOG.error("[SECURITY-ERROR]- Something went wrong while getting the client {}",e);
			throw new WebApplicationException(SecurityResponseUtil.buildClientInvalidResponse());
		}
		LOG.info("[END]- Updating Client In Security context");
		
	}
	
	/**
	 * <p>This will check if the principal is a valid principal of type {@link UIMPrincipal}</p>
	 * @param request
	 * @return <code>false</code> if it is type of {@link UIMPrincipal} else <code>true</code> 
	 */
	private boolean isTokenLoadinRequired(ContainerRequestContext request){
		Principal principal = request.getSecurityContext().getUserPrincipal();
		return !(principal instanceof UIMPrincipal);
	}
	
	/**
	 * <p>This will validate the if the updated principal is not of type {@link UIMPrincipal}</p>
	 * @param request
	 */
	private void validateUimPrincipal(ContainerRequestContext request) throws WebApplicationException{
		if(isTokenLoadinRequired(request)){
			throw new WebApplicationException(SecurityResponseUtil.buildTokenInvalidResponse());
		}
	}

}
