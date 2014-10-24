package com.uimirror.rtp.shop.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mongodb.DBCollection;
import com.uimirror.core.dao.AbstractMongoStore;

public class PersistedRegisterShopBasicInfoMongoStore extends AbstractMongoStore<ShopBasicInfo> implements RegisterShopBasicInfoStore{

  private final static String SHOP_BASIC_INFO_SEQ = "sbis";
  /**
   * Assign/ Create collection from the given {@link DBCollection}
   * @param collection
   */
  @Autowired
  public PersistedRegisterShopBasicInfoMongoStore(@Qualifier("clientBasicInfoCol") DBCollection collection, @Qualifier("clientBasicInfoSeqCol") DBCollection seqCollection){
    super(collection, seqCollection, SHOP_BASIC_INFO_SEQ, ShopBasicInfo.class);
  }
  
  @Override
  protected void ensureIndex() {
    // TODO Auto-generated method stub
    
  }
  

}
