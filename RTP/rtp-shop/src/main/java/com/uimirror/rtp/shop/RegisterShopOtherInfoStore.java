package com.uimirror.rtp.shop;

import com.uimirror.core.dao.DBException;
import com.uimirror.rtp.shop.bean.ShopMeta;

public interface RegisterShopOtherInfoStore {
  
  ShopMeta store(ShopMeta shopOtherInfo) throws DBException;

}
