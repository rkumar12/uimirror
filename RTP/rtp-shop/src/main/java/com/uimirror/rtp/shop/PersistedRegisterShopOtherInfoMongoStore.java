package com.uimirror.rtp.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mongodb.DBCollection;
import com.uimirror.core.dao.AbstractMongoStore;
import com.uimirror.core.dao.DBException;
import com.uimirror.rtp.shop.bean.ShopInfo;
import com.uimirror.rtp.shop.bean.ShopMeta;

public class PersistedRegisterShopOtherInfoMongoStore extends AbstractMongoStore<ShopMeta> implements RegisterShopBasicInfoStore{

  private final static String SHOP_OTHER_INFO_SEQ = "sbis";
  /**
   * Assign/ Create collection from the given {@link DBCollection}
   * @param collection
   */
  @Autowired
  public PersistedRegisterShopOtherInfoMongoStore(@Qualifier("clientBasicInfoCol") DBCollection collection, @Qualifier("clientBasicInfoSeqCol") DBCollection seqCollection){
    super(collection, seqCollection, SHOP_OTHER_INFO_SEQ, ShopMeta.class);
  }
  
  @Override
  protected void ensureIndex() {
    // TODO Auto-generated method stub
    
  }

/* (non-Javadoc)
 * @see com.uimirror.rtp.shop.register.RegisterShopBasicInfoStore#store(com.uimirror.rtp.shop.register.ShopBasicInfo)
 */
@Override
public ShopInfo store(ShopInfo shopBasicInfo) throws DBException {
	// TODO Auto-generated method stub
	return null;
}
  

}
