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
package com.uimirror.core;

/**
 * A collection of built-in priority constants for the JAX-RS components that are supposed to be
 * ordered based on their {@code javax.annotation.Priority} class-level annotation value when used
 * or applied by JAX-RS runtime.
 * <p>
 * For example, JAX-RS filters and interceptors are grouped in chains for each of the message
 * processing extension points: Pre, PreMatch, Post as well as ReadFrom and WriteTo.
 * Each of these chains is sorted based on priorities which are represented as integer numbers.
 * All chains, except Post, are sorted in ascending order; the lower the number the higher the priority.
 * The Post filter chain is sorted in descending order to ensure that response filters are executed in
 * <em>reverse order</em>.
 * </p>
 * <p>
 * JAX-RS components that belong to the same priority class (same integer value) are executed in an
 * implementation-defined manner. By default, when the {@code @Priority} annotation is absent on a component,
 * for which a priority should be applied, the {@link Priorities#USER} priority value is used.
 * </p>
 *
 * @author Jay
 * @since 2.0
 * @see javax.ws.rs.Priorities
 */
public final class Priorities {

	private Priorities() {
		// prevents construction
	}
	/**
     * Security authentication filter/interceptor priority.
     */
    public static final int AUTHENTICATION = 1000;
    /**
     * Security authorization filter/interceptor priority.
     */
    public static final int AUTHORIZATION = 2000;
    /**
     * Security authentication filter/interceptor priority.
     */
    public static final int TOKENVALIDATION = AUTHENTICATION;
    
    /**
     * Security authentication filter/interceptor priority.
     */
    public static final int TOKENEXTRACTOR = 999;
    
    public static final int USER_LICENSE_AUTHORIZATION = 1999;
    public static final int CLIENT_LICENSE_AUTHORIZATION = 1998;

    /**
     * Client Audit response commit, for any response to be get completed
     */
    public static final int  CLIENT_AUDIT_RESPONSE = 2999; 

    /**
     * Header decorator filter/interceptor priority.
     */
    public static final int HEADER_DECORATOR = 3000;
    
    /**
     * Message encoder or decoder filter/interceptor priority.
     */
    public static final int ENTITY_CODER = 4000;
    /**
     * User-level filter/interceptor priority.
     */
    public static final int USER = 5000;

}