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
package com.uimirror.core.shop;

import static com.uimirror.core.mongo.feature.BasicDBFields.ID;
import static com.uimirror.core.shop.ShopDBFields.*;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.util.StringUtils;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * @author
 */
public class ShopBasicInfo extends AbstractBeanBasedDocument<ShopBasicInfo> implements BeanValidatorService{
	
	private static final long serialVersionUID = 4208080092697146070L;
	
	private String shopName;
	private String email;
	private String profileSnapLoc;
	private String coverSnapLoc;
	private String registrationId;
	

	/**
	 * 
	 */
	public ShopBasicInfo() {
		//NOP
	}
	
	public ShopBasicInfo updateId(String id){
		return  new ShopBasicInfoBuilder(id).
				addShopName(shopName)
				.addEmail(email)
				.addProfileSnapLoc(profileSnapLoc)
				.addCovergeSnapLoc(coverSnapLoc)
				.addRegistrationId(registrationId)
				.build();
	}
	
	public ShopBasicInfo enable(){
		
		return new ShopBasicInfoBuilder(getProfileId()).
				addShopName(shopName)
				.addEmail(email)
				.addProfileSnapLoc(profileSnapLoc)
				.addCovergeSnapLoc(coverSnapLoc)
				.addRegistrationId(registrationId).build();
	}
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	
	public Map<String, Object> serailize() {
		Map<String, Object> state = new WeakHashMap<String, Object>(16);
		
		if(StringUtils.hasText(getId()))
			state.put(ID, getId());
			state.put(NAME, getShopName());
		return state;
		}
		

	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public ShopBasicInfo readFromMap(Map<String, Object> src)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
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

	public String getShopName() {
		return shopName;
	}

	public String getEmail() {
		return email;
	}

	public String getProfileSnapLoc() {
		return profileSnapLoc;
	}

	public String getCoverSnapLoc() {
		return coverSnapLoc;
	}

	public String getRegistrationId() {
		return registrationId;
	}
	
	public String getProfileId(){
		return getId();
	}
	
	public static class ShopBasicInfoBuilder implements Builder<ShopBasicInfo>{
		
		private String shopId;
		private String shopName;
		private String email;
		private String profileSnapLoc;
		private String coverSnapLoc;
		private String registrationId;
		
		public ShopBasicInfoBuilder(String shopId){
			
			this.shopId = shopId;
		}
		
		public ShopBasicInfoBuilder addShopName(String shopName){
			this.shopName = shopName;
			return this;
		}
		
		public ShopBasicInfoBuilder addEmail(String email){
			this.email = email;
			return this;
		}
		
		public ShopBasicInfoBuilder addProfileSnapLoc(String profileSnapLoc){
			this.profileSnapLoc = profileSnapLoc;
			return this;
		}
		
		public ShopBasicInfoBuilder addCovergeSnapLoc(String coverSnapLoc){
			this.coverSnapLoc = coverSnapLoc;
			return this;
		}
		
		public ShopBasicInfoBuilder addRegistrationId(String registrationId){
			this.registrationId = registrationId;
			return this;
		}
		

		public String getShopId() {
			return shopId;
		}

		public String getShopName() {
			return shopName;
		}

		public String getEmail() {
			return email;
		}

		public String getProfileSnapLoc() {
			return profileSnapLoc;
		}

		public String getCoverSnapLoc() {
			return coverSnapLoc;
		}

		public String getRegistrationId() {
			return registrationId;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public ShopBasicInfo build() {
			// TODO Auto-generated method stub
			return new ShopBasicInfo(this);
		}
		
	}
	
	private ShopBasicInfo(ShopBasicInfoBuilder builder){
		super(builder.shopId);
		this.shopName = builder.shopName;
		this.email= builder.email;
		this.registrationId = builder.registrationId;
		this.profileSnapLoc = builder.profileSnapLoc;
		this.coverSnapLoc = builder.coverSnapLoc;
	}
	
	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
	    style.setFieldSeparator(", ");
	    style.setUseClassName(false);
	    style.setUseIdentityHashCode(false);
	    return new ReflectionToStringBuilder(this, style).toString();
	}
	
	

}
