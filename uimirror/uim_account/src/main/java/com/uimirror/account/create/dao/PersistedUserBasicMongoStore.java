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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mongodb.DBCollection;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.user.bean.BasicUserInfo;

/**
 * @author Jay
 */
public class PersistedUserBasicMongoStore extends AbstractMongoStore<BasicUserInfo> implements BasicUserStore{


	/**
	 * Assign/ Create collection from the given {@link DBCollection}
	 * @param collection
	 */
	@Autowired
	public PersistedUserBasicMongoStore(@Qualifier("userBasicInfoCol") DBCollection collection){
		super(collection, BasicUserInfo.class);
	}

	/* (non-Javadoc)
	 * @see com.uimirror.account.create.dao.BasicUserStore#findByEmail(java.lang.String)
	 */
	@Override
	public BasicUserInfo findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.dao.AbstractMongoStore#ensureIndex()
	 */
	@Override
	protected void ensureIndex() {
		// TODO Auto-generated method stub
		
	}
}
