package com.uimirror.ws.api.security.repo;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.uimirror.ws.api.security.ReadWriteScope;
import com.uimirror.ws.api.security.Scope;
import com.uimirror.ws.api.security.bean.base.AccessToken;
import com.uimirror.ws.api.security.common.SecurityFieldConstants;

public class AccessTokenDaoTest {

	@Test
	public void findAllAccessTokentest() {
		DBCollection dbCollection = Mockito.mock(DBCollection.class);
		DBCursor cursor = Mockito.mock(DBCursor.class);
		Mockito.when(dbCollection.find()).thenReturn(cursor);
		AccessTokenDao accessTokenDao = new AccessTokenDaoImpl(dbCollection);
		Assert.assertEquals(new ArrayList<AccessToken>(), accessTokenDao.findAll());
	}
	
	@Test
	public void findAccessTokenByIdtest() {
		String tokenId = "123";
		AccessToken token = new AccessToken("123", 
				ZonedDateTime.now(Clock.systemUTC()), ZonedDateTime.now(Clock.systemUTC()), new Scope(1, ReadWriteScope.READ), "12345", "678");
		DBCollection dbCollection = Mockito.mock(DBCollection.class);
		Mockito.when(dbCollection.findOne(new BasicDBObject(SecurityFieldConstants._ID,tokenId))).thenReturn(token);
		AccessTokenDao accessTokenDao = new AccessTokenDaoImpl(dbCollection);
		Assert.assertEquals(token, accessTokenDao.findByToken("123"));
	}

}
