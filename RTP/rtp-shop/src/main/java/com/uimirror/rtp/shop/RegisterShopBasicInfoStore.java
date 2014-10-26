package com.uimirror.rtp.shop;

import com.uimirror.core.dao.DBException;
import com.uimirror.rtp.shop.bean.ShopInfo;

public interface RegisterShopBasicInfoStore {

  ShopInfo store(ShopInfo shopBasicInfo) throws DBException;

}
