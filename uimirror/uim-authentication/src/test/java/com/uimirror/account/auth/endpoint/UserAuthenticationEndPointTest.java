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
package com.uimirror.account.auth.endpoint;

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

import com.uimirror.core.auth.AuthConstants;
import com.uimirror.ws.auth.StartApp;
import com.uimirror.ws.auth.endpoint.UserAuthenticationEndPoint;

/**
 * Integration test case for the {@link UserAuthenticationEndPoint}
 * @author Jay
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StartApp.class)
@WebAppConfiguration
@IntegrationTest
@DirtiesContext
public class UserAuthenticationEndPointTest {

	@Test
	public void loginTest() {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add(AuthConstants.USER_ID, "jayaramimca@gmail.com");
		param.add(AuthConstants.PASSWORD, "Ommtest");
		param.add(AuthConstants.KEEP_ME_LOGIN, "Y");
		SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory() {
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
				super.prepareConnection(connection, httpMethod);
				connection.setConnectTimeout(4000);
				connection.setReadTimeout(4000);
				connection.setUseCaches(Boolean.TRUE);
				connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
				//connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			}
		};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer 1234");
		List<HttpMessageConverter<?> > messageConverters = new ArrayList< HttpMessageConverter<?> >(2);   
		messageConverters.add( new StringHttpMessageConverter() );
		messageConverters.add( new ByteArrayHttpMessageConverter() );
		messageConverters.add(new FormHttpMessageConverter());
		TestRestTemplate rst = new TestRestTemplate();
		rst.setRequestFactory(s);
		rst.setMessageConverters(messageConverters);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(param, headers);
		ResponseEntity<String> entity = rst.postForEntity("http://127.0.0.1:8080/uim/account/auth/login", request, String.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	public void otpTest() {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add(AuthConstants.OTP, "1234");
		SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory() {
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
				super.prepareConnection(connection, httpMethod);
				connection.setConnectTimeout(4000);
				connection.setReadTimeout(4000);
				connection.setUseCaches(Boolean.TRUE);
				connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
				//connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			}
		};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer 1234");
		List<HttpMessageConverter<?> > messageConverters = new ArrayList< HttpMessageConverter<?> >(2);   
		messageConverters.add( new StringHttpMessageConverter() );
		messageConverters.add( new ByteArrayHttpMessageConverter() );
		messageConverters.add(new FormHttpMessageConverter());
		TestRestTemplate rst = new TestRestTemplate();
		rst.setRequestFactory(s);
		rst.setMessageConverters(messageConverters);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(param, headers);
		ResponseEntity<String> entity = rst.postForEntity("http://127.0.0.1:8080/uim/account/auth/OTP", request, String.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
