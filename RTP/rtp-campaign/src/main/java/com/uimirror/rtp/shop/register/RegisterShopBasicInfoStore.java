package com.uimirror.rtp.shop.register;

import com.uimirror.core.dao.DBException;

public interface RegisterShopBasicInfoStore {

  ShopBasicInfo store(ShopBasicInfo shopBasicInfo) throws DBException;

}
