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
package com.uimirror.reach.web.core;

import java.util.Map;
import java.util.WeakHashMap;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;


/**
 * A reach user with different settings for the reach account.
 * @author Jay
 */
public class ReachUser extends AbstractBeanBasedDocument<RetailProduct> implements BeanValidatorService{

	private static final long serialVersionUID = 5386600445736661795L;

	private boolean adminOfAnyShop;
	private boolean anyOngoingSell;
	private int numberOfPromos;
	private boolean paymentAuthorized;

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public RetailProduct readFromMap(Map<String, Object> src) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
	

	public static class ReachUserBuilder implements Builder<ReachUser>{
		
		private String profileId;
		private boolean adminOfAnyShop;
		private boolean anyOngoingSell;
		private int numberOfPromos;
		private boolean paymentAuthorized;
		
		public ReachUserBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public ReachUserBuilder hasAdminOfShop(boolean adminOfAnyShop){
			this.adminOfAnyShop = adminOfAnyShop;
			return this;
		}
		
		public ReachUserBuilder hasAnyOnGoingSell(boolean anyOngoingSell){
			this.anyOngoingSell = anyOngoingSell;
			return this;
		}
		
		public ReachUserBuilder addNumberOfPromos(int numberOfPromos){
			this.numberOfPromos = numberOfPromos;
			return this;
		}
		
		public ReachUserBuilder hasPaymentAuthorized(boolean paymentAuthorized){
			this.paymentAuthorized = paymentAuthorized;
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public ReachUser build() {
			return new ReachUser(this);
		}
	}
	
	private ReachUser(ReachUserBuilder builder){
		super(builder.profileId);
		this.adminOfAnyShop = builder.adminOfAnyShop;
		this.anyOngoingSell = builder.anyOngoingSell;
		this.numberOfPromos = builder.numberOfPromos;
		this.paymentAuthorized = builder.paymentAuthorized;
	}

	public String getProfileId() {
		return getId();
	}

	public boolean isAdminOfAnyShop() {
		return adminOfAnyShop;
	}

	public boolean isAnyOngoingSell() {
		return anyOngoingSell;
	}

	public int getNumberOfPromos() {
		return numberOfPromos;
	}

	public boolean isPaymentAuthorized() {
		return paymentAuthorized;
	}
	
	//Sample Dummy Data
	private static final Map<String, ReachUser> STATIC;

	static{
		STATIC = new WeakHashMap<String, ReachUser>();
		ReachUser u1 = new ReachUserBuilder("1").
				addNumberOfPromos(12).hasAdminOfShop(Boolean.FALSE).hasAnyOnGoingSell(Boolean.FALSE).
				hasPaymentAuthorized(Boolean.FALSE).build();
		ReachUser u2 = new ReachUserBuilder("2").
				addNumberOfPromos(12).hasAdminOfShop(Boolean.TRUE).hasAnyOnGoingSell(Boolean.FALSE).
				hasPaymentAuthorized(Boolean.FALSE).build();
		
		ReachUser u3 = new ReachUserBuilder("3").
				addNumberOfPromos(12).hasAdminOfShop(Boolean.FALSE).hasAnyOnGoingSell(Boolean.TRUE).
				hasPaymentAuthorized(Boolean.FALSE).build();
		
		ReachUser u4 = new ReachUserBuilder("4").
				addNumberOfPromos(12).hasAdminOfShop(Boolean.TRUE).hasAnyOnGoingSell(Boolean.TRUE).
				hasPaymentAuthorized(Boolean.FALSE).build();
		
		ReachUser u5 = new ReachUserBuilder("5").
				addNumberOfPromos(12).hasAdminOfShop(Boolean.TRUE).hasAnyOnGoingSell(Boolean.TRUE).
				hasPaymentAuthorized(Boolean.TRUE).build();
		
		STATIC.put(u1.getProfileId(), u1);
		STATIC.put(u2.getProfileId(), u2);
		STATIC.put(u3.getProfileId(), u3);
		STATIC.put(u4.getProfileId(), u4);
		STATIC.put(u5.getProfileId(), u5);
	}
	
	public static ReachUser getReachUser(String profileId){
		return STATIC.get(profileId);
	}
}
