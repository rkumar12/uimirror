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
 * This is basically for the form based, password provided 
 * or accesstoken provided form
 * @author Jay
 */
public interface BasicAuthenticationForm extends CommonAuthentication{

	/**
	 * Should return the principal name i.e user id in case of
	 * {@link CredentialType#LOGINFORM}, {@link CredentialType#USERLOGINFORMFROMCLIENT}
	 * client id in case of {@link CredentialType#APIKEY}
	 * @return
	 */
	String getUserId();
	
	/**
	 * Should return the strategy that will be used to decrypt the password
	 * or accesstoken or secret key that has been issued.
	 * @return
	 */
	String getTokenEncryptStartegy();

	/**
	 * Should return the credential in case of
	 * {@link CredentialType#LOGINFORM} , {@link CredentialType#SCREENLOCK}, 
	 * {@link CredentialType#APIKEY}
	 * @return
	 */
	String getPassword();

	/**
	 * Should return this flag in case 
	 * {@link CredentialType#LOGINFORM}
	 * @return
	 */
	String getKeepMeLogedIn();
	
	/**
	 * Should return this in case of {@link CredentialType#APIKEY} 
	 * @return
	 */
	String getRedirectUri();
	
	/**
	 * Should return this in case of {@link CredentialType#USERLOGINFORMFROMCLIENT}
	 * @return
	 */
	String getScope();

	/**
	 * Specifies the token issued to the client 
	 * for {@link CredentialType#ACCESSKEY}, {@link CredentialType#SCREENLOCK}
	 * {@link CredentialType#USERLOGINFORMFROMCLIENT}
	 * @return
	 */
	String getAccessToken();
	
	/**
	 * When Client tries to login, he needs to present his client id
	 * @return
	 */
	String getClientId();
	
	/**
	 * returns the client secret key
	 * @return
	 */
	String getClientSecret();
}
