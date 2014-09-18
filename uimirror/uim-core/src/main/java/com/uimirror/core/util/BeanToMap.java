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
package com.uimirror.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to map bean to map
 * @author Jay
 */
public class BeanToMap {
	
	private static final Logger LOG = LoggerFactory.getLogger(BeanToMap.class);

	/**
	 * <p>This converts a bean to map using {@link BeanMap}</p>
	 * This remove class key from the map default
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> toMap(Object bean){
		return toMap(bean, Boolean.TRUE);
	}
	
	/**
	 * <p>This converts a bean to map using {@link BeanMap}</p>
	 * @param bean
	 * @param removeCLass is <code>Boolean.True</code> then removes class from the result map
	 * @return
	 */
	public static Map<String, Object> toMap(Object bean, boolean removeCLass){
		Map<String, Object> objectAsMap = new LinkedHashMap<String, Object>();
		new BeanMap(bean).entrySet().forEach(rs -> objectAsMap.put(rs.getKey().toString(), rs.getValue()));
		if(removeCLass)
			objectAsMap.remove("class");
		return objectAsMap;
	}
	
	/**
	 * <p>This converts a map to bean using {@link BeanUtils}</p>
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object fromMap(@SuppressWarnings("rawtypes") Map map, Object des){
		try {
			BeanUtils.populate(des, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOG.error("[ERROR-MINOR]- Can't populate bean to map {}", e);
			return null;
		}
		return des;
	}

}
