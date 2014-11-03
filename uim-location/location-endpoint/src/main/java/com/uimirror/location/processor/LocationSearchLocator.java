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
package com.uimirror.location.processor;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.location.form.LocationQueryForm;

/**
 * @author Jay
 */
public class LocationSearchLocator implements Processor<LocationQueryForm, String>{

	/**
	 * 
	 */
	public LocationSearchLocator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.Processor#invoke(java.lang.Object)
	 */
	@Override
	public String invoke(LocationQueryForm arg0) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
