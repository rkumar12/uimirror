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

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.uimirror.core.Builder;
import com.uimirror.core.mongo.feature.AbstractBeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

/**
 * Contains the filter details about the Promo. 
 * @author Jay
 */
public class PromoFilter extends AbstractBeanBasedDocument<PromoFilter> implements BeanValidatorService{

	private static final long serialVersionUID = 1L;
	private double distance;
	private DistanceUnit distanceUnit;
	private int limitResult;
	private boolean includePromo;
	private boolean includeUserSales;
	private int numberOfDaysOldUserSalesAd;
	private List<ProductFilter> products;
	
	/* (non-Javadoc)
	 * @see com.uimirror.core.mongo.feature.MongoDocumentSerializer#readFromMap(java.util.Map)
	 */
	@Override
	public PromoFilter readFromMap(Map<String, Object> src)
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
	
	@Override
	public Map<String, Object> writeToMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return null;
	}
	
	public static class PromoFilterBuilder implements Builder<PromoFilter>{
		private double distance;
		private DistanceUnit distanceUnit;
		private int limitResult;
		private boolean includePromo;
		private boolean includeUserSales;
		private int numberOfDaysOldUserSalesAd;
		private List<ProductFilter> products;
		private String profileId;
		
		public PromoFilterBuilder(String profileId){
			this.profileId = profileId;
		}
		
		public PromoFilterBuilder addDistance(double distance){
			this.distance = distance;
			return this;
		}
		
		public PromoFilterBuilder addDistanceUnit(DistanceUnit distanceUnit){
			this.distanceUnit = distanceUnit;
			return this;
		}
		
		public PromoFilterBuilder addDistanceUnit(String distanceUnit){
			this.distanceUnit = DistanceUnit.getEnum(distanceUnit);
			return this;
		}
		
		public PromoFilterBuilder addLimitResult(int limit){
			this.limitResult = limit;
			return this;
		}
		
		public PromoFilterBuilder includePromo(boolean includePromo){
			this.includePromo = includePromo;
			return this;
		}
		
		public PromoFilterBuilder includeUserSales(boolean userSales){
			this.includeUserSales = userSales;
			return this;
		}
		
		public PromoFilterBuilder addNumberOfDaysOldSaleAd(int numberOfDays){
			this.numberOfDaysOldUserSalesAd = numberOfDays;
			return this;
		}
		
		public PromoFilterBuilder addProductsCategory(List<ProductFilter> filters){
			this.products = filters;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.uimirror.core.Builder#build()
		 */
		@Override
		public PromoFilter build() {
			return new PromoFilter(this);
		}
		
	}
	
	private PromoFilter(PromoFilterBuilder builder){
		super(builder.profileId);
		this.distance = builder.distance;
		this.distanceUnit = builder.distanceUnit;
		this.limitResult = builder.limitResult;
		this.includePromo = builder.includePromo;
		this.includeUserSales = builder.includeUserSales;
		this.numberOfDaysOldUserSalesAd = builder.numberOfDaysOldUserSalesAd;
		this.products = builder.products;
		
	}


	public double getDistance() {
		return distance;
	}


	public DistanceUnit getDistanceUnit() {
		return distanceUnit;
	}


	public int getLimitResult() {
		return limitResult;
	}


	public boolean isIncludePromo() {
		return includePromo;
	}


	public boolean isIncludeUserSales() {
		return includeUserSales;
	}


	public int getNumberOfDaysOldUserSalesAd() {
		return numberOfDaysOldUserSalesAd;
	}

	public List<ProductFilter> getProducts() {
		return products;
	}

	public String getProfileId() {
		return getId();
	}
	
	//Sample Dummy Data
	private static final Map<String, PromoFilter> DATA;
	private static final PromoFilter f5;
	
	static{
		DATA = new WeakHashMap<String, PromoFilter>();
		PromoFilter f1 = new PromoFilterBuilder("1").
				addDistance(2).addDistanceUnit(DistanceUnit.KILOMETRE).addProductsCategory(ProductFilter.getCtategoryOne()).
				addLimitResult(10).addNumberOfDaysOldSaleAd(2).includePromo(Boolean.TRUE).includeUserSales(Boolean.TRUE).build();
		
		PromoFilter f2 = new PromoFilterBuilder("2").
				addDistance(2).addDistanceUnit(DistanceUnit.KILOMETRE).addProductsCategory(ProductFilter.getCtategoryTwo()).
				addLimitResult(10).addNumberOfDaysOldSaleAd(2).includePromo(Boolean.TRUE).includeUserSales(Boolean.TRUE).build();
		
		PromoFilter f3 = new PromoFilterBuilder("3").
				addDistance(2).addDistanceUnit(DistanceUnit.KILOMETRE).addProductsCategory(ProductFilter.getCtategoryThree()).
				addLimitResult(10).addNumberOfDaysOldSaleAd(2).includePromo(Boolean.TRUE).includeUserSales(Boolean.TRUE).build();
		
		PromoFilter f4 = new PromoFilterBuilder("4").
				addDistance(2).addDistanceUnit(DistanceUnit.KILOMETRE).addProductsCategory(ProductFilter.getCtategoryOne()).
				addLimitResult(10).addNumberOfDaysOldSaleAd(2).includePromo(Boolean.TRUE).includeUserSales(Boolean.TRUE).build();
		
		f5 = new PromoFilterBuilder("5").
				addDistance(2).addDistanceUnit(DistanceUnit.KILOMETRE).addProductsCategory(ProductFilter.getCtategoryTwo()).
				addLimitResult(10).addNumberOfDaysOldSaleAd(2).includePromo(Boolean.TRUE).includeUserSales(Boolean.TRUE).build();
		
		DATA.put(f1.getProfileId(), f1);
		DATA.put(f2.getProfileId(), f2);
		DATA.put(f3.getProfileId(), f3);
		DATA.put(f4.getProfileId(), f4);
		DATA.put(f5.getProfileId(), f5);
	}

	public static PromoFilter getFilter(String profileId){
		PromoFilter filter = DATA.get(profileId);
		if(filter == null){
			filter = f5;
			DATA.put(profileId, f5);
		}
		return filter;
	}
}
