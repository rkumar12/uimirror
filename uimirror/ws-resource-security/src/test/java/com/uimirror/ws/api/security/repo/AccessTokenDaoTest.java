package com.uimirror.ws.api.security.repo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

//TODO update all possible test case for AccessTokenDaoImpl
public class AccessTokenDaoTest {
	
	protected static final Logger LOG = LoggerFactory.getLogger(AccessTokenDaoTest.class);
	private Mongo mongo;
	private DB db;
	private DBCollection dbCollection;

	private DBCursor cursor;
	
	@Before
	public void setup(){
		mongo = Mockito.mock(Mongo.class);
		db = Mockito.mock(DB.class);
		dbCollection = Mockito.mock(DBCollection.class);
		cursor = Mockito.mock(DBCursor.class);
	}
	@Test
	public void findAllAccessTokentest() {
		LOG.info("[VERIFY-START]- Finding all the access token details by instaniating by collection");
//		Mockito.when(dbCollection.find()).thenReturn(cursor);
//		AccessTokenDao accessTokenDao = new AccessTokenDaoImpl(dbCollection);
//		Assert.assertEquals(new ArrayList<AccessToken>(), accessTokenDao.findAll());
//		LOG.info("[VERIFY-END]- Finding all the access token details by instaniating by collection");
//		
//		LOG.info("[VERIFY-START]- Finding all the access token details by instaniating by mongo instance");
//		Mockito.when(MongoDbFactory.getDB(mongo, AccessTokenDao.OUATH_DB)).thenReturn(db);
//		Mockito.when(DBCollectionUtil.getCollection(db, AccessTokenDao.ACCESS_TOKEN_COLLECTION)).thenReturn(dbCollection);
//		AccessTokenDao accessTokenDaoByMongo = new AccessTokenDaoImpl(mongo);
//		Assert.assertEquals(new ArrayList<AccessToken>(), accessTokenDaoByMongo.findAll());
//		LOG.info("[VERIFY-END]- Finding all the access token details by instaniating by mongo instance");
//		
//		LOG.info("[VERIFY-START]- Finding all the access token details by instaniating by mongo DB instance");
//		AccessTokenDao accessTokenDaoByMongoDb = new AccessTokenDaoImpl(db);
//		Mockito.when(DBCollectionUtil.getCollection(db, AccessTokenDao.ACCESS_TOKEN_COLLECTION)).thenReturn(dbCollection);
//		Assert.assertEquals(new ArrayList<AccessToken>(), accessTokenDaoByMongoDb.findAll());
		LOG.info("[VERIFY-END]- Finding all the access token details by instaniating by mongo DB instance");
		
	}
	
	@Test
	public void findAccessTokenByIdtest() {
		LOG.info("[VERIFY-START]- Finding all the access token details by token id instaniating by collection");
//		String tokenId = "123";
//		AccessToken token = new AccessToken("123", ZonedDateTime.now(Clock.systemUTC()), 456, new Scope(1, ReadWriteScope.READ), "12345", "678");
//		Mockito.when(dbCollection.findOne(new BasicDBObject(SecurityFieldConstants._ID,tokenId))).thenReturn(token);
//		AccessTokenDao accessTokenDao = new AccessTokenDaoImpl(dbCollection);
//		Assert.assertEquals(token, accessTokenDao.findByToken("123"));
		LOG.info("[VERIFY-END]- Finding all the access token details by token id instaniating by collection");
	}

}
