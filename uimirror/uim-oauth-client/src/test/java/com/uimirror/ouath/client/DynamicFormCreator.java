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
package com.uimirror.ouath.client;

import java.lang.reflect.Field;

import com.uimirror.ouath.client.form.ClientRegisterForm;

/**
 * @author Jay
 */
public class DynamicFormCreator {

	@SuppressWarnings("unchecked")
	public static ClientRegisterForm createClientRegisterForm(){
		
		Class<ClientRegisterForm> c = null;
		Object o = null;
	    try {
	        c = (Class<ClientRegisterForm>) Class.forName("com.uimirror.ouath.client.form.ClientRegisterForm");
	        Field[] metaf = c.getSuperclass().getSuperclass().getDeclaredFields();
	        Field[] f  = c.getDeclaredFields(); //or getField("...")
	        o = c.newInstance();
	        
	        metaf[1].setAccessible(true);
	        metaf[1].set(o, "127.0.0.1");
	        metaf[2].setAccessible(true);
	        metaf[2].set(o, "Chrome");
	        
	        f[1].setAccessible(true);
	        f[1].set(o, "UIMIRROR");
	        f[2].setAccessible(true);
	        f[2].set(o, "http://uimirror.com/test");
	        f[3].setAccessible(true);
	        f[3].set(o, "http://uimirror.com");
	        f[4].setAccessible(true);
	        f[4].set(o, "test.png");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }   catch (IllegalAccessException iae) {
	        iae.printStackTrace();
	    } catch (InstantiationException ie) {
	        ie.printStackTrace();
	    }
	    return (ClientRegisterForm)o;
	}

}
