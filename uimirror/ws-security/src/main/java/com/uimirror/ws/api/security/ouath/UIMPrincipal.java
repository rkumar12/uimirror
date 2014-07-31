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
}
