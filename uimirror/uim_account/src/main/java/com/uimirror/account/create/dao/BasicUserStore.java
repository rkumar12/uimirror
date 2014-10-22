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
package com.uimirror.account.create.dao;

import com.uimirror.core.user.BasicInfo;

/**
 * @author Jay
 */
//TODO update java doc
public interface BasicUserStore {

	BasicInfo findByEmail(String email);
	
	BasicInfo store(BasicInfo details);
}
