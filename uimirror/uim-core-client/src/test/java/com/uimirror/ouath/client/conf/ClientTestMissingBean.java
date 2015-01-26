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
package com.uimirror.ouath.client.conf;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.Mongo;
import com.uimirror.core.mongo.ConnectionFactory;
import com.uimirror.core.rest.extra.JsonResponseTransFormer;
import com.uimirror.core.rest.extra.ResponseTransFormer;

/**
 * @author Jay
 */
@Configuration
public class ClientTestMissingBean {
	
	@Bean
	public Mongo mongo() throws UnknownHostException{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("127.0.0.1");
		return cf.getMongoClient();
	}

	@Bean(name=JsonResponseTransFormer.NAME)
	public ResponseTransFormer<String> jsonResponseTransFormer(){
		return new JsonResponseTransFormer();
	}
}
