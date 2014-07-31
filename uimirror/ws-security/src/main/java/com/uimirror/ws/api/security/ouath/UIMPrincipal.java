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
package com.uimirror.ws.api.security.ouath;

import java.security.Principal;
import java.time.ZonedDateTime;

import com.uimirror.ws.api.security.bean.base.Client;
import com.uimirror.ws.api.security.bean.base.Scope;
import com.uimirror.ws.api.security.bean.base.User;

/**
 * @author Jay
 *
 */
public interface UIMPrincipal extends Principal {

	/**
	 * <p>This is in contrast to the {@link Principal#getName()} which will return the client access token.</p>
	 * <p>This will return the user id associated with this</p>
	 * @return
	 */
	public String getUserId();
	
	/**
	 * <p>This is in contrast to the {@link Principal#getName()} which will return the client access token.</p>
	 * <p>This will return the client id associated with this</p>
	 * @return
	 */
	public String getClientId();
	
	/**
	 * <p>This is in contrast to the {@link Principal#getName()} which will return the client access token.</p>
	 * <p>This will return the client apiKey associated with this</p>
	 * @return
	 */
	public String getClientApiKey();
	
	/**
	 * <p>This will return the scope of the current access-token, if user is already populated, else 
	 * {@link IllegalArgumentException}</p>
	 * 
	 * @return
	 */
	public Scope getScope();
	
	/**
	 * <p>This will return the client of the current access-token, if client and user is authenticated, 
	 * else {@link IllegalArgumentException}</p>
	 * @return
	 */
	public Client getClient();
	
	/**
	 * <p>This will return the user of the current access-token, if client and user is authenticated, 
	 * else {@link IllegalArgumentException}</p>
	 * @return
	 */
	public User getUser(); 
	
	/**
	 * <p>This will return the grantedOn of the current access-token, if client and user is authenticated, 
	 * else {@link IllegalArgumentException}</p>
	 * @return
	 */
	public ZonedDateTime getTokenCreationDate();
	
	/**
	 * <p>This will return the expiresOn of the current access-token, if client and user is authenticated, 
	 * else {@link IllegalArgumentException}</p>
	 * @return
	 */
	public ZonedDateTime getExpiresOn();
	
}
