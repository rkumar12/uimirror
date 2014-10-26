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
package com.uimirror.account.client.endpoint;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.uimirror.account.StartApp;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.ouath.client.form.RegisterConstants;

/**
 * Integration test case for the {@link ClientAccountEndPoint}
 * @author Jay
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StartApp.class)
@WebAppConfiguration
@IntegrationTest
@DirtiesContext
public class ClientEndPointTest {
	
	@Test
	public void createClientTest(){
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add(RegisterConstants.NAME, "UIM_MOBILE");
		param.add(RegisterConstants.APP_URL, "http://uimirror.com");
		param.add(RegisterConstants.REDIRECT_URL, "http://uimirror.com");
		SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory() {
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
				super.prepareConnection(connection, httpMethod);
				//connection.setConnectTimeout(4000);
				//connection.setReadTimeout(4000);
				connection.setUseCaches(Boolean.TRUE);
				connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
				//connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			}
		};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer adf");
		List<HttpMessageConverter<?> > messageConverters = new ArrayList< HttpMessageConverter<?> >(2);   
		messageConverters.add( new StringHttpMessageConverter() );
		messageConverters.add( new ByteArrayHttpMessageConverter() );
		messageConverters.add(new FormHttpMessageConverter());
		TestRestTemplate rst = new TestRestTemplate();
		rst.setRequestFactory(s);
		rst.setMessageConverters(messageConverters);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(param, headers);
		ResponseEntity<String> entity = rst.postForEntity("http://127.0.0.1:8080/uim/account/client/create", request, String.class);
		System.out.println(entity.getHeaders().get(AuthConstants.AUTHORIZATION_TOKEN));
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
