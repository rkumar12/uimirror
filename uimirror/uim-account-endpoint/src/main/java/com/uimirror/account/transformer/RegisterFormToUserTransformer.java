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
package com.uimirror.account.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.uimirror.account.core.Password;
import com.uimirror.account.form.RegisterForm;
import com.uimirror.core.service.TransformerService;
import com.uimirror.core.user.AccountLogs;
import com.uimirror.core.user.AccountState;
import com.uimirror.core.user.AccountStatus;
import com.uimirror.core.user.BasicDetails;
import com.uimirror.core.user.BasicInfo;
import com.uimirror.core.user.Credentials;
import com.uimirror.core.user.DefaultUser;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.ouath.client.form.ClientRegisterForm;

/**
 * A transformer implementation which will transform the {@linkplain ClientRegisterForm}
 * to {@linkplain DefaultUser}
 * @author Jay
 */
public class RegisterFormToUserTransformer implements TransformerService<RegisterForm, DefaultUser>{


	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public DefaultUser transform(RegisterForm src) {
		Assert.notNull(src, "Source Can't be empty");
		// Form Should be validated a way behind , so not required Validate the form
		//src.isValid();
		BasicInfo basicInfo = createUserInfo(src);
		Credentials credentials = createCredentials(src);
		AccountLogs logs = createLogs();
		BasicDetails details = createDetails(src);
		return new DefaultUser.DefaultUserBuilder(null).
				addBasicInfo(basicInfo).
				addCredentials(credentials).
				addDetails(details).
				addLogs(logs).build();
//		return new DefaultUser(basicInfo, credentials, details, logs);
	}
	
	private BasicInfo createUserInfo(RegisterForm form){
		
		BasicInfo user = new BasicInfo.BasicInfoBuilder(null).
				addFirstName(form.getFirstName()).
				addLastName(form.getLastName()).
				addEmail(form.getEmail()).
				addGender(form.getGender()).
				addStatus(AccountStatus.ACTIVE).
				addState(AccountState.NEW).
				build();
//		BasicInfo user = new BasicInfo(null, form.getFirstName()
//				, form.getLastName(), form.getEmail(), form.getGender(), AccountStatus.ACTIVE, AccountState.NEW);
		return user;
	}
	
	private Credentials createCredentials(RegisterForm form){
		Password password = form.getPassword();
		List<String> userNames = new ArrayList<String>();
		userNames.add(form.getEmail());
		return new Credentials.CredentialsBuilder(null).
				addUserNames(userNames).
				addPassword(password.getPassword()).
				addState(AccountState.NEW).
				addStatus(AccountStatus.ACTIVE).
				addEncStartegy(password.getParaphrase()).build();
//		return new Credentials(null, userNames, password.getPassword(), null, AccountState.NEW, AccountStatus.ACTIVE, password.getParaphrase(), null);
	}
	
	private AccountLogs createLogs(){
		return new AccountLogs.LogBuilder(null).updatedCreatedOn(DateTimeUtil.getCurrentSystemUTCEpoch()).build();
//		return new AccountLogs(null, DateTimeUtil.getCurrentSystemUTCEpoch(), 0l, null);
	}
	private BasicDetails createDetails(RegisterForm form){
//		DOB dob = new DOB(form.getDateOfBirth());
		//return new BasicDetails(null, null, null, dob, null);
		//TODO fix me
		return null;
	}

}
