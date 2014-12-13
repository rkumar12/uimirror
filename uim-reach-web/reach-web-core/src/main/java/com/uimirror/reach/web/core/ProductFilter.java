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

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the filter details about the Promo. 
 * @author Jay
 */
public class ProductFilter {

	private RetailProduct product;
	private boolean selectAll;

	public ProductFilter(RetailProduct product, boolean selectAll){
		this.product = product;
		this.selectAll = selectAll;
	}

	public RetailProduct getProduct() {
		return product;
	}

	public boolean isSelectAll() {
		return selectAll;
	}
	
	//Sample Dummy Data
	private static final List<ProductFilter> C1;
	private static final List<ProductFilter> C2;
	private static final List<ProductFilter> C3;
	
	static{
		C1 = new ArrayList<ProductFilter>();
		C1.add(new ProductFilter(RetailProduct.getMongoBookProduct(), Boolean.TRUE));
		C1.add(new ProductFilter(RetailProduct.getTvProduct(), Boolean.TRUE));
		C1.add(new ProductFilter(RetailProduct.getProgramingProduct(), Boolean.TRUE));
		C1.add(new ProductFilter(RetailProduct.getHomeAppliance(), Boolean.FALSE));

		C2 = new ArrayList<ProductFilter>();
		C2.add(new ProductFilter(RetailProduct.getMongoBookProduct(), Boolean.TRUE));
		C2.add(new ProductFilter(RetailProduct.getTvProduct(), Boolean.TRUE));
		C2.add(new ProductFilter(RetailProduct.getHomeAppliance(), Boolean.FALSE));
		
		C3 = new ArrayList<ProductFilter>();
		C3.add(new ProductFilter(RetailProduct.getMongoBookProduct(), Boolean.TRUE));
		C3.add(new ProductFilter(RetailProduct.getProgramingProduct(), Boolean.TRUE));
		C3.add(new ProductFilter(RetailProduct.getHomeAppliance(), Boolean.FALSE));
	}
	
	public static List<ProductFilter> getCtategoryOne(){
		return C1;
	}
	public static List<ProductFilter> getCtategoryTwo(){
		return C2;
	}
	public static List<ProductFilter> getCtategoryThree(){
		return C3;
	}

}
