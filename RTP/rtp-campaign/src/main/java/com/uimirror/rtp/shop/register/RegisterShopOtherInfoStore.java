package com.uimirror.rtp.shop.register;

import com.uimirror.core.dao.DBException;

public interface RegisterShopOtherInfoStore {
  
  ShopOtherInfo store(ShopOtherInfo shopOtherInfo) throws DBException;

}
