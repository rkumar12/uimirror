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
package com.uimirror.core.auth.bean.form;

import com.uimirror.core.auth.bean.CredentialType;

/**
 * Form that will be initiated during any incoming call
 * for authentication.
 * @author Jay
 */
public interface CommonAuthentication {

	/**
	 * Should return the credential type that caller is sending
	 * it can be anything from the below
	 * {@link CredentialType#APIKEY}, {@link CredentialType#CLIENTSECRECTKEY}, {@link CredentialType#ACCESSKEY}
	 * {@link CredentialType#LOGINFORM}, {@link CredentialType#SCREENLOCK}, {@link CredentialType#USERLOGINFORMFROMCLIENT}
	 * @return
	 */
	CredentialType getCredentialType();

	/**
	 * This gives the client IP, tries to connect 
	 * this should present in all communication
	 * @return
	 */
	String getIp();

	/**
	 * Specifies the user browser meta information, from where user tries to get connect
	 * it should present in all the communication
	 * @return
	 */
	String getUserAgent();

}
